package programs.battle;

import programs.data.BlackJackResult;
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
	public void updateHP(BlackJackResult bjresult) {
		switch (bjresult) {
		case VICTORY:
			int newDealerHP = dealer.getHp() - player.getAtk();
			dealer.setHp(newDealerHP < 0 ? 0 : newDealerHP);
			break;
		case DEFEAT:
			int newPlayerHP = player.getHp() - dealer.getAtk();
			player.setHp(newPlayerHP < 0 ? 0 : newPlayerHP);
			break;
		case DRAW:
			// Do nothing
			break;
		}
	}

}
