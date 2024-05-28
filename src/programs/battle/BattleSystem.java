package programs.battle;

import programs.blackJack.GameRules;
import programs.data.AnimationType;
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
	
	private int process = 0;

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
			playerTurn();
			break;
		case DEALER:
			dealerTurn();
			break;
		case ATTACK:
			attackSequence();
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
		if(!display.isAnimating() && !display.isAnimationEnd()) {
			gameRules.setupDeck();
			display.startAnimation(AnimationType.WAIT, 1d);
		} else if(display.isAnimationEnd()) {
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
			
			display.getAnnounceText().setText("your turn");
			
			process = 0;
			phase = BattlePhase.PLAYER;
		}
	}
	
	/**
	 * プレイヤーのターンの処理
	 */
	private void playerTurn() {
		if(display.isAnimationEnd()) {
			display.changeScoreColor(player.getScore(), display.getPlayerScoreText());
			display.getPlayerScoreText().setText(Integer.toString(player.getScore()));
			
			display.displayButton(true);
			if(player.getScore() >= 21) {
				display.hideHitButton();
			}
		}
		
		if(!display.isAnimating()) {
			if(player.getScore() < 21 && hit.isClickedDown()) {
				playerHit();
			}
			if(stand.isClickedDown()) {
				playerStand();
			}
		}
	}
	
	/**
	 * プレイヤーのヒットの処理
	 */
	private void playerHit() {
		gameRules.playerHit();
		
		display.displayButton(false);
		display.startAnimation(AnimationType.PLAYER_HIT, 0.5d);
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
		
		display.getAnnounceText().setText("dealer's turn");
		
		phase = BattlePhase.DEALER;
	}
	
	/**
	 * ディーラーのターンの処理
	 */
	private void dealerTurn() {
		if(process == 0) {
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				display.startAnimation(AnimationType.WAIT, 0.5d);
			} else if(display.isAnimationEnd()) {
				if(dealer.getScore() < 17) {
					dealerHit();
				} else {
					phase = BattlePhase.ATTACK;
				}
			}
		} else if(process == 1) {
			if(display.isAnimationEnd()) {
				process = 0;
			}
		}
	}
	
	/**
	 * ディーラーのヒットの処理
	 */
	private void dealerHit() {
		gameRules.dealerHit();
		
		display.getDealerScoreText().setText(Integer.toString(dealer.getScore()));
		display.changeScoreColor(dealer.getScore(), display.getDealerScoreText());
		
		process = 1;
		display.startAnimation(AnimationType.DEALER_HIT, 0.5d);
	}
	
	/**
	 * 攻撃の処理
	 */
	private void attackSequence() {
		BlackJackResult bjresult = gameRules.determineWinner(player.getScore(), dealer.getScore());
		updateHP(bjresult);
		if(player.getHp() <= 0 || dealer.getHp() <= 0) {
			phase = BattlePhase.RESULT;
		} else {
			phase = BattlePhase.SETUP;
		}
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
			display.getAnnounceText().setText("your attack!");
			break;
		case DEFEAT:
			int newPlayerHP = player.getHp() - dealer.getAtk();
			player.setHp(newPlayerHP < 0 ? 0 : newPlayerHP);
			display.getAnnounceText().setText("dealer's attack!");
			break;
		case DRAW:
			display.getAnnounceText().setText("draw");
			break;
		}
		display.updateHpDisplay(player.getHp(), dealer.getHp());
	}
}
