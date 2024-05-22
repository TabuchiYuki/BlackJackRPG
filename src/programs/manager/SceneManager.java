package programs.manager;

import java.util.List;
import java.util.Objects;

import programs.data.SceneData;
import programs.system.GameObject;

/**
 * シーンの管理をするクラス
 * @author 田淵勇輝
 */
public class SceneManager {
	private static SceneManager instance;
	
	private SceneData scene;
	private boolean loaded = false;
	private boolean firstFrame = false;
	
	/**
	 * プライベートコンストラクタ
	 */
	private SceneManager() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public SceneManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new SceneManager();
		}
		return instance;
	}
	/**
	 * シーンのゲッター
	 * @see {@link #scene}
	 * @return シーン
	 */
	public SceneData getScene() { return scene; }
	/**
	 * シーンの読み込み完了判定のゲッター
	 * @see {@link #loaded}
	 * @return 読み込み完了判定
	 */
	public boolean hasLoaded() { return loaded; }
	/**
	 * 実行されているシーンの最初のフレーム判定のゲッター
	 * @return 最初のフレーム判定
	 */
	public boolean isFirstFrame() { return firstFrame; }
	
	/**
	 * シーンを読み込む
	 * @param index 読み込むシーンのインデックス番号
	 */
	public void loadScene(int index) {
		
	}
	
	/**
	 * ゲームオブジェクトの初期化メソッドを呼び出す
	 */
	public void callInit() {
		List<GameObject> gameObjects = scene.getGameObjects();
		for(int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).init();
		}
	}
	
	/**
	 * ゲームオブジェクトのアップデートメソッドを呼び出す
	 */
	public void callUpdate() {
		List<GameObject> gameObjects = scene.getGameObjects();
		for(int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).update();
		}
	}
}
