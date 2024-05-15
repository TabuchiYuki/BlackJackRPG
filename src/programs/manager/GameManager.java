package programs.manager;

import programs.system.ImageLoader;

/**
 * ゲーム進行を管理するマネージャークラス
 * @author 田淵勇輝
 */
public class GameManager {
	/**
	 * エントリポイント
	 * @param args コマンドライン引数:使用しない
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception  {
		WindowManager.getInstance().createWindow("Test", 800, 600);
		WindowManager.getInstance().setIcon(ImageLoader.getInstance().loadImage("testicon.png"));
	}
}
