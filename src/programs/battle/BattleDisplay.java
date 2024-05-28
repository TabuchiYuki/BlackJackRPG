package programs.battle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import programs.data.GraphicData;
import programs.data.TextGraphicData;
import programs.data.Vector2;
import programs.manager.GraphicManager;
import programs.system.FontLoader;
import programs.system.GameObject;
import programs.system.ImageLoader;

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
	private final Vector2 PLAYER_SCORE_POSITION = new Vector2(400, 520);
	private final Vector2 DEALER_SCORE_POSITION = new Vector2(400, 120);
	private final Vector2 PLAYER_CARD_LEFT_POSITION = new Vector2(320, 500);
	private final Vector2 PLAYER_CARD_RIGHT_POSITION = new Vector2(480, 500);
	private final Vector2 DEALER_CARD_LEFT_POSITION = new Vector2(320, 100);
	private final Vector2 DEALER_CARD_RIGHT_POSITION = new Vector2(480, 100);
	private final Vector2 PLAYER_BUST_POSITION = new Vector2(400, 500);
	private final Vector2 DEALER_BUST_POSITION = new Vector2(400, 100);
	private final Vector2 CARD_SCALE = new Vector2(0.3d, 0.3d);
	private final Vector2 HIT_BUTTON_POSITION = new Vector2(680, 430);
	private final Vector2 STAND_BUTTON_POSITION = new Vector2(680, 540);
	
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
	
	private boolean animating = false;
	
	/**
	 * アニメーション中かの判定のゲッター
	 * @see {@link #animating}
	 * @return アニメーション中かの判定
	 */
	public boolean isAnimating() { return animating; }
	
	@Override
	public void init() {
		setupGraphic();
	}
	
	@Override
	public void update() {
		
	}
	
	/**
	 * グラフィックの準備
	 */
	public void setupGraphic() {
		cardCells = ImageLoader.getInstance().getImagesMap().get(CARD_CELL_IMAGE_KEY_NAME);
		damageText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(0),
			STATUS_TEXT_FONT_SIZE,
			Vector2.ZERO,
			new Color(0, 0, 0, 0)
		);
		playerScoreText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(0),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_SCORE_POSITION,
			Color.BLACK
		);
		dealerScoreText = new TextGraphicData(
			"",
			FontLoader.getInstance().getFonts().get(0),
			STATUS_TEXT_FONT_SIZE,
			DEALER_SCORE_POSITION,
			Color.BLACK
		);
		GraphicManager.getInstance().getTextDataList().add(damageText);
		
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
		
		GraphicManager.getInstance().getGraphicDataList().add(hitButton);
		GraphicManager.getInstance().getGraphicDataList().add(standButton);
		GraphicManager.getInstance().getGraphicDataList().add(playerBust);
		GraphicManager.getInstance().getGraphicDataList().add(dealerBust);
		GraphicManager.getInstance().getGraphicDataList().add(result);
		
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
			Color.WHITE
		);
		playerHpText = new TextGraphicData(
			String.format("hp:%d", playerHp),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE),
			Color.WHITE
		);
		TextGraphicData playerAtkText = new TextGraphicData(
			String.format("atk:%d", playerAtk),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			PLAYER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE.multiply(2)),
			Color.WHITE
		);
		TextGraphicData dealerNameText = new TextGraphicData(
			"dealer",
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION,
			Color.WHITE
		);
		dealerHpText = new TextGraphicData(
			String.format("hp:%d", dealerHp),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE),
			Color.WHITE
		);
		TextGraphicData dealerAtkText = new TextGraphicData(
			String.format("atk:%d", dealerAtk),
			FontLoader.getInstance().getFonts().get(FONT_INDEX),
			STATUS_TEXT_FONT_SIZE,
			DEALER_TEXT_BASE_POSITION.add(STATUS_TEXT_LINE_SPACE.multiply(2)),
			Color.WHITE
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
}
