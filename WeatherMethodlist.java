import java.util.Random;
import java.time.LocalDate;

public class WeatherMethodlist {
    // 星座占い（staticメソッド＆String型で結果を返す）
    public static String printUranai() {
        String[] constellation = {
                "おひつじ座", "おうし座", "ふたご座", "かに座", "しし座", "おとめ座",
                "てんびん座", "さそり座", "いて座", "やぎ座", "みずがめ座", "うお座"
        };
        String[] luckyitem = {
                "ダンベル","プロテイン","サポーター","ヨガマット","ランニングシューズ","筋トレノート","シェイカー","サプリメント","ハンドグリップ","タオル",
                "水筒","エナジードリンク","ジム会員証","腹筋ローラー","バランスボール","トレーニンググローブ","InBody","パワーベルト","バーベル","トレーニングウェア",
                "テーピング","パワーグリップ","リストラップ","フォームローラー","プッシュアップボード"
        };
        String[] luckytraining = {
                "ベンチプレス","ラットプルダウン","ダンベルカール","ミリタリープレス","スクワット","ダンベルフライ","デッドリフト","ハンマーカール","サイドレイズ","レッグプレス",
                "プッシュアップ","バーベルベントオーバーロウ","バーベルカール","フロントレイズ","レッグエクステンション","インクラインベンチプレス","チンニング","フレンチプレス","リアレイズ","カーフレイズ"
        };
        StringBuilder sb = new StringBuilder();
        int[][] list = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list[i][j] = -1;
            }
        }
        // 日付情報からシード値を生成（import java.time.LocalDate; を利用）
        LocalDate today = LocalDate.now();
        int seed = today.getYear() * 10000 + today.getMonthValue() * 100 + today.getDayOfMonth();
        for (int i = 0; i < 3; i++) {
            int numcon = new Random(seed).nextInt(12);
            int numitem = new Random(seed).nextInt(25);
            int numcolor = new Random(seed).nextInt(20);
            while (list[0][0] == numcon || list[1][0] == numcon) {
                numcon = (numcon + 1) % 12;
            }
            while (list[0][1] == numitem || list[1][1] == numitem) {
                numitem = (numitem + 1) % 50;
            }
            while (list[0][2] == numcolor || list[1][2] == numcolor) {
                numcolor = (numcolor + 1) % 20;
            }
            sb.append("第" + (i + 1) + "位は" + "「" + constellation[numcon] + "」\n");
            sb.append("　　ラッキーアイテムは" + "「" + luckyitem[numitem] + "」\n");
            sb.append("　　ラッキートレーニングは" + "「" + luckytraining[numcolor] + "」\n");
            list[i][0] = numcon;
            list[i][1] = numitem;
            list[i][2] = numcolor;
        }
        return sb.toString();
    }

    // 天気情報
    public static String printWeather(int code) {
        return switch (code) {
            case 0 -> "快晴";
            case 1, 2, 3 -> "曇り";
            case 45, 48 -> "霧";
            case 51, 53, 55 -> "霧雨";
            case 56, 57 -> "凍る霧雨";
            case 61, 63, 65 -> "雨";
            case 66, 67 -> "凍る雨";
            case 71, 73, 75 -> "雪";
            case 77 -> "雪の粒";
            case 80, 81, 82 -> "にわか雨";
            case 85, 86 -> "にわか雪";
            case 95 -> "雷雨";
            case 96, 99 -> "雷雨（ひょう）";
            default -> "不明";
        };
    }
}