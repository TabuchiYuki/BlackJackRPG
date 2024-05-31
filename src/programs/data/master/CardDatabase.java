package programs.data.master;

import java.util.Objects;

/**
 * すべてのカードのデータベース
 * @see スーパークラス:{@link programs.system.Singleton Singleton}
 * @author 進藤颯斗
 */
public class CardDatabase {
	private static CardDatabase instance;
	private Card[] cards;
	
	/**
	 * プライベートコンストラクタ
	 */
	private CardDatabase() {
		init();
	}
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static CardDatabase getInstance() {
		if(Objects.isNull(instance)) {
			instance = new CardDatabase();
		}
		return instance;
	}
	
	/**
	 * カードのゲッター
	 * @see {@link #cards}
	 * @return カード
	 */
	public Card[] getCards() { return cards; }
	
	/**
	 * カードのデータを初期化
	 */
	public void init() {
		cards = new Card[52];
		
		for(int i = 0; i < 4; i++) {
			CardSuit tmpSuit;
			
			switch(i) {
			case 0:
				tmpSuit = CardSuit.SPADE;
				break;
			case 1:
				tmpSuit = CardSuit.DIAMOND;
				break;
			case 2:
				tmpSuit = CardSuit.CLOVER;
				break;
			case 3:
				tmpSuit = CardSuit.HEART;
				break;
			default:
				tmpSuit = CardSuit.NONE;
				break;
			}
			
			for(int j = 0; j < 13; j++) {
				cards[i * 13 + j] = new Card(tmpSuit, j + 1);
			}
		}
	}
}
