import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WeatherApiClient {
    public String fetchWeatherData(String areaCode)throws Exception{
        String apiUrl ="https://www.jma.go.jp/bosai/forecast/data/forecast/240000.json";
        URI uri = new URI(apiUrl);
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuilder responseBody = new StringBuilder();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }
            }
            return responseBody.toString();
        } else {
            throw new IOException("HTTP エラーコード: " + responseCode);
        }
    }
}