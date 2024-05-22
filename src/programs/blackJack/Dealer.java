package programs.blackJack;

import programs.data.master.Card;

/**
 * ブラックジャックのディーラーを表すクラスです。
 * ディーラーは手札を持ち、ゲームのルールに基づきカードを引くかスタンドします。
 * @author 菅原 凜
 */
public class Dealer implements PlayerActions {
    private Hand hand;
    private Deck deck;

    /**
     * Dealer オブジェクトを初期化します。
     * @param deck このディーラーが使用するデッキ。
     */
    public Dealer(Deck deck) {
        this.deck = deck;
        hand = new Hand();
    }

    /**
     * ディーラーがカードを引きます。引いたカードがあれば手札に追加します。
     * ディーラーの手札の合計値が16以下の場合にのみカードを引きます。
     */
    @Override
    public void hit() {
        while (hand.calculateHandValue() <= 16) {
            Card drawnCard = deck.drawCard();
            if (drawnCard != null) {
                hand.addCard(drawnCard);
            }
        }
    }

    /**
     * ディーラーがスタンドします。これ以上カードを引かないことを示します。
     */
    @Override
    public void stand() {
        // ディーラーがスタンドする処理
    }

    /**
     * ディーラーの手札を取得します。
     * @return ディーラーの手札。
     */
    @Override
    public Hand getHand() {
        return hand;
    }
}
