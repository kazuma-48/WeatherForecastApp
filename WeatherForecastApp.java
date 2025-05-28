import java.util.List;

public class WeatherForecastApp {
    private static final String[][] AREAS = {
            { "210000", "岐阜" },
            { "220000", "静岡" },
            { "230000", "愛知" },
            { "240000", "三重" }
    };

    public static void main(String[] args) {
        printWeatherForecasts();
        KionForecast.printKionForecast();
        WeatherUranai.printWeatherUranai();
    }

    private static void printWeatherForecasts() {
        WeatherApiClient apiClient = new WeatherApiClient();
        WeatherDataParser parser = new WeatherDataParser();

        for (String[] area : AREAS) {
            String code = area[0];
            String name = area[1];

            System.out.println("▼ " + name + " の天気予報 ----------------------");

            try {
                String jsonData = apiClient.fetchWeatherData(code);
                List<WeatherData> weatherList = parser.parse(jsonData);
                for (WeatherData data : weatherList) {
                    System.out.println(data);
                }
            } catch (Exception e) {
                System.err.println("エラー: " + e.getMessage());
            }

            System.out.println("------------------------------------------\n");
        }
    }
}