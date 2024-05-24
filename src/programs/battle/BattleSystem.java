package programs.battle;

import programs.blackJack.GameRules;
import programs.data.BlackJackResult;
import programs.data.CharacterData;
import programs.system.GameObject;

/**
 * バトルシステム
 * @author 小川涼介
 */
public class BattleSystem implements GameObject {
	private CharacterData player;
	private CharacterData dealer;
	
	private GameRules gameRules;

	/**
	 * コンストラクタ
	 * @param playerHp プレイヤーの体力
	 * @param playerAtk プレイヤーの攻撃力
	 * @param dealerHp ディーラーの体力
	 * @param dealerAtk ディーラーの攻撃力
	 */
	public BattleSystem(double playerHp, double playerAtk, double dealerHp, double dealerAtk) {
		player = new CharacterData((int) playerHp, (int) playerAtk);
		dealer = new CharacterData((int) dealerHp, (int) dealerAtk);
		
	}
	
	@Override
	public void init() {
		System.out.println(player.getHp());
		System.out.println(player.getAtk());
		System.out.println(dealer.getHp());
		System.out.println(dealer.getAtk());
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
