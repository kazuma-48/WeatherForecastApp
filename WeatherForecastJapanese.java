import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.time.LocalDate;

public class WeatherForecastJapanese {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<WeatherData> datas = List.of(
                new WeatherData("岐阜", 35.4233, 136.7606),
                new WeatherData("静岡", 34.9756, 138.3828),
                new WeatherData("愛知", 35.1815, 136.9066),
                new WeatherData("三重", 34.7303, 136.5086));

        WeatherApiClient client = new WeatherApiClient();

        // GUIウィンドウの準備
        JFrame frame = new JFrame("日本の天気予報");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        frame.add(scrollPane);

        // ボタンパネルの追加（上部に横並びで配置し、余白を追加）
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setBackground(new Color(240, 248, 255));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // ホームに戻るボタンの追加（左上に配置）
        JButton homeBtn = new JButton("ホームに戻る");
        homeBtn.setFont(new Font("Yu Gothic UI", Font.PLAIN, 14));
        homeBtn.setFocusPainted(false);
        homeBtn.setBackground(new Color(230, 240, 255));
        homeBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        homePanel.setBackground(new Color(240, 248, 255));
        homePanel.add(homeBtn);
        frame.add(homePanel, BorderLayout.NORTH);

        // ホーム画面を表示するメソッド
        Runnable showHome = () -> {
            panel.removeAll();
            // 画像の表示（左上に表示）
            JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            imgPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            try {
                ImageIcon icon = new ImageIcon("image/map.png");
                Image img = icon.getImage();
                int width = 600; // 左上なので小さめに
                int height = img.getHeight(null) * width / img.getWidth(null);
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaledImg));
                imgPanel.add(imgLabel);
            } catch (Exception ex) {
                // 画像が読み込めない場合は何もしない
            }
            panel.add(imgPanel);
            panel.add(Box.createVerticalStrut(10));
            // 文字を左寄せに
            JLabel homeLabel = new JLabel("今日のトレーニングコンディションは晴れのちパワーアップ！");
            homeLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 40));
            homeLabel.setHorizontalAlignment(SwingConstants.LEFT);
            homeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(homeLabel);
            panel.add(Box.createVerticalStrut(20));
            String uranaiResult = WeatherMethodlist.printUranai();
            JLabel uranaiLabel = new JLabel("<html>今日の占い<br>" + uranaiResult.replace("\n", "<br>") + "</html>");
            uranaiLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
            uranaiLabel.setHorizontalAlignment(SwingConstants.LEFT);
            uranaiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(uranaiLabel);
            panel.add(Box.createVerticalStrut(20));
            panel.revalidate();
            panel.repaint();
        };
        // 最初にホーム画面を表示
        showHome.run();
        // ホームボタンのアクション
        homeBtn.addActionListener(e -> showHome.run());

        // 各県ごとにボタンを作成
        for (WeatherData data : datas) {
            JButton btn = new JButton(data.getName() + "の天気");
            btn.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBackground(new Color(220, 230, 250));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            buttonPanel.add(Box.createHorizontalStrut(10));
            buttonPanel.add(btn);
            btn.addActionListener(e -> {
                try {
                    panel.removeAll();
                    // 県名タイトル
                    JLabel cityLabel = new JLabel("==== " + data.getName() + "の天気予報 ====");
                    cityLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 26)); // 文字を大きく
                    cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                    panel.add(cityLabel);
                    // 天気データ取得
                    JSONObject json = client.getWeatherForecast(data, 3);
                    JSONArray dates = json.getJSONObject("daily").getJSONArray("time");
                    JSONArray weatherCodes = json.getJSONObject("daily").getJSONArray("weathercode");
                    JSONArray tempsMax = json.getJSONObject("daily").getJSONArray("temperature_2m_max");
                    JSONArray tempsMin = json.getJSONObject("daily").getJSONArray("temperature_2m_min");
                    // 降水量データの取得
                    JSONArray precipitation = null;
                    if (json.getJSONObject("daily").has("precipitation_sum")) {
                        precipitation = json.getJSONObject("daily").getJSONArray("precipitation_sum");
                    }
                    // 各日ごとの天気情報を表示
                    for (int d = 0; d < dates.length(); d++) {
                        LocalDate localDate = LocalDate.parse(dates.getString(d));
                        String date = localDate.toString();
                        int code = weatherCodes.getInt(d);
                        double max = tempsMax.getDouble(d);
                        double min = tempsMin.getDouble(d);
                        String weather = WeatherMethodlist.printWeather(code);
                        String precipStr = "";
                        if (precipitation != null) {
                            double precip = precipitation.getDouble(d);
                            precipStr = String.format(", 降水量 %.1fmm", precip);
                        }
                        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        // 日付・天気・気温・降水量のメッセージ
                        JLabel dateLabel = new JLabel(
                                String.format("%s: %s, 最低 %.1f°C, 最高 %.1f°C%s", date, weather, min, max, precipStr));
                        dateLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20)); // 文字を大きく
                        row.add(dateLabel);
                        panel.add(row);
                    }
                    // メッセージ：戻るには他のボタンを押してください
                    JLabel backMsg = new JLabel("他の県の天気や占いを見るには下のボタンを押してください");
                    backMsg.setFont(new Font("Yu Gothic UI", Font.ITALIC, 13));
                    backMsg.setForeground(new Color(80, 80, 80));
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(backMsg);
                    panel.revalidate();
                    panel.repaint();
                } catch (IOException | InterruptedException ex) {
                    JOptionPane.showMessageDialog(frame, "天気情報の取得に失敗しました: " + ex.getMessage(), "エラー",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        frame.setVisible(true);
    }
}