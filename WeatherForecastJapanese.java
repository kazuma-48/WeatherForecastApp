import java.util.List;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class WeatherForecastJapanese {
    // 47都道府県のリスト
    private static final List<WeatherData> PREFS = List.of(
            new WeatherData("北海道", 43.0642, 141.3469), new WeatherData("青森", 40.8244, 140.74),
            new WeatherData("岩手", 39.7036, 141.1527), new WeatherData("宮城", 38.2688, 140.8721),
            new WeatherData("秋田", 39.7186, 140.1024), new WeatherData("山形", 38.2404, 140.3633),
            new WeatherData("福島", 37.7503, 140.4675), new WeatherData("茨城", 36.3418, 140.4468),
            new WeatherData("栃木", 36.5657, 139.8836), new WeatherData("群馬", 36.3912, 139.0609),
            new WeatherData("埼玉", 35.8569, 139.6489), new WeatherData("千葉", 35.6046, 140.1233),
            new WeatherData("東京", 35.6895, 139.6917), new WeatherData("神奈川", 35.4478, 139.6425),
            new WeatherData("新潟", 37.9026, 139.0232), new WeatherData("富山", 36.6953, 137.2113),
            new WeatherData("石川", 36.5947, 136.6256), new WeatherData("福井", 36.0652, 136.2216),
            new WeatherData("山梨", 35.6642, 138.5684), new WeatherData("長野", 36.6513, 138.181),
            new WeatherData("岐阜", 35.4233, 136.7606), new WeatherData("静岡", 34.9756, 138.3828),
            new WeatherData("愛知", 35.1815, 136.9066), new WeatherData("三重", 34.7303, 136.5086),
            new WeatherData("滋賀", 35.0045, 135.8686), new WeatherData("京都", 35.0214, 135.7556),
            new WeatherData("大阪", 34.6863, 135.52), new WeatherData("兵庫", 34.6913, 135.183),
            new WeatherData("奈良", 34.6851, 135.8048), new WeatherData("和歌山", 34.226, 135.1675),
            new WeatherData("鳥取", 35.5039, 134.2377), new WeatherData("島根", 35.4723, 133.0505),
            new WeatherData("岡山", 34.6618, 133.9344), new WeatherData("広島", 34.3963, 132.4596),
            new WeatherData("山口", 34.1859, 131.4714), new WeatherData("徳島", 34.0658, 134.5593),
            new WeatherData("香川", 34.3401, 134.0434), new WeatherData("愛媛", 33.8417, 132.7661),
            new WeatherData("高知", 33.5597, 133.5311), new WeatherData("福岡", 33.5902, 130.4017),
            new WeatherData("佐賀", 33.2494, 130.2988), new WeatherData("長崎", 32.7448, 129.8737),
            new WeatherData("熊本", 32.7898, 130.7417), new WeatherData("大分", 33.2382, 131.6126),
            new WeatherData("宮崎", 31.9111, 131.4239), new WeatherData("鹿児島", 31.5602, 130.5581),
            new WeatherData("沖縄", 26.2124, 127.6809));

    public static void main(String[] args) throws IOException, InterruptedException {
        // --- Swingウィンドウ初期化 ---
        JFrame frame = new JFrame("日本の天気予報");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700); // サイズ拡大
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 250, 255));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getViewport().setBackground(new Color(245, 250, 255));
        frame.add(scrollPane);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buttonPanel.setBackground(new Color(200, 220, 255));
        frame.add(buttonPanel, BorderLayout.SOUTH);
        // ホームボタン
        JButton homeBtn = new JButton("ホームに戻る");
        homeBtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 36));
        homeBtn.setBackground(new Color(120, 180, 255));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setFocusPainted(false);
        homeBtn.setBorder(BorderFactory.createEmptyBorder(16, 36, 16, 36));
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel homePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        homePanel.setBackground(new Color(200, 220, 255));
        homePanel.add(homeBtn);
        frame.add(homePanel, BorderLayout.NORTH);

        // --- ホーム画面表示メソッド ---
        Runnable showHome = () -> {
            panel.removeAll();
            // 日本地図画像を右側に表示するパネル
            JPanel homeContentPanel = new JPanel();
            homeContentPanel.setLayout(new BorderLayout());
            homeContentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            // 左側: タイトルやプルダウンなど
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setOpaque(false);
            leftPanel.add(Box.createVerticalStrut(10));
            JLabel titleLabel = new JLabel("今日のトレーニングコンディションは晴れのちパワーアップ！");
            titleLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 48));
            titleLabel.setForeground(new Color(0, 120, 220));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            leftPanel.add(titleLabel);
            leftPanel.add(Box.createVerticalStrut(20));
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            searchPanel.setOpaque(false);
            JLabel searchLabel = new JLabel("都道府県選択: ");
            searchLabel.setFont(new Font("Yu Gothic UI", Font.BOLD, 28));
            searchLabel.setForeground(new Color(0, 80, 180));
            String[] prefNames = PREFS.stream().map(WeatherData::getName).toArray(String[]::new);
            JComboBox<String> prefCombo = new JComboBox<>(prefNames);
            prefCombo.setFont(new Font("Yu Gothic UI", Font.PLAIN, 24));
            prefCombo.setBackground(new Color(230, 240, 255));
            JButton searchBtn = new JButton("表示");
            searchBtn.setFont(new Font("Yu Gothic UI", Font.BOLD, 26));
            searchBtn.setBackground(new Color(120, 180, 255));
            searchBtn.setForeground(Color.WHITE);
            searchBtn.setFocusPainted(false);
            searchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            searchPanel.add(searchLabel);
            searchPanel.add(prefCombo);
            searchPanel.add(searchBtn);
            leftPanel.add(searchPanel);
            searchBtn.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String selected = (String) prefCombo.getSelectedItem();
                    WeatherData found = PREFS.stream().filter(d -> d.getName().equals(selected)).findFirst()
                            .orElse(null);
                    if (found != null) {
                        String imagePath = WeatherMethodlist.getPrefImagePathByData(found);
                        WeatherData.weatherUtil.showWeather(panel, found, imagePath);
                    }
                }
            });
            // --- 占い ---
            String uranaiResult;
            try {
                uranaiResult = WeatherMethodlist.printUranai();
            } catch (Exception ex) {
                uranaiResult = "占い結果の取得に失敗しました";
            }
            JPanel uranaiPanel = new JPanel();
            uranaiPanel.setLayout(new BoxLayout(uranaiPanel, BoxLayout.Y_AXIS));
            uranaiPanel.setOpaque(false);
            String[] lines = uranaiResult.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                if (line.startsWith("第") && line.contains("は「") && line.contains("座")) {
                    String seiza = line.substring(line.indexOf("「") + 1, line.indexOf("座"));
                    String imageFile = WeatherMethodlist.getSeizaImageFile(seiza);
                    JPanel row = WeatherData.uranaiUtil.createUranaiRow(line, imageFile);
                    uranaiPanel.add(row);
                    if (i + 2 < lines.length) {
                        uranaiPanel.add(WeatherData.uranaiUtil.createUranaiSubRow(lines[i + 1], lines[i + 2]));
                        i += 2;
                    }
                } else {
                    addLabel(uranaiPanel, line, 22, false, new Color(120, 80, 180));
                }
            }
            leftPanel.add(uranaiPanel);
            leftPanel.add(Box.createVerticalStrut(40));
            // 右側: 画像
            JPanel rightPanel = new JPanel();
            rightPanel.setOpaque(false);
            rightPanel.setLayout(new BorderLayout());
            JLabel imgTitle = new JLabel("日本地図");
            imgTitle.setFont(new Font("Yu Gothic UI", Font.BOLD, 32));
            imgTitle.setForeground(new Color(0, 120, 220));
            imgTitle.setHorizontalAlignment(SwingConstants.CENTER);
            rightPanel.add(imgTitle, BorderLayout.NORTH);
            WeatherData.imageUtil.addImage(rightPanel, "image/cd1f65b4-88c3-4750-b086-913bc4a34136.jpg", 340);
            homeContentPanel.add(leftPanel, BorderLayout.CENTER);
            homeContentPanel.add(rightPanel, BorderLayout.EAST);
            panel.add(homeContentPanel);
            panel.revalidate();
            panel.repaint();
        };
        showHome.run();
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHome.run();
            }
        });

        // --- 県ごと画像ボタン削除済み ---
        frame.setVisible(true);

        // --- ユーティリティクラスをstaticにしてmain内で直接newせず使えるようにする ---
    }

    // --- ラベル追加用メソッド ---
    static void addLabel(JPanel panel, String text, int fontSize, boolean bold) {
        addLabel(panel, text, fontSize, bold, new java.awt.Color(30, 30, 30), false);
    }

    static void addLabel(JPanel panel, String text, int fontSize, boolean bold, java.awt.Color color) {
        addLabel(panel, text, fontSize, bold, color, false);
    }

    static void addLabel(JPanel panel, String text, int fontSize, boolean bold, java.awt.Color color, boolean italic) {
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Yu Gothic UI",
                italic ? java.awt.Font.ITALIC : (bold ? java.awt.Font.BOLD : java.awt.Font.PLAIN), fontSize + 10));
        label.setForeground(color);
        label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(label);
    }
}

// --- ここから下をWeatherData.javaに移動 ---
// --- 占い行生成メソッド ---
// --- 画像追加メソッド ---
// --- 天気表示メソッド ---
// これらのクラス・インスタンスはWeatherData.javaに移動済みのため、ここから削除