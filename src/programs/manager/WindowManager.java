package programs.manager;

import java.awt.Image;
import java.util.Objects;

import javax.swing.JFrame;

/**
 * ウィンドウの管理をするシングルトンクラス
 */
public class WindowManager {
	private static WindowManager instance;
	
	private int height;
	private int width;
	private JFrame frame;
	
	private WindowManager() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static WindowManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new WindowManager();
		}
		return instance;
	}
	
	/**
	 * 高さのゲッター
	 * @see {@link #height}
	 * @return ウィンドウの高さ
	 */
	public int getHeight() { return height; }
	/**
	 * 幅のゲッター
	 * @see {@link #width}
	 * @return ウィンドウの幅
	 */
	public int getWidth() { return width; }
	/**
	 * フレームのゲッター
	 * @see {@link #frame}
	 * @return フレーム
	 */
	public JFrame getFrame() { return frame; }
	
	/**
	 * ウィンドウ作成
	 * @param title ウィンドウタイトル
	 * @param height ウィンドウの高さ
	 * @param width ウィンドウの幅
	 */
	public void createWindow(String title,int height, int width) {
		this.height = height;
		this.width = width;
		
		frame = new JFrame(title);
	    frame.setSize(height, width);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	
	/**
	 * アイコンを設定
	 * @param image 画像データ
	 */
	public void setIcon(Image image) {
		frame.setIconImage(image);
	}
}
