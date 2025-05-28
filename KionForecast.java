import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class KionForecast {
    public static void printKionForecast() {
        Map<String, double[]> cities = new LinkedHashMap<>();
        cities.put("岐阜", new double[] { 35.4233, 136.7606 });
        cities.put("愛知", new double[] { 35.1802, 136.9066 });
        cities.put("静岡", new double[] { 34.9756, 138.3828 });
        cities.put("三重", new double[] { 34.7303, 136.5086 });

        for (Map.Entry<String, double[]> entry : cities.entrySet()) {
            String city = entry.getKey();
            double lat = entry.getValue()[0];
            double lon = entry.getValue()[1];

            String urlStr = String.format(
                    "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&daily=temperature_2m_max,temperature_2m_min&timezone=Asia%%2FTokyo",
                    lat, lon);

            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                JSONArray dates = json.getJSONObject("daily").getJSONArray("time");
                JSONArray tempsMax = json.getJSONObject("daily").getJSONArray("temperature_2m_max");
                JSONArray tempsMin = json.getJSONObject("daily").getJSONArray("temperature_2m_min");

                System.out.println(  city + " の気温予報：");
                for (int i = 0; i < 3; i++) {
                    System.out.printf("  %s: 最低 %.1f°C / 最高 %.1f°C%n",
                            dates.getString(i), tempsMin.getDouble(i), tempsMax.getDouble(i));
                }
                System.out.println();
            } catch (Exception e) {
                System.err.println("気温取得エラー: " + city + " " + e.getMessage());
            }
        }
    }
}
