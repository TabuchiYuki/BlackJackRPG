package programs.battle;

import programs.blackJack.GameRules;
import programs.data.BlackJackResult;
import programs.data.CharacterData;
import programs.data.TextGraphicData;
import programs.manager.SceneManager;
import programs.system.GameObject;

/**
 * バトルシステム
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 小川涼介
 */
public class BattleSystem implements GameObject {
	private final int BATTLE_DISPLAY_OBJECT_INDEX = 1;
	
	private BattleDisplay battleDisplay;
	
	private CharacterData player;
	private CharacterData dealer;
	
	private TextGraphicData playerHpText;
	private TextGraphicData dealerHpText;
	
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
		// ディスプレイの取得
		battleDisplay = (BattleDisplay) SceneManager.getInstance().getScene().getGameObjects().get(BATTLE_DISPLAY_OBJECT_INDEX);
		battleDisplay.showStatus(player.getHp(), player.getAtk(), dealer.getHp(), dealer.getAtk());
	}
	
	@Override
	public void update() {
		
	}

	/**
	 * バトルの結果に基づいてHPを更新する
	 */
	public void updateHP(BlackJackResult bjresult) {
		switch (bjresult) {
		case VICTORY:
			int newDealerHP = dealer.getHp() - player.getAtk();
			dealer.setHp(newDealerHP < 0 ? 0 : newDealerHP);
			dealerHpText.setText(String.format("hp:%d", dealer.getHp()));
			break;
		case DEFEAT:
			int newPlayerHP = player.getHp() - dealer.getAtk();
			player.setHp(newPlayerHP < 0 ? 0 : newPlayerHP);
			playerHpText.setText(String.format("hp:%d", player.getHp()));
			break;
		case DRAW:
			// Do nothing
			break;
		}
	}

}
