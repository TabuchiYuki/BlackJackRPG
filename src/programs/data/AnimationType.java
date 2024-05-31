package programs.data;

/**
 * アニメーションの種類
 * @author 田淵勇輝
 */
public enum AnimationType {
	/**
	 * アニメーションせず待つ
	 */
	WAIT,
	/**
	 * デッキから2枚ずつ配分する
	 */
	DISTRIBUTE_FROM_DECK,
	/**
	 * プレイヤーのヒット
	 */
	PLAYER_HIT,
	/**
	 * ディーラーのカードを公開する
	 */
	SHOW_DEALERS_CARD,
	/**
	 * ディーラーのヒット
	 */
	DEALER_HIT,
	/**
	 * 攻撃
	 */
	ATTACK_AND_DAMAGE,
	/**
	 * 手札を捨て札に捨てる
	 */
	DISCARD_TO_TALON,
	/**
	 * 手札と捨て札をデッキに戻す
	 */
	BACK_TO_DECK,
	/**
	 * 結果を表示する
	 */
	SHOW_RESULT
}
