package programs.system;

/**
 * ゲームのシステム用変数を持つクラス
 * @author 田淵勇輝
 */
public class SystemValue {
	/**
	 * ウィンドウタイトルなどに表示するゲームタイトル
	 */
	public static final String GAME_TITLE = "「21」～ギャンブラーの戦い～";
	/**
	 * リフレッシュレート
	 */
	public static final int REFRESH_RATE = 60;
	/**
	 * 単位リフレッシュフレームの経過時間
	 */
	public static final double REFRESH_TIME = 1.0d / (double) REFRESH_RATE;
	/**
	 * ウィンドウの幅
	 */
	public static final int WINDOW_WIDTH = 800;
	/**
	 * ウィンドウの高さ
	 */
	public static final int WINDOW_HEIGHT = 600;
	/**
	 * デバッグの有効化
	 */
	public static final boolean DEBUG = false;
	
	/**
	 * プライベートコンストラクタを用い、インスタンス化を制限
	 */
	private SystemValue() { }
}
