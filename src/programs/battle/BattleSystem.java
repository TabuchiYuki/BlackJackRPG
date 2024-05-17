package programs.battle;

import programs.data.CharacterData;

/**
 * バトルシステム
 * @author 小川涼介
 */
public class BattleSystem {
	private CharacterData player;
	private CharacterData dealer;

	/**
	 * コンストラクタ
	 * @param player プレイヤーデータ
	 * @param dealer ディーラーデータ
	 */
	public BattleSystem(CharacterData player, CharacterData dealer) {
		this.player = player;
		this.dealer = dealer;
	}

	/**
	 * バトルの結果に基づいてHPを更新する
	 */
	public void updateHP() {
		if (player.getScore() > dealer.getScore()) {
			int newHP = dealer.getHp() - player.getAtk();
			dealer.setHp(newHP < 0 ? 0 : newHP);
		} else {
			int newHP = player.getHp() - dealer.getAtk();
			player.setHp(newHP < 0 ? 0 : newHP);
		}
	}
}
