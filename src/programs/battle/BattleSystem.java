package programs.battle;

import java.util.Objects;

import programs.blackJack.GameRules;
import programs.data.AnimationType;
import programs.data.BattlePhase;
import programs.data.BlackJackResult;
import programs.data.CharacterData;
import programs.data.ClickEventData;
import programs.data.DifficultyGrade;
import programs.data.master.Card;
import programs.manager.ClickEventManager;
import programs.manager.SaveManager;
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
	private final int DECK_NUMBER_TO_SHUFFLE = 13;
	private final int PASS_SCORE_MAX = 21;
	private final int DEALER_STAND_SCORE = 17;
	private final String HIT_BUTTON_CLICK_EVENT_KEY = "HitButton";
	private final String STAND_BUTTON_CLICK_EVENT_KEY = "StandButton";
	private final String RESULT_CLICK_EVENT_KEY = "ResultClick";
	
	// フィールド
	private int grade;
	
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
	public BattleSystem(double playerHp, double playerAtk, double dealerHp, double dealerAtk, double grade) {
		this.grade = (int) grade;
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
			resultSequence();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 準備フェーズの動作
	 */
	private void setup() {
		switch(process) {
		case 0:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				display.getDealerScoreText().setText("");
				display.getPlayerScoreText().setText("");
				
				if(player.getCards().isEmpty()) {
					process = 1;
				} else if(gameRules.getDeck().getCards().size() <= DECK_NUMBER_TO_SHUFFLE){
					display.startAnimation(AnimationType.BACK_TO_DECK, 0.3d);
				} else {
					display.startAnimation(AnimationType.DISCARD_TO_TALON, 0.3d);
				}
			} else if(display.isAnimationEnd()) {
				process = 1;
			}
			break;
		case 1:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				// デッキが13以下ならデッキをシャッフルする
				if(Objects.isNull(gameRules.getDeck().getCards())){
					gameRules.setupDeck();
				} else if(gameRules.getDeck().getCards().size() <= DECK_NUMBER_TO_SHUFFLE){
					gameRules.setupDeck();
				}
				display.updateDeckNum(gameRules.getDeck().getCards().size());
				
				player.getCards().clear();
				dealer.getCards().clear();
				
				display.startAnimation(AnimationType.WAIT, 0.2d);
			} else if(display.isAnimationEnd()) {
				gameRules.playerHit();
				gameRules.playerHit();
				
				gameRules.dealerHit();
				gameRules.dealerHit();
				
				process = 2;
			}
			break;
		case 2:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				Card[] firstCards = {
					player.getCards().get(0),
					player.getCards().get(1),
					dealer.getCards().get(0),
					dealer.getCards().get(1),
				};
				display.setFirstCards(firstCards);
				
				display.updateDeckNum(gameRules.getDeck().getCards().size());
				display.startAnimation(AnimationType.DISTRIBUTE_FROM_DECK, 0.3d);
			} else if(display.isAnimationEnd()) {
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
			break;
		default:
			System.out.println("不正な値です");
			phase = BattlePhase.PLAYER;
			break;
		}
		
	}
	
	/**
	 * プレイヤーのターンの処理
	 */
	private void playerTurn() {
		if(display.isAnimationEnd()) {
			display.changeScoreColor(player.getScore(), display.getPlayerScoreText());
			display.getPlayerScoreText().setText(Integer.toString(player.getScore()));
			
			hit.setChangeCursor(true);
			stand.setChangeCursor(true);
			
			display.displayButton(true);
			
			// 21以上または山札が0ならヒットを不可にする
			if(player.getScore() >= PASS_SCORE_MAX || gameRules.getDeck().getCards().isEmpty()) {
				hit.setChangeCursor(false);
				display.hideHitButton();
			}
			
			// バーストしたらバースト表示
			if(player.getScore() > PASS_SCORE_MAX) {
				display.getPlayerBust().setShow(true);
			}
		}
		
		if(!display.isAnimating()) {
			if(player.getScore() < PASS_SCORE_MAX && hit.isClickedDown()) {
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
		
		hit.setChangeCursor(false);
		stand.setChangeCursor(false);
		
		display.displayButton(false);
		display.setHitCard(player.getCards().get(player.getCards().size() - 1));
		
		display.updateDeckNum(gameRules.getDeck().getCards().size());
		display.startAnimation(AnimationType.PLAYER_HIT, 0.2d);
	}
	
	/**
	 * プレイヤーのスタンドの処理
	 */
	private void playerStand() {
		hit.setChangeCursor(false);
		stand.setChangeCursor(false);
		
		display.displayButton(false);
		
		display.getAnnounceText().setText("dealer's turn");
		
		phase = BattlePhase.DEALER;
	}
	
	/**
	 * ディーラーのターンの処理
	 */
	private void dealerTurn() {
		switch(process) {
		case 0:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				display.startAnimation(AnimationType.SHOW_DEALERS_CARD, 0.3d);
			} else if(display.isAnimationEnd()) {
				process = 1;
			}
			break;
		case 1:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				// バーストしたらバースト表示
				if(dealer.getScore() > PASS_SCORE_MAX) {
					display.getDealerBust().setShow(true);
				}
				
				display.getDealerScoreText().setText(Integer.toString(dealer.getScore()));
				display.changeScoreColor(dealer.getScore(), display.getDealerScoreText());
				display.startAnimation(AnimationType.WAIT, 0.3d);
			} else if(display.isAnimationEnd()) {
				// スコアが17未満ならヒット、17以上または山札が0ならターン終了
				if(dealer.getScore() < DEALER_STAND_SCORE && !gameRules.getDeck().getCards().isEmpty()) {
					dealerHit();
				} else {
					process = 0;
					phase = BattlePhase.ATTACK;
				}
			}
			break;
		case 2:
			if(display.isAnimationEnd()) {
				process = 1;
			}
			break;
		default:
			System.out.println("不正な値です");
			phase = BattlePhase.ATTACK;
			break;
		}
	}
	
	/**
	 * ディーラーのヒットの処理
	 */
	private void dealerHit() {
		gameRules.dealerHit();
		
		process = 2;
		
		display.updateDeckNum(gameRules.getDeck().getCards().size());
		display.setHitCard(dealer.getCards().get(dealer.getCards().size() - 1));
		display.startAnimation(AnimationType.DEALER_HIT, 0.2d);
	}
	
	/**
	 * 攻撃の処理
	 */
	private void attackSequence() {
		if(!display.isAnimating() && !display.isAnimationEnd()) {
			BlackJackResult bjresult = gameRules.determineWinner(player.getScore(), dealer.getScore());
			updateHP(bjresult);
		} else if(display.isAnimationEnd()) {
			if(player.getHp() <= 0 || dealer.getHp() <= 0) {
				phase = BattlePhase.RESULT;
			} else {
				display.getPlayerBust().setShow(false);
				display.getDealerBust().setShow(false);
				
				phase = BattlePhase.SETUP;
			}
		}
	}
	
	/**
	 * 勝敗結果時の処理
	 */
	private void resultSequence() {
		switch(process) {
		case 0:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				display.startAnimation(AnimationType.WAIT, 0.5d);
			} else if(display.isAnimationEnd()) {
				process = 1;
			}
			break;
		case 1:
			if(!display.isAnimating() && !display.isAnimationEnd()) {
				display.setPlayerWin(dealer.getHp() <= 0);
				
				display.startAnimation(AnimationType.SHOW_RESULT, 0.3d);
			} else if(display.isAnimationEnd()) {
				process = 2;
			}
			break;
		case 2:
			if(result.isClickedUp()) {
				resultClicked();
			}
			break;
		default:
			System.out.println("不正な値を検出");
			break;
		}
	}
	
	/**
	 * 結果を確認してクリックされたときの処理
	 */
	private void resultClicked() {
		if(dealer.getHp() <= 0) {
			DifficultyGrade curtGrade = SaveManager.getInstance().getSaveData().getGrade();
			if(curtGrade == DifficultyGrade.LOW && grade == 0) {
				SaveManager.getInstance().getSaveData().setGrade(DifficultyGrade.MIDDLE);
				SaveManager.getInstance().save();
			} else if(curtGrade == DifficultyGrade.MIDDLE && grade == 1) {
				SaveManager.getInstance().getSaveData().setGrade(DifficultyGrade.HIGH);
			}
		}
		SaveManager.getInstance().save();
		SceneManager.getInstance().loadScene(1);
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
			
			display.setAtk(player.getAtk());
			display.setBjResult(bjresult);
			display.startAnimation(AnimationType.ATTACK_AND_DAMAGE, 0.75d);
			break;
		case DEFEAT:
			int newPlayerHP = player.getHp() - dealer.getAtk();
			player.setHp(newPlayerHP < 0 ? 0 : newPlayerHP);
			
			display.getAnnounceText().setText("dealer's attack!");
			
			display.setAtk(dealer.getAtk());
			display.setBjResult(bjresult);
			display.startAnimation(AnimationType.ATTACK_AND_DAMAGE, 0.75d);
			break;
		case DRAW:
			display.getAnnounceText().setText("draw");
			display.startAnimation(AnimationType.WAIT, 0.3d);
			break;
		}
		display.updateHpDisplay(player.getHp(), dealer.getHp());
	}
}
