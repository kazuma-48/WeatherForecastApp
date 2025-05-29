import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class WeatherForecastJapanese {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<WeatherData> datas = List.of(
                new WeatherData("岐阜", 35.4233, 136.7606),
                new WeatherData("静岡", 34.9756, 138.3828),
                new WeatherData("愛知", 35.1815, 136.9066),
                new WeatherData("三重", 34.7303, 136.5086));

        WeatherApiClient client = new WeatherApiClient();

        for (WeatherData data : datas) {
            JSONObject json = client.getWeatherForecast(data, 3);

            JSONArray dates = json.getJSONObject("daily").getJSONArray("time");
            JSONArray weatherCodes = json.getJSONObject("daily").getJSONArray("weathercode");
            JSONArray tempsMax = json.getJSONObject("daily").getJSONArray("temperature_2m_max");
            JSONArray tempsMin = json.getJSONObject("daily").getJSONArray("temperature_2m_min");

            System.out.println("==== " + data.getName() + "の天気予報 ====");
            for (int d = 0; d < dates.length(); d++) {
                String date = dates.getString(d);
                int code = weatherCodes.getInt(d);
                double max = tempsMax.getDouble(d);
                double min = tempsMin.getDouble(d);
                String weather = WeatherMethodlist.printWeather(code);

                System.out.printf("%s: %s, 最低 %.1f°C, 最高 %.1f°C%n", date, weather, min, max);
            }
            System.out.println();
        }
    }
}