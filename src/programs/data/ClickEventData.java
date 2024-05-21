package programs.data;

import java.util.function.Consumer;

public class ClickEventData {
	private Vector2 position;
	private Vector2 area;
	private Consumer<Void> event;
	
	/**
	 * コンストラクタ
	 * @param position 位置(左上)
	 * @param area 幅と高さ
	 * @param event 呼び出すイベント
	 */
	public ClickEventData(Vector2 position, Vector2 area, Consumer<Void> event) {
		this.position = position;
		this.area = area;
		this.event = event;
	}
	
	/**
	 * 位置のゲッター
	 * @see {@link #position}
	 * @return 位置(左上)
	 */
	public Vector2 getPosition() { return position; }
	/**
	 * 幅と高さのゲッター
	 * @see {@link #area}
	 * @return 幅と高さ
	 */
	public Vector2 getArea() { return area; }
	
	/**
	 * 位置のセッター
	 * @see {@link #position}
	 * @param position 位置(左上)
	 */
	public void setPoision(Vector2 position) { this.position = position; }
	/**
	 * 幅と高さのセッター
	 * @see {@link area}
	 * @param area 幅と高さ
	 */
	public void setArea(Vector2 area) { this.area = area; }
	
	/**
	 * イベントを呼び出す
	 */
	public void callEvent() {
		event.accept(null);
	}
}
