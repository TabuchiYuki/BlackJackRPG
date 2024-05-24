package programs.controller;

import programs.data.ClickEventData;
import programs.manager.ClickEventManager;
import programs.system.GameObject;

/**
 * タイトル画面のコントローラー
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 田淵勇輝
 */
public class TitleController implements GameObject {
	private ClickEventData clickEvent;
	
	@Override
	public void init() {
		System.out.println("title");
		clickEvent = ClickEventManager.getInstance().getClickEventsMap().get("TitleClick");
	}
	
	@Override
	public void update() {
		if(clickEvent.isClickedDown()) {
			System.out.println("clicked Down");
		}
		if(clickEvent.isClicked()) {
			System.out.println("clicking");
		}
		if(clickEvent.isClickedUp()) {
			System.out.println("clicked Up");
		}
	}
}
