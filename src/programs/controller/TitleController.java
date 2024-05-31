package programs.controller;

import programs.data.ClickEventData;
import programs.manager.ClickEventManager;
import programs.manager.SceneManager;
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
		clickEvent = ClickEventManager.getInstance().getClickEventsMap().get("TitleClick");
	}
	
	@Override
	public void update() {
		if(clickEvent.isClickedUp()) {
			SceneManager.getInstance().loadScene(1);
		}
	}
}
