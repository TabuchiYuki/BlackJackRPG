package programs.manager;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
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
	private JFrame frame;
	
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
        frame = new JFrame(title);
        
        // ウィンドウのサイズを設定
        frame.setSize(width, height);
        
        // ウィンドウのリサイズを禁止
        frame.setResizable(false);
        
        // 画面の中央にウィンドウを表示するための計算
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        int windowWidth = frame.getWidth();
        int windowHeight = frame.getHeight();
        int x = (screenWidth - windowWidth) / 2;
        int y = (screenHeight - windowHeight) / 2;
        
        // ウィンドウの表示位置を設定
        frame.setLocation(x, y);
        
        // ウィンドウを閉じた時に処理を終了する設定
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
