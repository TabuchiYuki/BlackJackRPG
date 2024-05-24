package programs.data;

/**
 * クリックイベントのデータ
 * @author 田淵勇輝
 */
public class ClickEventData {
	private boolean clicked = false;
	private boolean prevClicked = false;
	private boolean clickedDown = false;
	private boolean clickedUp = false;
	
	private Vector2 position;
	private Vector2 area;
	private boolean changeCursor;
	
	/**
	 * コンストラクタ
	 * @param position 位置(左上)
	 * @param area 幅と高さ
	 */
	public ClickEventData(Vector2 position, Vector2 area, boolean changeCursor) {
		this.position = position;
		this.area = area;
		this.changeCursor = changeCursor;
	}
	
	/**
	 * クリック判定のゲッター
	 * @see {@link #clicked}
	 * @return クリック判定
	 */
	public boolean isClicked() { return clicked; }
	/**
	 * クリック下判定のゲッター
	 * @see {@link #clickedDown}
	 * @return クリック下判定
	 */
	public boolean isClickedDown() { return clickedDown; }
	/**
	 * クリック上判定のゲッター
	 * @see {@link #clickedUp}
	 * @return クリック上判定
	 */
	public boolean isClickedUp() { return clickedUp; }
	/**
	 * マウスポインターの表示を変えるかのゲッター
	 * @see {@link #changeCursor}
	 * @return マウスポインターの表示を変えるか
	 */
	public boolean canChangeCursor() { return changeCursor; }
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
	 * マウスポインターの表示を変えるかのゲッター
	 * @see {@link #changeCursor}
	 * @param changeCursor マウスポインターの表示を変えるか
	 */
	public void setChangeCursor(boolean changeCursor) { this.changeCursor = changeCursor; }
	
	/**
	 * 範囲内をクリックされた時に呼ばれる
	 */
	public void clickAreaHandle() {
		clicked = true;
	}
	
	/**
	 * クリックが離された時に呼ばれる
	 */
	public void clickReleasedHandle() {
		clicked = false;
	}
	
	/**
	 * 毎フレーム呼ばれ、クリック情報が更新される
	 */
	public void update() {
		if(clicked) {
			clickedDown = !prevClicked;
			clickedUp = false;
			prevClicked = true;
		} else {
			clickedUp = prevClicked;
			clickedDown = false;
			prevClicked = false;
		}
	}
}
