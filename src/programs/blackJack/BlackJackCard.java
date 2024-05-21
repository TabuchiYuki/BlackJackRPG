package programs.blackJack;

import programs.data.master.CardSuit;

/**
 * 
 * @author 菅原 凜
 */
public class BlackJackCard {
    private CardSuit suit; // カードのスート
    private int rank;      // カードのランク

    /**
     * Card オブジェクトを初期化します。
     * @param suit カードのスートを指定します。
     * @param rank カードのランクを指定します。
     */
    public BlackJackCard(CardSuit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * カードのスートを取得します。
     * @return このカードのスート。
     */
    public CardSuit getSuit() {
        return suit;
    }

    /**
     * カードのランクを取得します。
     * @return このカードのランク。
     */
    public int getRank() {
        return rank;
    }
}
