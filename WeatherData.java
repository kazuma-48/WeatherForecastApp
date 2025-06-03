import javax.swing.*;
import java.awt.*;

public class WeatherData {
    private final String name;
    private final double latitude;
    private final double longitude;

    public WeatherData(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // --- 占い行生成メソッド ---
    public static class UranaiUtil {
        public JPanel createUranaiRow(String line, String imageFile) {
            JPanel row = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
            row.setOpaque(false);
            JLabel textLabel = new JLabel(line);
            textLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, 28));
            textLabel.setForeground(new java.awt.Color(120, 80, 180));
            row.add(textLabel);
            if (imageFile != null) {
                ImageIcon icon = new ImageIcon(imageFile);
                Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                row.add(new JLabel(new ImageIcon(scaled)));
            }
            return row;
        }

        public JPanel createUranaiSubRow(String item, String training) {
            JPanel subRow = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));
            subRow.setOpaque(false);
            JLabel itemLabel = new JLabel(item);
            itemLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 20));
            itemLabel.setForeground(new java.awt.Color(80, 120, 180));
            JLabel trainingLabel = new JLabel(training);
            trainingLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 20));
            trainingLabel.setForeground(new java.awt.Color(80, 120, 180));
            subRow.add(itemLabel);
            subRow.add(trainingLabel);
            subRow.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 0));
            return subRow;
        }
    }

    public static UranaiUtil uranaiUtil = new UranaiUtil();

    // --- 画像追加メソッド ---
    public static class MyImageUtil {
        public void addImage(JPanel panel, String path, int width) {
            try {
                ImageIcon icon = new ImageIcon(path);
                int height = icon.getIconHeight() * width / icon.getIconWidth();
                Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaled));
                imgLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                panel.add(imgLabel);
            } catch (Exception ignored) {
            }
        }
    }

    public static MyImageUtil imageUtil = new MyImageUtil();

    // --- 天気表示メソッド ---
    public static class WeatherUtil {
        public void showWeather(JPanel panel, WeatherData data, String imagePath) {
            panel.removeAll();
            imageUtil.addImage(panel, WeatherMethodlist.getPrefImagePathByData(data), 300);
            JLabel titleLabel = new JLabel("==== " + data.getName() + "の天気予報 ====\n");
            titleLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, 36));
            titleLabel.setForeground(new java.awt.Color(30, 30, 30));
            titleLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            panel.add(titleLabel);
            try {
                WeatherApiClient client = new WeatherApiClient();
                org.json.JSONObject json = client.getWeatherForecast(data, 3);
                org.json.JSONObject dailyObj = json.getJSONObject("daily");
                org.json.JSONArray dates = dailyObj.getJSONArray("time");
                org.json.JSONArray weatherCodes = dailyObj.getJSONArray("weathercode");
                org.json.JSONArray tempsMax = dailyObj.getJSONArray("temperature_2m_max");
                org.json.JSONArray tempsMin = dailyObj.getJSONArray("temperature_2m_min");
                org.json.JSONArray precipitation = dailyObj.optJSONArray("precipitation_sum");
                org.json.JSONArray uvIndex = dailyObj.optJSONArray("uv_index_max");
                for (int d = 0; d < dates.length(); d++) {
                    String date = java.time.LocalDate.parse(dates.getString(d)).toString();
                    int code = weatherCodes.getInt(d);
                    double max = tempsMax.getDouble(d), min = tempsMin.getDouble(d);
                    String weather = WeatherMethodlist.printWeather(code);
                    String precipStr = (precipitation != null && d < precipitation.length())
                            ? String.format(", 降水量 %.1fmm", precipitation.getDouble(d))
                            : "";
                    String uvStr = (uvIndex != null && d < uvIndex.length())
                            ? String.format(", 紫外線指数 %.1f", uvIndex.getDouble(d))
                            : "";
                    String weatherImagePath = WeatherMethodlist.getWeatherImagePath(weather);
                    if (weatherImagePath != null) {
                        imageUtil.addImage(panel, weatherImagePath, 80);
                    }
                    JLabel infoLabel = new JLabel(String.format("%s: %s, 最低 %.1f°C, 最高 %.1f°C%s%s", date, weather, min,
                            max, precipStr, uvStr));
                    infoLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 30));
                    infoLabel.setForeground(new java.awt.Color(30, 30, 30));
                    infoLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                    infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                    panel.add(infoLabel);
                }
            } catch (Exception ex) {
                JLabel errorLabel = new JLabel("天気情報の取得に失敗しました: " + ex.getMessage());
                errorLabel.setFont(new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, 26));
                errorLabel.setForeground(java.awt.Color.RED);
                errorLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                errorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                panel.add(errorLabel);
            }
        }
    }

    public static WeatherUtil weatherUtil = new WeatherUtil();
}