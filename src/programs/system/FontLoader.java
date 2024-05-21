package programs.system;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * フォントを読み込むシングルトンクラス
 * @author 田淵勇輝
 */
public class FontLoader {
	// 定数
	private final String FONT_REGISTRY_LOCAL_PATH = "resources/fonts/";
	private final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private final String TRUE_TYPE_FONT_EXTEND = ".ttf";
	private final String OPEN_TYPE_FONT_EXTEND = ".otf";
	
	// 静的フィールド
	private static FontLoader instance;
	
	// フィールド
	private List<Font> fonts = new ArrayList<>();
	
	/**
	 * プライベートコンストラクタ
	 */
	private FontLoader() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static FontLoader getInstance() { 
		if(Objects.isNull(instance)) {
			instance = new FontLoader();
		}
		return instance;
	}
	
	/**
	 * フォントリストのゲッター
	 * @return フォントリスト
	 */
	public List<Font> getFonts() { return fonts; }
	
	/**
	 * フォントファイルを読み込む
	 * @param fileName ファイル名
	 * @return 読み込んだフォント
	 */
	public Font loadFont(String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLASS_PATH);
		sb.append(FONT_REGISTRY_LOCAL_PATH);
		sb.append(fileName);
		String tmpPath = sb.toString();
		
		try {
			File ttfFile = new File(tmpPath + TRUE_TYPE_FONT_EXTEND);
			if(ttfFile.exists()) {
				return Font.createFont(Font.TRUETYPE_FONT, ttfFile);
			}
			// ttfでなければotfで判定
			File otfFile = new File(tmpPath + OPEN_TYPE_FONT_EXTEND);
			if(otfFile.exists()) {
				return Font.createFont(Font.TRUETYPE_FONT, otfFile);
			}
			System.out.print("指定のファイルが見つかりませんでした");
			return null;
		} catch(IOException | FontFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * フォントファイルを読み込んでリストに追加する
	 * @param fileName ファイル名
	 */
	public void loadAndAddFontList(String fileName) {
		fonts.add(loadFont(fileName));
	}
}
