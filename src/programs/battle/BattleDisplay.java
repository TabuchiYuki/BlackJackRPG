package programs.battle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import programs.data.AnimationType;
import programs.data.GraphicData;
import programs.data.TextGraphicData;
import programs.data.Vector2;
import programs.manager.GraphicManager;
import programs.system.FontLoader;
import programs.system.GameObject;
import programs.system.ImageLoader;
import programs.system.SystemValue;

/**
 * バトルシーンでの表示やアニメーションを行うクラス
 * @author 田淵勇輝
 */
public class BattleDisplay implements GameObject {
	// 定数
	private final int FONT_INDEX = 0;
	
	private final String CARD_CELL_IMAGE_KEY_NAME = "cards";
	private final String HIT_BUTTON_KEY_NAME = "button_hit";
	private final String STAND_BUTTON_KEY_NAME = "button_stand";
	private final String WIN_IMAGE_KEY_NAME = "win";
	private final String LOSE_IMAGE_KEY_NAME = "lose";
	private final String BUST_IMAGE_KEY_NAME = "bust";
	
	private final int STATUS_TEXT_FONT_SIZE = 48;
	private final int SCORE_TEXT_FONT_SIZE = 64;
	private final int ANNOUNCE_TEXT_FONT_SIZE = 32;
	
	private final int CARD_SPLIT_HORIZONTAL = 13;
	private final int CARD_SPLIT_VERTICAL = 5;
	
	private final int CARD_LAYER_BASE = 1000;
	private final int BUTTON_LAYER_BASE = 2000;
	private final int BUST_LAYER_BASE = 3000;
	private final int RESULT_LAYER_BASE = 4000;
	
	private final Vector2 CENTER_POSITION = new Vector2(400, 300);
	
	private final Vector2 PLAYER_DAMAGE_POSITION = new Vector2(180, 475);
	private final Vector2 DEALER_DAMAGE_POSITION = new Vector2(740, 95);
	
	private final Vector2 PLAYER_TEXT_BASE_POSITION = new Vector2(50, 450);
	private final Vector2 DEALER_TEXT_BASE_POSITION = new Vector2(610, 70);
	private final Vector2 STATUS_TEXT_LINE_SPACE = new Vector2(0, 50);
	
	private final Vector2 ANNOUNCE_TEXT_POSITION = new Vector2(400, 350);
	
	private final Vector2 PLAYER_SCORE_POSITION = new Vector2(400, 430);
	private final Vector2 DEALER_SCORE_POSITION = new Vector2(400, 200);
	
	private final Vector2 DECK_CARD_POSITION = new Vector2(100, 150);
	private final Vector2 PLAYER_CARD_LEFT_POSITION = new Vector2(320, 500);
	private final Vector2 PLAYER_CARD_RIGHT_POSITION = new Vector2(480, 500);
	private final Vector2 DEALER_CARD_LEFT_POSITION = new Vector2(320, 100);
	private final Vector2 DEALER_CARD_RIGHT_POSITION = new Vector2(480, 100);
	private final Vector2 CARD_SCALE = new Vector2(0.3d, 0.3d);
	
	private final Vector2 PLAYER_BUST_POSITION = new Vector2(400, 500);
	private final Vector2 DEALER_BUST_POSITION = new Vector2(400, 100);
	
	private final Vector2 HIT_BUTTON_POSITION = new Vector2(680, 430);
	private final Vector2 STAND_BUTTON_POSITION = new Vector2(680, 540);
	
	private final Color SCORE_NORMAL_COLOR = new Color(64, 127, 255);
	private final Color SCORE_PERFECT_COLOR = new Color(255, 255, 0);
	private final Color SCORE_BUST_COLOR = new Color(100, 100, 100);
	
	// フィールド
	private BufferedImage cardCells;
	
	private GraphicData hitButton;
	private GraphicData standButton;
	private GraphicData playerBust;
	private GraphicData dealerBust;
	private GraphicData result;
	
	private TextGraphicData playerHpText;
	private TextGraphicData dealerHpText;
	private TextGraphicData playerScoreText;
	private TextGraphicData dealerScoreText;
	private TextGraphicData damageText;
	private TextGraphicData announceText;
	
	private AnimationType animType;
	
	private boolean animating = false;
	private boolean animationEnd = false;
	private boolean animated = false;
	
	private double animTime;
	private double timeCounter;
	
