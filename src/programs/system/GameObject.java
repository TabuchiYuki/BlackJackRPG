package programs.system;

/**
 * ゲームオブジェクトのインターフェース
 * @author 進藤颯斗
 */
public interface GameObject {
	/**
	 * シーン開始時に呼ばれる
	 */
	default void init() { }
	/**
	 * マイフレームが呼ばれる
	 */
	default void update() { }
}
