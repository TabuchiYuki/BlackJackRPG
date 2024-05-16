package programs.data.master;

import programs.system.Singleton;

/**
 * すべてのカードのデータベース
 * @see スーパークラス:{@link programs.system.Singleton Singleton}
 * @author 進藤颯斗
 */
public class CardDatabase extends Singleton<CardDatabase> {
	private Card[] cards;
	
	/**
	 * カードのゲッター
	 * @see {@link #cards}
	 * @return カード
	 */
	public Card[] getCards() { return cards; }
	
	/**
	 * カードのデータを初期化
	 */
	private CardDatabase() {
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
