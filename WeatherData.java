public class WeatherData {
    private final String areaName;
    private final String time;
    private final String weather;

    public WeatherData(String areaName, String time, String weather) {
        this.areaName = areaName;
        this.time = time;
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "エリア: " + areaName + " [" + time + "] " + weather;
    }
}