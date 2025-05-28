import org.json.JSONArray;
import org.json.JSONObject;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataParser {
    public List<WeatherData> parse(String jsonData) {
        List<WeatherData> result = new ArrayList<>();
        JSONArray rootArray = new JSONArray(jsonData);
        JSONObject firstItem = rootArray.getJSONObject(0);

        JSONArray timeDefines = firstItem
                .getJSONArray("timeSeries")
                .getJSONObject(0)
                .getJSONArray("timeDefines");

        JSONArray areas = firstItem
                .getJSONArray("timeSeries")
                .getJSONObject(0)
                .getJSONArray("areas");

        for (int i = 0; i < areas.length(); i++) {
            JSONObject areaObj = areas.getJSONObject(i);
            String areaName = areaObj.getJSONObject("area").getString("name");
            JSONArray weathers = areaObj.getJSONArray("weathers");

            for (int j = 0; j < weathers.length(); j++) {
                String time = timeDefines.getString(j);
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(time);
                String formattedTime = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
                String weather = weathers.getString(j);
                result.add(new WeatherData(areaName, formattedTime, weather));
            }
        }
        return result;
    }
}