package programs.blackJack;

import programs.data.BlackJackResult;
import programs.data.CharacterData;

/**
 * ブラックジャックの勝敗を判定するクラスです。
 * プレイヤーとディーラーの手札を比較して勝敗を決定します。
 * @author 菅原 凜
 */
public class GameRules {
	private Deck deck;
	private PlayerActions player;
	private PlayerActions dealer;
	
	/**
	 * 各種データの初期化を行うコンストラクタ
	 * @param playerData プレイヤーのデータ
	 * @param dealerData ディーラーのデータ
	 */
	public GameRules(CharacterData playerData, CharacterData dealerData) {
		deck = new Deck();
		player = new Player(deck, playerData);
		dealer = new Dealer(deck, dealerData);
	}
	
	/**
	 * デッキを初期化し、シャッフルする
	 */
	public void setupDeck() {
		player.getData().getCards().clear();
		dealer.getData().getCards().clear();
		deck.initializeDeck();
		deck.shuffle();
	}
	
	/**
	 * プレイヤーのヒット
	 */
	public void playerHit() {
		player.hit();
	}
	
	/**
	 * ディーラーのヒット
	 */
	public void dealerHit() {
		dealer.hit();
	}
	
    /**
     * プレイヤーとディーラーの手札を比較して勝敗を判定します。
     * @param playerHand プレイヤーの手札
     * @param dealerHand ディーラーの手札
     * @return 勝敗結果の列挙型 (BlackJackResult)
     */
    public BlackJackResult determineWinner(int playerScore, int dealerScore) {
        if (playerScore > 21) {
            return BlackJackResult.DEFEAT;  // プレイヤーがバスト
        } else if (dealerScore > 21) {
            return BlackJackResult.VICTORY;  // ディーラーがバスト
        } else if (playerScore > dealerScore) {
            return BlackJackResult.VICTORY;  // プレイヤーのスコアが高い
        } else if (playerScore < dealerScore) {
            return BlackJackResult.DEFEAT; // ディーラーのスコアが高い
        } else {
            return BlackJackResult.DRAW;  // スコアが同じ
        }
    }
}
