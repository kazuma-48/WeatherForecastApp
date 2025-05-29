import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class WeatherApiClient {
    private final HttpClient client = HttpClient.newHttpClient();

    public JSONObject getWeatherForecast(WeatherData city, int forecastDays) throws IOException, InterruptedException {
        String url = String.format(
                "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=Asia%%2FTokyo&forecast_days=%d",
                city.getLatitude(), city.getLongitude(), forecastDays);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}