package programs.battle;

import java.awt.Color;

import programs.blackJack.GameRules;
import programs.data.BlackJackResult;
import programs.data.CharacterData;
import programs.data.TextGraphicData;
import programs.data.Vector2;
import programs.manager.GraphicManager;
import programs.system.FontLoader;
import programs.system.GameObject;

/**
 * バトルシステム
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 小川涼介
 */
public class BattleSystem implements GameObject {
	private CharacterData player;
	private CharacterData dealer;
	
	private TextGraphicData playerNameText;
	private TextGraphicData playerHpText;
	private TextGraphicData dealerNameText;
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
		playerNameText = new TextGraphicData(
			"player",
			FontLoader.getInstance().getFonts().get(0),
			64,
			new Vector2(50, 480),
			Color.WHITE
		);
		System.out.println(playerNameText.getFont());
		playerHpText = new TextGraphicData(
			String.format("hp:%d", player.getHp()),
			FontLoader.getInstance().getFonts().get(0),
			64,
			new Vector2(50, 540),
			Color.WHITE
		);
		dealerNameText = new TextGraphicData(
			"dealer",
			FontLoader.getInstance().getFonts().get(0),
			64,
			new Vector2(580, 80),
			Color.WHITE
		);
		dealerHpText = new TextGraphicData(
			String.format("hp:%d", dealer.getHp()),
			FontLoader.getInstance().getFonts().get(0),
			64,
			new Vector2(580, 140),
			Color.WHITE
		);
		
		GraphicManager.getInstance().getTextDataList().add(playerNameText);
		GraphicManager.getInstance().getTextDataList().add(playerHpText);
		GraphicManager.getInstance().getTextDataList().add(dealerNameText);
		GraphicManager.getInstance().getTextDataList().add(dealerHpText);
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
