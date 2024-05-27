package programs.manager;

import java.awt.Image;
import java.awt.Insets;
import java.util.Objects;

import javax.swing.JFrame;

/**
 * ウィンドウの管理をするシングルトンクラス
 * @author 田淵勇輝
 */
public class WindowManager{
	private static WindowManager instance;
	
	private int width;
	private int height;
	private JFrame frame = new JFrame();
	
	/**
	 * プライベートコンストラクタ
	 */
	private WindowManager() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return
	 */
	public static WindowManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new WindowManager();
		}
		return instance;
	}
	
	/**
	 * 幅のゲッター
	 * @see {@link #width}
	 * @return ウィンドウの幅
	 */
	public int getWidth() { return width; }
	/**
	 * 高さのゲッター
	 * @see {@link #height}
	 * @return ウィンドウの高さ
	 */
	public int getHeight() { return height; }
	/**
	 * フレームのゲッター
	 * @see {@link #frame}
	 * @return フレーム
	 */
	public JFrame getFrame() { return frame; }
	
	/**
	 * ウィンドウ作成
	 * @param title タイトル
	 * @param width 幅
	 * @param height 高さ
	 */
	public void createWindow(String title, int width, int height) {
		this.width = width;
		this.height = height;
		createFrame(title, width, height);
	}
	
	/**
	 * ウィンドウの作成
	 * @param title タイトル
	 * @param width 幅
	 * @param height 高さ
	 */
	private void createFrame(String title, int width, int height) {
		frame.setTitle(title);
		
		// ウィンドウを閉じた時に処理を終了する設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// ウィンドウのサイズを設定
		frame.pack();
		Insets insets = frame.getInsets();
		frame.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
		
		// ウィンドウのリサイズを禁止
		frame.setResizable(false);
		
		// ウィンドウの表示位置を中央に設定
		frame.setLocationRelativeTo(null);
		
		// ウィンドウを表示
		frame.setVisible(true);
	}

	/**
	 * アイコンを設定
	 * @param image 画像データ
	 */
	public void setIcon(Image image) {
		if (frame != null) {
			frame.setIconImage(image);
		} else {
			System.err.println("Frame is not initialized. Cannot set icon.");
		}
	}
}
