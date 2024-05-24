package programs.data;

import java.awt.Color;
import java.awt.Font;

/**
 * テキストグラフィックのデータ
 * @author 田淵勇輝
 */
public class TextGraphicData {
	private String text;
	private Font font;
	private int size;
	private Vector2 position;
	private Color color;
	
	/**
	 * 全て初期化するコンストラクタ
	 * @param text 表示する文字列
	 * @param font フォント
	 * @param size フォントサイズ
	 * @param position 表示位置
	 * @param color 文字色
	 */
	public TextGraphicData(String text, Font font, int size, Vector2 position, Color color) {
		this.text = text;
		this.font = font.deriveFont(size);
		this.size = size;
		this.position = position;
		this.color = color;
	}
	
	/**
	 * コンストラクタ
	 * フォントは自動的にArialになります
	 * @param text 表示する文字列
	 * @param size フォントサイズ
	 * @param position 表示位置
	 * @param color 文字色
	 */
	public TextGraphicData(String text, int size, Vector2 position, Color color) {
		this.text = text;
		font = new Font("Arial", Font.PLAIN, size);
		this.size = size;
		this.position = position;
		this.color = color;
	}
	
	/**
	 * 文字列のゲッター
	 * @see {@link #text}
	 * @return 文字列
	 */
	public String getText() { return text; }
	/**
	 * フォントのゲッター
	 * @see {@link #font}
	 * @return フォント
	 */
	public Font getFont() { return font; }
	/**
	 * フォントサイズのゲッター
	 * @see {@link #size}
	 * @return フォントサイズ
	 */
	public int getSize() { return size; }
	/**
	 * 表示位置のゲッター
	 * @see {@link #position}
	 * @return 表示位置
	 */
	public Vector2 getPosition() { return position; }
	/**
	 * 文字色のゲッター
	 * @see {@link #color}
	 * @return
	 */
	public Color getColor() { return color; }
	
	/**
	 * 文字列のセッター
	 * @see {@link #text}
	 * @param text 文字列
	 */
	public void setText(String text) { this.text = text; }
	/**
	 * フォントサイズのセッター
	 * @see {@link #size}
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
		font = font.deriveFont(size);
	}
	/**
	 * 表示位置のセッター
	 * @see {@link #position}
	 * @param position 表示位置
	 */
	public void setPosition(Vector2 position) { this.position = position; }
	/**
	 * 文字色のセッター
	 * @see {@link #color}
	 * @param color 文字色
	 */
	public void setColor(Color color) { this.color = color; }
	
	/**
	 * 座標を加算
	 * @param pos 座標に加算する値
	 */
	public void addPosition(Vector2 pos) {
		position.setX(position.getX() + pos.getX());
		position.setY(position.getY() + pos.getY());
	}
}
