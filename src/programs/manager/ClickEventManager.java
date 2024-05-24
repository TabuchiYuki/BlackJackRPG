package programs.manager;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JFrame;

import programs.data.ClickEventData;

/**
 * クリックイベントを制御するクラス
 * @author 田淵勇輝
 */
public class ClickEventManager {
	private static ClickEventManager instance;
	
	private JFrame frame;
	private Map<String,ClickEventData> clickEventsMap = new HashMap<>();
	
	/**
	 * プライベートコンストラクタ
	 */
	private ClickEventManager() {
		frame = WindowManager.getInstance().getFrame();
		initMouseListener();
	}
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static ClickEventManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new ClickEventManager();
		}
		return instance;
	}
	/**
	 * クリックイベントデータのマップのゲッター
	 * @see {@link clickEventsMap}
	 * @return クリックイベントデータのマップ
	 */
	public Map<String,ClickEventData> getClickEventsMap() { return clickEventsMap; }
	
	/**
	 * マウスリスナーを初期化
	 */
	private void initMouseListener() {
		// マウスリスナー追加
		frame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				callClickHandle(e);
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				callReleaseHandle();
			}
		});
		
		// マウスモーションリスナー追加
		frame.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				changeMouseCursor(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) { }
		});
	}
	
	/**
	 * クリックされた時のハンドルを呼ぶ
	 * @param e マウスイベント
	 */
	private void callClickHandle(MouseEvent e) {
		clickEventsMap.forEach((k, v) -> {
			Rectangle eventArea = new Rectangle(
				(int) v.getPosition().getX(), (int) v.getPosition().getY(),
				(int) v.getArea().getX(), (int) v.getArea().getY()
			);
			
			if(eventArea.contains(e.getPoint())) {
				v.clickAreaHandle();
			}
		});
	}
	
	/**
	 * クリックが離された時のハンドルを呼ぶ
	 */
	private void callReleaseHandle() {
		clickEventsMap.forEach((k, v) -> {
			v.clickReleasedHandle();
		});
	}
	
	/**
	 * カーソル表示変更
	 * @param e マウスイベント
	 */
	private void changeMouseCursor(MouseEvent e) {
		clickEventsMap.forEach((k, v) -> {
			Rectangle eventArea = new Rectangle(
				(int) v.getPosition().getX(), (int) v.getPosition().getY(),
				(int) v.getArea().getX(), (int) v.getArea().getY()
			);
			
			if(v.canChangeCursor() && eventArea.contains(e.getPoint())) {
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			} else {
				frame.setCursor(Cursor.getDefaultCursor());
			}
		});
	}
}