	/**
	 * アニメーション中かの判定のゲッター
	 * @see {@link #animating}
	 * @return アニメーション中かの判定
	 */
	public boolean isAnimating() { return animating; }
	/**
	 * アニメーション終了判定のゲッター
	 * @see {@link #animationEnd}
	 * @return アニメーション終了判定
	 */
	public boolean isAnimationEnd() { return animationEnd; }
	/**
	 * プレイヤースコアのテキストのゲッター
	 * @see {@link #playerScoreText}
	 * @return プレイヤースコアのテキスト
	 */
	public TextGraphicData getPlayerScoreText() { return playerScoreText; }
	/**
	 * ディーラースコアのテキストのゲッター
	 * @see {@link #dealerScoreText}
	 * @return ディーラースコアのテキスト
	 */
	public TextGraphicData getDealerScoreText() { return dealerScoreText; }
	/**
	 * アナウンステキストのゲッター
	 * @see {@link #announceText}
	 * @return アナウンステキスト
	 */
	public TextGraphicData getAnnounceText() { return announceText; }
	
	@Override
	public void init() {
		setupGraphic();
	}
	
	@Override
	public void update() {
		if(animating) {
			timeCounter += SystemValue.REFRESH_TIME;
			animated = true;
			
			switch(animType) {
			case WAIT:
				waitOnly();
				break;
			case DISTRIBUTE_FROM_DECK:
				distribution();
				break;
			case BACK_TO_DECK:
				backToDeck();
				break;
			case PLAYER_HIT:
				playerHit();
				break;
			case SHOW_DEALERS_CARD:
				showDealersCard();
				break;
			case DEALER_HIT:
				dealerHit();
				break;
			case ATTACK_AND_DAMAGE:
				attack();
				break;
			case SHOW_RESULT:
				showResult();
				break;
			default:
				break;
			}
		}
		
		if(animated && !animating) {
			animated = false;
			animationEnd = true;
		} else {
			animationEnd = false;
		}
	}
	
