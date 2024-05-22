package programs.blackJack;

import programs.data.master.Card;

/**
 * ブラックジャックのプレイヤーを表すクラスです。
 * プレイヤーは手札を持ち、ゲーム中にカードを引くかスタンドするかを選択します。
 * @author 菅原 凜
 */
public class Player implements PlayerActions {
    private Hand hand;
    private Deck deck;

    /**
     * Player オブジェクトを初期化します。
     * @param deck このプレイヤーが使用するデッキ。
     */
    public Player(Deck deck) {
        this.deck = deck;
        hand = new Hand();
    }

    /**
     * プレイヤーがカードを引きます。引いたカードがあれば手札に追加します。
     */
    @Override
    public void hit() {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            hand.addCard(drawnCard);
        }
    }

    /**
     * プレイヤーがスタンドします。これ以上カードを引かないことを示します。
     */
    @Override
    public void stand() {
        // プレイヤーがスタンドする処理
    }

    /**
     * プレイヤーの手札を取得します。
     * @return プレイヤーの手札。
     */
    @Override
    public Hand getHand() {
        return hand;
    }
}
