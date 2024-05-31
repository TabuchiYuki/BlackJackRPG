package programs.data;

/**
 * 戦闘フェーズの列挙型
 * @author 田淵勇輝
 */
public enum BattlePhase {
	/**
	 * 準備フェーズ
	 */
	SETUP,
	/**
	 * プレイヤーのターン
	 */
	PLAYER,
	/**
	 * ディーラーのターン
	 */
	DEALER,
	/**
	 * 攻撃フェーズ
	 */
	ATTACK,
	/**
	 * 戦闘終了
	 */
	RESULT
}