	/**
	 * グラフィックの準備
	 */
	public void setupGraphic() {
		cardCells = ImageLoader.getInstance().getImagesMap().get(CARD_CELL_IMAGE_KEY_NAME);
		damageText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			Vector2.ZERO,
			Color.RED,
			false
		);
		playerScoreText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			SCORE_TEXT_FONT_SIZE,
			PLAYER_SCORE_POSITION,
			Color.GRAY,
			true
		);
		dealerScoreText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			SCORE_TEXT_FONT_SIZE,
			DEALER_SCORE_POSITION,
			Color.GRAY,
			true
		);
		announceText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			ANNOUNCE_TEXT_FONT_SIZE,
			ANNOUNCE_TEXT_POSITION,
			Color.WHITE,
			true
		);
		GraphicManager.getInstance().getTextDataList().add(damageText);
		GraphicManager.getInstance().getTextDataList().add(playerScoreText);
		GraphicManager.getInstance().getTextDataList().add(dealerScoreText);
		GraphicManager.getInstance().getTextDataList().add(announceText);
		
		hitButton = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(HIT_BUTTON_KEY_NAME),
			BUTTON_LAYER_BASE,
			HIT_BUTTON_POSITION,
			Vector2.ONE,
			0,
			Vector2.ZERO,
			false
		);
		standButton = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(STAND_BUTTON_KEY_NAME),
			BUTTON_LAYER_BASE + 1,
			STAND_BUTTON_POSITION,
			Vector2.ONE,
			0,
			Vector2.ZERO,
			false
		);
		playerBust = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(BUST_IMAGE_KEY_NAME),
			BUST_LAYER_BASE,
			PLAYER_BUST_POSITION,
			Vector2.ONE,
			0,
			Vector2.ZERO,
			false
		);
		dealerBust = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(BUST_IMAGE_KEY_NAME),
			BUST_LAYER_BASE + 1,
			DEALER_BUST_POSITION,
			Vector2.ONE,
			0,
			Vector2.ZERO,
			false
		);
		result = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(WIN_IMAGE_KEY_NAME),
			RESULT_LAYER_BASE + 1,
			CENTER_POSITION,
			Vector2.ONE,
			0,
			Vector2.ZERO,
			false
		);
		GraphicData deckCard = new GraphicData(
			ImageLoader.getInstance().imageSplit(cardCells, CARD_SPLIT_HORIZONTAL, CARD_SPLIT_VERTICAL, 0, 4),
			CARD_LAYER_BASE,
			DECK_CARD_POSITION,
			CARD_SCALE,
			0,
			Vector2.ZERO,
			true
		);
		
		GraphicManager.getInstance().getGraphicDataList().add(hitButton);
		GraphicManager.getInstance().getGraphicDataList().add(standButton);
		GraphicManager.getInstance().getGraphicDataList().add(playerBust);
		GraphicManager.getInstance().getGraphicDataList().add(dealerBust);
		GraphicManager.getInstance().getGraphicDataList().add(result);
		GraphicManager.getInstance().getGraphicDataList().add(deckCard);
		
		GraphicManager.getInstance().sortLayer();
	}
	
	/**
	 * キャラクターのステータスを表示する
	 * @param playerHp プレイヤーのHP
	 * @param playerAtk プレイヤーの攻撃力
	 * @param dealerHp ディーラーのHP
	 * @param dealerAtk ディーラーの攻撃力
	 */
	public void showStatus(int playerHp, int playerAtk, int dealerHp, int dealerAtk) {
		TextGraphicData playerNameText = new TextGraphicData(
			"player",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_TEXT_BASE_POSITION,
			Color.WHITE,
			false
		);
		playerHpText = new TextGraphicData(
			String.format("hp:%d", playerHp),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE),
			Color.WHITE,
			false
		);
		TextGraphicData playerAtkText = new TextGraphicData(
			String.format("atk:%d", playerAtk),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE.multiply(2)),
			Color.WHITE,
			false
		);
		TextGraphicData dealerNameText = new TextGraphicData(
			"dealer",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION,
			Color.WHITE,
			false
		);
		dealerHpText = new TextGraphicData(
			String.format("hp:%d", dealerHp),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE),
			Color.WHITE,
			false
		);
		TextGraphicData dealerAtkText = new TextGraphicData(
			String.format("atk:%d", dealerAtk),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE.multiply(2)),
			Color.WHITE,
			false
		);
		
		GraphicManager.getInstance().getTextDataList().add(playerNameText);
		GraphicManager.getInstance().getTextDataList().add(playerHpText);
		GraphicManager.getInstance().getTextDataList().add(playerAtkText);
		GraphicManager.getInstance().getTextDataList().add(dealerNameText);
		GraphicManager.getInstance().getTextDataList().add(dealerHpText);
		GraphicManager.getInstance().getTextDataList().add(dealerAtkText);
	}
	
	/**
	 * ボタンの表示変更
	 * @param show 表示・非表示
	 */
	public void displayButton(boolean show) {
		hitButton.setShow(show);
		standButton.setShow(show);
	}
	
	/**
	 * ヒットボタンだけを隠す
	 */
	public void hideHitButton() {
		hitButton.setShow(false);
	}
	
	/**
	 * スコア表示の色変更
	 * @param score
	 * @param text
	 */
	public void changeScoreColor(int score, TextGraphicData text) {
		if(score < 21) {
			text.setColor(SCORE_NORMAL_COLOR);
		} else if(score == 21) {
			text.setColor(SCORE_PERFECT_COLOR);
		} else {
			text.setColor(SCORE_BUST_COLOR);
		}
	}
	
	/**
	 * HPの表示を更新する
	 * @param playerHp プレイヤーのHP
	 * @param dealerHp ディーラーのHP
	 */
	public void updateHpDisplay(int playerHp, int dealerHp) {
		playerHpText.setText(String.format("hp:%d", playerHp));
		dealerHpText.setText(String.format("hp:%d", dealerHp));;
	}
	
	/**
	 * アニメーションを開始する
	 * @param animationType アニメーションの種類
	 * @param animationTime アニメーションの時間
	 */
	public void startAnimation(AnimationType animationType, double animationTime) {
		animType = animationType;
		animTime = animationTime;
		timeCounter = 0.0d;
		
		animating = true;
	}
	
	/**
	 * アニメーションせずに待つだけ
	 */
	private void waitOnly() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * 手札の分配アニメーション
	 */
	private void distribution() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * 手札をデッキに戻すアニメーション
	 */
	private void backToDeck() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * プレイヤーのヒットのアニメーション
	 */
	private void playerHit() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * ディーラーのカードを公開するアニメーション
	 */
	private void showDealersCard() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * ディーラーのヒットのアニメーション
	 */
	private void dealerHit() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * 攻撃のアニメーション
	 */
	private void attack() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
	
	/**
	 * 戦闘結果の表示アニメーション
	 */
	private void showResult() {
		if(animTime <= timeCounter) {
			animating = false;
		}
	}
}
