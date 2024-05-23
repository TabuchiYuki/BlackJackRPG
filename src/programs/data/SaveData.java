package programs.data;

/**
 * セーブデータ
 */
public class SaveData {
	private DifficultyGrade grade;
	
	/**
	 * コンストラクタ
	 * 初期化時は初級になる
	 */
	public SaveData() {
		grade = DifficultyGrade.LOW;
	}
	
	/**
	 * 挑戦可能な難易度のゲッター
	 * @see {@link #grade}
	 * @return 挑戦可能な難易度
	 */
	public DifficultyGrade getGrade() { return grade; }
	
	/**
	 * 挑戦可能な難易度のセッター
	 * @see {@link #grade}
	 * @return 挑戦可能な難易度
	 */
	public void setGrade(DifficultyGrade grade) { this.grade = grade; }
}
