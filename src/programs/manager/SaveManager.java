package programs.manager;

import java.util.Objects;

import programs.data.SaveData;
import programs.system.SaveUtils;

/**
 * セーブデータの管理をおこなうクラス
 * @author 田淵勇輝
 */
public class SaveManager {
	private static SaveManager instance;
	
	private SaveData saveData;
	
	/**
	 * プライベートコンストラクタ
	 */
	private SaveManager() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static SaveManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new SaveManager();
		}
		return instance;
	}
	/**
	 * セーブデータのゲッター
	 * @see {@link #saveData}
	 * @return セーブデータ
	 */
	public SaveData getSaveData() { return saveData; }
	
	/**
	 * セーブする
	 */
	public void save() {
		SaveUtils.encryptAndSave(saveData);
	}
	
	/**
	 * ロードする
	 */
	public void load() {
		saveData = SaveUtils.decryptAndLoad();
	}
}
