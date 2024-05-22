package programs.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import programs.system.GameObject;

/**
 * シーンのデータ
 * @author 田淵勇輝
 */
public class SceneData {
	private List<GameObject> gameObjects = new ArrayList<>();
	
	/**
	 * ゲームオブジェクトのリストのゲッター
	 * @see {@link #gameObjects}
	 * @return ゲームオブジェクトのリスト
	 */
	public List<GameObject> getGameObjects() { return gameObjects; }
	
	/**
	 * シーンのデータをロードする
	 * @param Workbook Excelデータ
	 */
	public void loadData(Workbook wb) {
		
	}
}
