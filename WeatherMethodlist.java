import java.util.Random;

public class WeatherMethodlist {
    //星座占い
    public static void printWeatherUranai() {
        String[] constellation = {
                "おひつじ座", "おうし座", "ふたご座", "かに座", "しし座", "おとめ座",
                "てんびん座", "さそり座", "いて座", "やぎ座", "みずがめ座", "うお座"
        };
        String[] luckyitem = {
                "ハンカチ", "帽子", "水筒", "日傘", "サングラス", "弁当箱", "ネクタイ", "サンドイッチ", "おにぎり", "ネイル",
                "ダンベル", "らっきょう", "ウエットシート", "カードケース", "エコバッグ", "履き慣れた靴", "枕", "通帳ケース", "掃除機", "メッセージカード",
                "ポスター", "ハンドソープ", "砂時計", "缶詰", "小説", "ぬいぐるみ", "アクセサリー", "日焼け止め", "免許証", "万年筆",
                "香水", "加湿器", "パワーストーン", "手鏡", "コーヒーカップ", "手袋", "切手", "絵画", "ティーポット", "ステッカー",
                "歯ブラシ", "抗菌グッズ", "スマホケース", "ハンドクリーム", "タオル", "二つ折りの財布", "モバイル充電器", "工具セット", "ソフトクリーム", "はさみ",
                "ヘルメット", "チョーク", "パソコン", "警棒", "カルテ", "ボールペン", "テレビ", "聴診器", "マスク", "ピアス",
                "ヘアゴム", "ライター", "調理器具", "イヤホン", "ヘアピン", "水", "指し棒", "カメラ", "ハンガー", "スマホ"
        };
        String[] luckycolor = {
                "赤", "青", "緑", "黄", "紫", "オレンジ", "ピンク", "黒", "白", "金",
                "銀", "水色", "茶色", "ベージュ", "グレー", "黄緑", "シアン", "マゼンタ", "パステル", "クリーム"
        };
        System.out.println("今日の運勢");
        int list[][] = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                list[i][j] = -1;
            }
        }
        for (int i = 0; i < 3; i++) {
            Random rnd = new Random();
            int numcon = rnd.nextInt(12);
            int numitem = rnd.nextInt(50);
            int numcolor = rnd.nextInt(20);
            while (list[0][0] == numcon || list[1][0] == numcon) {
                numcon = ((numcon + numcon) / 4) % 12;
            }
            while (list[0][1] == numitem || list[1][1] == numitem) {
                numitem = ((numitem + numitem) / 4) % 50;
            }
            while (list[0][2] == numcolor || list[1][2] == numcolor) {
                numcolor = ((numcolor + numcolor) / 4) % 20;
            }
            System.out.println("第" + (i + 1) + "位は" + "「" + constellation[numcon] + "」\n"
                    + "　　ラッキーアイテムは" + "「" + luckyitem[numitem] + "」\n"
                    + "　　ラッキーカラーは" + "「" + luckycolor[numcolor] + "」");
            System.out.println();
            list[i][0] = numcon;
            list[i][1] = numitem;
            list[i][2] = numcolor;
        }
    }
    //天気情報
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
