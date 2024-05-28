package programs.battle;

import programs.blackJack.GameRules;
import programs.data.BattlePhase;
import programs.data.BlackJackResult;
import programs.data.CharacterData;
import programs.data.ClickEventData;
import programs.manager.ClickEventManager;
import programs.manager.SceneManager;
import programs.system.GameObject;

/**
 * バトルシステム
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 小川涼介
 */
public class BattleSystem implements GameObject {
	// 定数
	private final int BATTLE_DISPLAY_OBJECT_INDEX = 1;
	private final String HIT_BUTTON_CLICK_EVENT_KEY = "HitButton";
	private final String STAND_BUTTON_CLICK_EVENT_KEY = "StandButton";
	private final String RESULT_CLICK_EVENT_KEY = "ResultClick";
	
	// フィールド
	private BattleDisplay display;
	
	private CharacterData player;
	private CharacterData dealer;
	
	private ClickEventData hit;
	private ClickEventData stand;
	private ClickEventData result;
	
	private GameRules gameRules;
	
	private BattlePhase phase = BattlePhase.SETUP;

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
		display = (BattleDisplay) SceneManager.getInstance().getScene().getGameObjects().get(BATTLE_DISPLAY_OBJECT_INDEX);
		
		display.showStatus(player.getHp(), player.getAtk(), dealer.getHp(), dealer.getAtk());
		
		// ブラックジャックルールのインスタンス化
		gameRules = new GameRules(player, dealer);
		
		// クリックイベントの取得
		hit = ClickEventManager.getInstance().getClickEventsMap().get(HIT_BUTTON_CLICK_EVENT_KEY);
		stand = ClickEventManager.getInstance().getClickEventsMap().get(STAND_BUTTON_CLICK_EVENT_KEY);
		result = ClickEventManager.getInstance().getClickEventsMap().get(RESULT_CLICK_EVENT_KEY);
		
		hit.setChangeCursor(false);
		stand.setChangeCursor(false);
	}
	
	@Override
	public void update() {
		switch(phase) {
		case SETUP:
			setup();
			break;
		case PLAYER:
			if(player.getScore() < 21 && hit.isClickedDown()) {
				playerHit();
			}
			if(stand.isClickedDown()) {
				playerStand();
			}
			break;
		case DEALER:
			if(dealer.getScore() < 17) {
				dealerHit();
			} else {
				phase = BattlePhase.ATTACK;
			}
			break;
		case ATTACK:
			BlackJackResult bjresult = gameRules.determineWinner(player.getScore(), dealer.getScore());
			updateHP(bjresult);
			if(player.getHp() <= 0 || dealer.getHp() <= 0) {
				phase = BattlePhase.RESULT;
			} else {
				phase = BattlePhase.SETUP;
			}
			break;
		case RESULT:
			break;
		default:
			break;
		}
	}
	
	/**
	 * 準備フェーズの動作
	 */
	private void setup() {
		gameRules.setupDeck();
		
		gameRules.playerHit();
		gameRules.playerHit();
		
		gameRules.dealerHit();
		gameRules.dealerHit();
		
		display.getDealerScoreText().setText("");
		display.getPlayerScoreText().setText(Integer.toString(player.getScore()));
		display.changeScoreColor(player.getScore(), display.getPlayerScoreText());
		
		display.displayButton(true);
		if(player.getScore() < 21) {
			hit.setChangeCursor(true);
		} else {
			hit.setChangeCursor(false);
			display.hideHitButton();
		}
		stand.setChangeCursor(true);
		phase = BattlePhase.PLAYER;
	}
	
	/**
	 * プレイヤーのヒットの処理
	 */
	private void playerHit() {
		gameRules.playerHit();
		
		display.changeScoreColor(player.getScore(), display.getPlayerScoreText());
		display.getPlayerScoreText().setText(Integer.toString(player.getScore()));
		
		if(player.getScore() >= 21) {
			display.hideHitButton();
		}
	}
	
	/**
	 * プレイヤーのスタンドの処理
	 */
	private void playerStand() {
		hit.setChangeCursor(false);
		stand.setChangeCursor(false);
		
		display.displayButton(false);
		
		display.getDealerScoreText().setText(Integer.toString(dealer.getScore()));
		display.changeScoreColor(dealer.getScore(), display.getDealerScoreText());
		
		phase = BattlePhase.DEALER;
	}
	
	/**
	 * ディーラーのヒットの処理
	 */
	private void dealerHit() {
		gameRules.dealerHit();
		
		display.getDealerScoreText().setText(Integer.toString(dealer.getScore()));
		display.changeScoreColor(dealer.getScore(), display.getDealerScoreText());
	}

	/**
	 * バトルの結果に基づいてHPを更新する
	 * @param bjresult ブラックジャックの結果
	 */
	private void updateHP(BlackJackResult bjresult) {
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
		display.updateHpDisplay(player.getHp(), dealer.getHp());
	}
}
