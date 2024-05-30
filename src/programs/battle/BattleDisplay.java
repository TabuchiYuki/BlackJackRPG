package programs.battle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import programs.data.AnimationType;
import programs.data.BlackJackResult;
import programs.data.GraphicData;
import programs.data.TextGraphicData;
import programs.data.Vector2;
import programs.data.master.Card;
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
	private final int DECK_NUMBER_FONT_SIZE = 48;
	private final int ANNOUNCE_TEXT_FONT_SIZE = 32;
	
	private final int CARD_SPLIT_HORIZONTAL = 13;
	private final int CARD_SPLIT_VERTICAL = 5;
	
	private final double TALON_CARD_ROTATION = 90.0d;
	
	private final int CARD_LAYER_BASE = 1000;
	private final int BUTTON_LAYER_BASE = 2000;
	private final int BUST_LAYER_BASE = 3000;
	private final int RESULT_LAYER_BASE = 4000;
	private final int PLAYER_CARD_RELATIVE_LAYER_BASE = 100;
	private final int DEALER_CARD_RELATIVE_LAYER_BASE = 200;
	
	private final Vector2 CENTER_POSITION = new Vector2(400, 300);
	
	private final Vector2 PLAYER_DAMAGE_POSITION = new Vector2(180, 475);
	private final Vector2 DEALER_DAMAGE_POSITION = new Vector2(740, 95);
	
	private final Vector2 PLAYER_TEXT_BASE_POSITION = new Vector2(50, 450);
	private final Vector2 DEALER_TEXT_BASE_POSITION = new Vector2(610, 70);
	private final Vector2 STATUS_TEXT_LINE_SPACE = new Vector2(0, 50);
	
	private final Vector2 DECK_NUMBER_TEXT_POSITION = new Vector2(80, 200);
	
	private final Vector2 ANNOUNCE_TEXT_POSITION = new Vector2(400, 350);
	
	private final Vector2 PLAYER_SCORE_POSITION = new Vector2(400, 430);
	private final Vector2 DEALER_SCORE_POSITION = new Vector2(400, 200);
	
	private final Vector2 DECK_CARD_POSITION = new Vector2(80, 300);
	private final Vector2 TALON_CARD_POSITION = new Vector2(100, 80);
	private final Vector2 PLAYER_CARD_LEFT_POSITION = new Vector2(320, 500);
	private final Vector2 PLAYER_CARD_RIGHT_DISTANCE = new Vector2(160, 0);
	private final Vector2 DEALER_CARD_LEFT_POSITION = new Vector2(320, 100);
	private final Vector2 DEALER_CARD_RIGHT_DISTANCE = new Vector2(160, 0);
	private final Vector2 CARD_SCALE = new Vector2(0.3d, 0.3d);
	
	private final Vector2 PLAYER_BUST_POSITION = new Vector2(400, 500);
	private final Vector2 DEALER_BUST_POSITION = new Vector2(400, 100);
	private final Vector2 BUST_SCALE = new Vector2(0.5d, 0.5d);
	
	private final Vector2 HIT_BUTTON_POSITION = new Vector2(680, 430);
	private final Vector2 STAND_BUTTON_POSITION = new Vector2(680, 540);
	
	private final Vector2 FLIP_CARD_MAX_SHEAR = new Vector2(0, 0.3d);
	
	private final Color SCORE_NORMAL_COLOR = new Color(64, 127, 255);
	private final Color SCORE_PERFECT_COLOR = new Color(255, 255, 0);
	private final Color SCORE_BUST_COLOR = new Color(100, 100, 100);
	
	// フィールド
	private BufferedImage cardCells;
	private BufferedImage cardBack;
	
	private GraphicData talon;
	private GraphicData hitButton;
	private GraphicData standButton;
	private GraphicData playerBust;
	private GraphicData dealerBust;
	private GraphicData result;
	
	private TextGraphicData playerHpText;
	private TextGraphicData dealerHpText;
	private TextGraphicData playerScoreText;
	private TextGraphicData dealerScoreText;
	private TextGraphicData deckNumText;
	private TextGraphicData damageText;
	private TextGraphicData announceText;
	
	private List<GraphicData> playerCards = new ArrayList<>();
	private List<GraphicData> dealerCards = new ArrayList<>();
	
	private Card[] firstCards = new Card[4];
	private Card hitCard;
	private int atk;
	private boolean playerWin;
	
	private List<Vector2> tmpVector = new ArrayList<>();
	private boolean tmpFlag;
	
	private AnimationType animType;
	
	private boolean animating = false;
	private boolean animationEnd = false;
	private boolean animated = false;
	
	private double animTime;
	private double timeCounter;
	
	private BlackJackResult bjResult;
	
	/**
	 * プレイヤーのバースト表示のゲッター
	 * @see {@link #playerBust}
	 * @return プレイヤーのバースト表示
	 */
	public GraphicData getPlayerBust() { return playerBust; }
	/**
	 * ディーラーのバースト表示のゲッター
	 * @see {@link #dealerBust}
	 * @return ディーラーのバースト表示
	 */
	public GraphicData getDealerBust() { return dealerBust; }
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
	 * 最初に分配されるカードのセッター
	 * @see {@link #firstCards}
	 * @param firstCards 最初に分配されるカード
	 */
	public void setFirstCards(Card[] firstCards) { this.firstCards = firstCards; }
	/**
	 * ヒットしたカードのデータのセッター
	 * @see {@link #hitCard}
	 * @param hitCard ヒットしたカードのデータ
	 */
	public void setHitCard(Card hitCard) { this.hitCard = hitCard; }
	/**
	 * 攻撃力のセッター
	 * @see {@link #atk}
	 * @param atk 攻撃力
	 */
	public void setAtk(int atk) { this.atk = atk; }
	/**
	 * ブラックジャックの結果のセッター
	 * @see {@link #bjResult}
	 * @param bjResult ブラックジャックの結果
	 */
	public void setBjResult(BlackJackResult bjResult) { this.bjResult = bjResult; }
	/**
	 * プレイヤー勝利判定のセッター
	 * @see {@link #playerWin}
	 * @param playerWin プレイヤー勝利判定
	 */
	public void setPlayerWin(boolean playerWin) { this.playerWin = playerWin; }
	
	@Override
	public void init() {
		setupGraphic();
	}
	
	@Override
	public void update() {
		animationUpdater();
	}
	
	/**
	 * グラフィックの準備
	 */
	public void setupGraphic() {
		cardCells = ImageLoader.getInstance().getImagesMap().get(CARD_CELL_IMAGE_KEY_NAME);
		cardBack = ImageLoader.getInstance().imageSplit(cardCells, CARD_SPLIT_HORIZONTAL, CARD_SPLIT_VERTICAL, 0, 4);
		
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
		deckNumText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			DECK_NUMBER_FONT_SIZE,
			DECK_NUMBER_TEXT_POSITION,
			Color.WHITE,
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
		GraphicManager.getInstance().getTextDataList().add(deckNumText);
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
			BUST_SCALE,
			0,
			Vector2.ZERO,
			false
		);
		dealerBust = new GraphicData(
			ImageLoader.getInstance().getImagesMap().get(BUST_IMAGE_KEY_NAME),
			BUST_LAYER_BASE + 1,
			DEALER_BUST_POSITION,
			BUST_SCALE,
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
			cardBack,
			CARD_LAYER_BASE,
			DECK_CARD_POSITION,
			CARD_SCALE,
			0,
			Vector2.ZERO,
			true
		);
		talon = new GraphicData(
			cardBack,
			CARD_LAYER_BASE + 1,
			TALON_CARD_POSITION,
			CARD_SCALE,
			TALON_CARD_ROTATION,
			Vector2.ZERO,
			false
		);
		
		GraphicManager.getInstance().getGraphicDataList().add(hitButton);
		GraphicManager.getInstance().getGraphicDataList().add(standButton);
		GraphicManager.getInstance().getGraphicDataList().add(playerBust);
		GraphicManager.getInstance().getGraphicDataList().add(dealerBust);
		GraphicManager.getInstance().getGraphicDataList().add(result);
		GraphicManager.getInstance().getGraphicDataList().add(deckCard);
		GraphicManager.getInstance().getGraphicDataList().add(talon);
		
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
	 * デッキの残り枚数を更新する
	 * @param num 枚数
	 */
	public void updateDeckNum(int num) {
		deckNumText.setText(Integer.toString(num));
	}
	
	/**
	 * アニメーションの更新を行う
	 */
	private void animationUpdater() {
		if(animating) {
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
			case DISCARD_TO_TALON:
				discard();
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
			
			timeCounter += SystemValue.REFRESH_TIME;
		}
		
		if(animated && !animating) {
			// アニメーション終了フレーム
			tmpVector.clear();
			tmpFlag = false;
			animated = false;
			animationEnd = true;
		} else {
			animationEnd = false;
		}
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
		Vector2[] targetPos = {
			PLAYER_CARD_LEFT_POSITION,
			PLAYER_CARD_LEFT_POSITION.add(PLAYER_CARD_RIGHT_DISTANCE),
			DEALER_CARD_LEFT_POSITION,
			DEALER_CARD_LEFT_POSITION.add(DEALER_CARD_RIGHT_DISTANCE),
		};
		
		if(timeCounter == 0) {
			// 手札の画像を追加
			for(int i = 0; i < 4; i++) {
				int layer;
				
				if(i < 2) {
					layer = CARD_LAYER_BASE + PLAYER_CARD_RELATIVE_LAYER_BASE + playerCards.size();
				} else {
					layer = CARD_LAYER_BASE + DEALER_CARD_RELATIVE_LAYER_BASE + dealerCards.size();
				}
				
				BufferedImage cardImg;
				if(i == 3) {
					cardImg = cardBack;
				} else {
					cardImg = splitCardImage(firstCards[i]);
				}
				
				GraphicData card = new GraphicData(
					cardImg,
					layer,
					DECK_CARD_POSITION,
					CARD_SCALE,
					0,
					Vector2.ZERO,
					true
				);
				
				if(i < 2) {
					playerCards.add(card);
				} else {
					dealerCards.add(card);
				}
				
				GraphicManager.getInstance().getGraphicDataList().add(card);
			}
			GraphicManager.getInstance().sortLayer();
		} else if(animTime <= timeCounter) {
			// 最終的なポジションに設置
			for(int i = 0; i < 4; i++) {
				if(i < 2) {
					playerCards.get(i).setPosition(targetPos[i]);
				} else {
					dealerCards.get(i - 2).setPosition(targetPos[i]);
				}
			}
			animating = false;
		} else {
			// イージングしながら移動
			for(int i = 0; i < 4; i++) {
				if(i < 2) {
					playerCards.get(i).setPosition(
						new Vector2(
							easingIn(DECK_CARD_POSITION.getX(), targetPos[i].getX(), timeCounter, animTime),
							easingIn(DECK_CARD_POSITION.getY(), targetPos[i].getY(), timeCounter, animTime)
						)
					);
				} else {
					dealerCards.get(i - 2).setPosition(
						new Vector2(
							easingIn(DECK_CARD_POSITION.getX(), targetPos[i].getX(), timeCounter, animTime),
							easingIn(DECK_CARD_POSITION.getY(), targetPos[i].getY(), timeCounter, animTime)
						)
					);
				}
			}
		}
	}
	
	/**
	 * 手札を捨て札に捨てるアニメーション
	 */
	private void discard() {
		if(timeCounter == 0) {
			// 最初のポジションの保管
			playerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
			dealerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
		} else if(animTime <= timeCounter) {
			// リストから削除
			playerCards.forEach((pc) -> {
				GraphicManager.getInstance().getGraphicDataList().remove(pc);
			});
			dealerCards.forEach((pc) -> {
				GraphicManager.getInstance().getGraphicDataList().remove(pc);
			});
			GraphicManager.getInstance().sortLayer();
			
			playerCards.clear();
			dealerCards.clear();
			
			// 捨て札の位置と回転を調整して表示
			talon.setPosition(TALON_CARD_POSITION);
			talon.setRotation(TALON_CARD_ROTATION);
			talon.setShow(true);
			
			animating = false;
		} else {
			// イージングしながら移動
			for(int i = 0; i < playerCards.size(); i++) {
				playerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i).getX(), TALON_CARD_POSITION.getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i).getY(), TALON_CARD_POSITION.getY(), timeCounter, animTime)
					)
				);
			}
			for(int i = 0; i < dealerCards.size(); i++) {
				dealerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i + playerCards.size()).getX(), TALON_CARD_POSITION.getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i + playerCards.size()).getY(), TALON_CARD_POSITION.getY(), timeCounter, animTime)
					)
				);
			}
			
			// イージングしながら回転
			for(int i = 0; i < playerCards.size(); i++) {
				playerCards.get(i).setRotation(
					easingIn(0d, TALON_CARD_ROTATION, timeCounter, animTime)
				);
			}
			for(int i = 0; i < dealerCards.size(); i++) {
				dealerCards.get(i).setRotation(
					easingIn(0d, TALON_CARD_ROTATION, timeCounter, animTime)
				);
			}
		}
	}
	
	/**
	 * 手札と捨て札をデッキに戻すアニメーション
	 */
	private void backToDeck() {
		if(timeCounter == 0) {
			// 最初のポジションの保管
			playerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
			dealerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
		} else if(animTime <= timeCounter) {
			// リストから削除
			playerCards.forEach((pc) -> {
				GraphicManager.getInstance().getGraphicDataList().remove(pc);
			});
			dealerCards.forEach((pc) -> {
				GraphicManager.getInstance().getGraphicDataList().remove(pc);
			});
			GraphicManager.getInstance().sortLayer();
			
			playerCards.clear();
			dealerCards.clear();
			
			// 捨て札を非表示
			talon.setShow(false);
			
			animating = false;
		} else {
			// イージングしながら移動
			for(int i = 0; i < playerCards.size(); i++) {
				playerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i).getX(), DECK_CARD_POSITION.getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i).getY(), DECK_CARD_POSITION.getY(), timeCounter, animTime)
					)
				);
			}
			for(int i = 0; i < dealerCards.size(); i++) {
				dealerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i + playerCards.size()).getX(), DECK_CARD_POSITION.getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i + playerCards.size()).getY(), DECK_CARD_POSITION.getY(), timeCounter, animTime)
					)
				);
			}
			talon.setPosition(
				new Vector2(
					easingIn(TALON_CARD_POSITION.getX(), DECK_CARD_POSITION.getX(), timeCounter, animTime),
					easingIn(TALON_CARD_POSITION.getY(), DECK_CARD_POSITION.getY(), timeCounter, animTime)
				)
			);
			
			// 捨て札をイージングしならが回転
			talon.setRotation(
				easingIn(TALON_CARD_ROTATION, 0d, timeCounter, animTime)
			);
		}
	}
	
	/**
	 * プレイヤーのヒットのアニメーション
	 */
	private void playerHit() {
		// 最終的なポジションを取得
		Vector2[] targetPos = new Vector2[playerCards.size()];
		if(!playerCards.isEmpty()) {
			for(int i = 0; i < targetPos.length; i++) {
				double distanceRate = (double) i / (double) (targetPos.length - 1);
				targetPos[i] = PLAYER_CARD_LEFT_POSITION.add(PLAYER_CARD_RIGHT_DISTANCE.multiply(distanceRate));
			}
		}
		
		if(timeCounter == 0) {
			// 手札の画像を追加
			BufferedImage hitCardImg = splitCardImage(hitCard);
			GraphicData newHit = new GraphicData(
				hitCardImg,
				CARD_LAYER_BASE + PLAYER_CARD_RELATIVE_LAYER_BASE + playerCards.size(),
				DECK_CARD_POSITION,
				CARD_SCALE,
				0,
				Vector2.ZERO,
				true
			);
			playerCards.add(newHit);
			GraphicManager.getInstance().getGraphicDataList().add(newHit);
			GraphicManager.getInstance().sortLayer();
			
			// 最初のポジションの保管
			playerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
		} else if(animTime <= timeCounter) {
			// 最終的なポジションに設置
			for(int i = 0; i < playerCards.size(); i++) {
				playerCards.get(i).setPosition(targetPos[i]);
			}
			animating = false;
		} else {
			// イージングしながら移動
			for(int i = 0; i < playerCards.size(); i++) {
				playerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i).getX(), targetPos[i].getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i).getY(), targetPos[i].getY(), timeCounter, animTime)
					)
				);
			}
		}
	}
	
	/**
	 * ディーラーのカードを公開するアニメーション
	 */
	private void showDealersCard() {
		if(animTime / 2d > timeCounter) {
			dealerCards.get(1).setShear(
				new Vector2(
					FLIP_CARD_MAX_SHEAR.getX() * timeCounter / (animTime / 2),
					FLIP_CARD_MAX_SHEAR.getY() * timeCounter / (animTime / 2)
				)
			);
			dealerCards.get(1).setScale(
				new Vector2(
					CARD_SCALE.getX() - timeCounter / (animTime / 2) * CARD_SCALE.getX(),
					CARD_SCALE.getX()
				)
			);
		} else if(animTime <= timeCounter) {
			// 元の形に戻す
			dealerCards.get(1).setShear(Vector2.ZERO);
			dealerCards.get(1).setScale(CARD_SCALE);
			
			animating = false;
		} else{
			// カードの画像を変更
			if(!tmpFlag) {
				BufferedImage card = splitCardImage(firstCards[3]);
				dealerCards.get(1).setImage(card);
				tmpFlag = true;
			}
			
			dealerCards.get(1).setShear(
				new Vector2(
					-(FLIP_CARD_MAX_SHEAR.getX() - FLIP_CARD_MAX_SHEAR.getX() * (timeCounter - animTime / 2d) / (animTime / 2)),
					-(FLIP_CARD_MAX_SHEAR.getY() - FLIP_CARD_MAX_SHEAR.getY() * (timeCounter - animTime / 2d) / (animTime / 2))
				)
			);
			dealerCards.get(1).setScale(
				new Vector2(
					(timeCounter - animTime / 2d) / (animTime / 2) * CARD_SCALE.getX(),
					CARD_SCALE.getY()
				)
			);
		}
	}
	
	/**
	 * ディーラーのヒットのアニメーション
	 */
	private void dealerHit() {
		// 最終的なポジションを取得
		Vector2[] targetPos = new Vector2[dealerCards.size()];
		if(!dealerCards.isEmpty()) {
			for(int i = 0; i < targetPos.length; i++) {
				double distanceRate = (double) i / (double) (targetPos.length - 1);
				targetPos[i] = DEALER_CARD_LEFT_POSITION.add(DEALER_CARD_RIGHT_DISTANCE.multiply(distanceRate));
			}
		}
		
		if(timeCounter == 0) {
			// 手札の画像を追加
			BufferedImage hitCardImg = splitCardImage(hitCard);
			GraphicData newHit = new GraphicData(
				hitCardImg,
				CARD_LAYER_BASE + DEALER_CARD_RELATIVE_LAYER_BASE + dealerCards.size(),
				DECK_CARD_POSITION,
				CARD_SCALE,
				0,
				Vector2.ZERO,
				true
			);
			dealerCards.add(newHit);
			GraphicManager.getInstance().getGraphicDataList().add(newHit);
			GraphicManager.getInstance().sortLayer();
			
			// 最初のポジションの保管
			dealerCards.forEach((pc) -> {
				tmpVector.add(pc.getPosition());
			});
		} else if(animTime <= timeCounter) {
			// 最終的なポジションに設置
			for(int i = 0; i < dealerCards.size(); i++) {
				dealerCards.get(i).setPosition(targetPos[i]);
			}
			animating = false;
		} else {
			// イージングしながら移動
			for(int i = 0; i < dealerCards.size(); i++) {
				dealerCards.get(i).setPosition(
					new Vector2(
						easingIn(tmpVector.get(i).getX(), targetPos[i].getX(), timeCounter, animTime),
						easingIn(tmpVector.get(i).getY(), targetPos[i].getY(), timeCounter, animTime)
					)
				);
			}
		}
	}
	
	/**
	 * 攻撃のアニメーション
	 */
	private void attack() {
		if(timeCounter == 0) {
			switch(bjResult) {
			case VICTORY:
				damageText.setPosition(DEALER_DAMAGE_POSITION);
				break;
			case DEFEAT:
				damageText.setPosition(PLAYER_DAMAGE_POSITION);
				break;
			default:
				animating = false;
				return;
			}
			damageText.setText("-" + Integer.toString(atk));
			damageText.setColor(Color.RED);
		} else if(animTime <= timeCounter) {
			damageText.setText("");
			animating = false;
		} else if(animTime / 2d <= timeCounter) {
			
			damageText.setColor(
				new Color(
					damageText.getColor().getRed(),
					damageText.getColor().getGreen(),
					damageText.getColor().getBlue(),
					(int) (255d * (1d - (timeCounter - animTime / 2d) / (animTime / 2)))
				)
			);
		}
	}
	
	/**
	 * 戦闘結果の表示アニメーション
	 */
	private void showResult() {
		if(timeCounter == 0) {
			if(!playerWin) {
				result.setImage(ImageLoader.getInstance().getImagesMap().get(LOSE_IMAGE_KEY_NAME));
			}
			
			announceText.setText("");
			playerScoreText.setText("");
			dealerScoreText.setText("");
			
			result.setScale(Vector2.ZERO);
			result.setShow(true);
		} else if(animTime <= timeCounter) {
			result.setScale(Vector2.ONE);
			animating = false;
		} else {
			result.setScale(
				new Vector2(
					easingIn(0, 1, timeCounter, animTime),
					easingIn(0, 1, timeCounter, animTime)
				)
			);
		}
	}
	
	/**
	 * カードのデータからカード1枚の画像を抜き取る
	 * @param card カードのデータ
	 * @return カードの画像
	 */
	private BufferedImage splitCardImage(Card card) {
		int suitNum;
		switch(card.suit()) {
		case SPADE:
			suitNum = 0;
			break;
		case CLOVER:
			suitNum = 1;
			break;
		case DIAMOND:
			suitNum = 2;
			break;
		case HEART:
			suitNum = 3;
			break;
		default:
			System.out.println("スートが不正な値です");
			return null;
		}
		return ImageLoader.getInstance().imageSplit(cardCells, CARD_SPLIT_HORIZONTAL, CARD_SPLIT_VERTICAL, card.rank() - 1, suitNum);
	}
	
	/**
	 * イージングイン
	 * @param start 開始の値
	 * @param target 目的の値
	 * @param curtTime 現在の時間
	 * @param endTime 終了時間
	 * @return イージングされた値
	 */
	private double easingIn(double start, double target, double curtTime, double endTime) {
		double t = curtTime / endTime;
		double easingFactor = t * t * (3 - 2 * t);
		return start + (target - start) * easingFactor;
	}
}
