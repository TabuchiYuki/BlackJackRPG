package programs.system;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * フォントを読み込むシングルトンクラス
 * @author 田淵勇輝
 */
public class FontLoader {
	// 定数
	private final String FONT_RESOURCE_PATH = "/resources/fonts/";
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
		String ttfPath = FONT_RESOURCE_PATH + fileName + TRUE_TYPE_FONT_EXTEND;
		String otfPath = FONT_RESOURCE_PATH + fileName + OPEN_TYPE_FONT_EXTEND;
		
		try (InputStream ttfStream = getClass().getResourceAsStream(ttfPath)) {
			if (ttfStream != null) {
				return Font.createFont(Font.TRUETYPE_FONT, ttfStream);
			}
		} catch(IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		try (InputStream ttfStream = getClass().getResourceAsStream(otfPath)) {
			if (ttfStream != null) {
				return Font.createFont(Font.TRUETYPE_FONT, ttfStream);
			}
		} catch(IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		System.out.print("指定のファイルが見つかりませんでした");
		return null;
	}
	
	/**
	 * フォントファイルを読み込んでリストに追加する
	 * @param fileName ファイル名
	 */
	public void loadAndAddFontList(String fileName) {
		fonts.add(loadFont(fileName));
	}
}
