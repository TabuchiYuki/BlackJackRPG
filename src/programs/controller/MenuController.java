package programs.controller;

import programs.data.ClickEventData;
import programs.data.DifficultyGrade;
import programs.data.GraphicData;
import programs.manager.ClickEventManager;
import programs.manager.GraphicManager;
import programs.manager.SaveManager;
import programs.manager.SceneManager;
import programs.system.GameObject;
import programs.system.ImageLoader;

/**
 * メニュー画面のコントローラー
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 田淵勇輝
 */
public class MenuController implements GameObject {
	private DifficultyGrade grade;
	private ClickEventData easyButton;
	private ClickEventData normalButton;
	private ClickEventData hardButton;
	
	@Override
	public void init() {
		grade = SaveManager.getInstance().getSaveData().getGrade();
		easyButton = ClickEventManager.getInstance().getClickEventsMap().get("easy");
		normalButton = ClickEventManager.getInstance().getClickEventsMap().get("normal");
		hardButton = ClickEventManager.getInstance().getClickEventsMap().get("hard");
		
		GraphicData normalButtonGra = GraphicManager.getInstance().getGraphicDataList().get(2);
		GraphicData hardButtonGra = GraphicManager.getInstance().getGraphicDataList().get(3);
		
		// 現在のグレードに応じてボタンの表示を変える
		switch(grade) {
		case LOW:
			normalButtonGra.setImage(ImageLoader.getInstance().getImagesMap().get("lock"));
			hardButtonGra.setImage(ImageLoader.getInstance().getImagesMap().get("lock"));
			
			normalButton.setChangeCursor(false);
			hardButton.setChangeCursor(false);
			break;
		case MIDDLE:
			hardButtonGra.setImage(ImageLoader.getInstance().getImagesMap().get("lock"));
			
			hardButton.setChangeCursor(false);
			break;
		case HIGH:
			break;
		default:
			break;
		}
	}
	
	@Override
	public void update() {
		if(easyButton.isClickedUp()) {
			SceneManager.getInstance().loadScene(2);
		}
		
		if(normalButton.isClickedUp() && (grade == DifficultyGrade.MIDDLE || grade == DifficultyGrade.HIGH)) {
			SceneManager.getInstance().loadScene(3);
		}
		
		if(hardButton.isClickedUp() && grade == DifficultyGrade.HIGH) {
			SceneManager.getInstance().loadScene(4);
		}
	}
}
