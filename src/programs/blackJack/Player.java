package programs.blackJack;

/**
 * ブラックジャックのプレイヤーを表すクラスです。
 * プレイヤーは手札を持ち、ゲーム中にカードを引くかスタンドするかを選択します。
 * @author 菅原 凜
 */
public class Player implements PlayerActions {
    private Hand hand;

    public Player() {
        hand = new Hand();
    }

    @Override
    public void hit(Deck deck) {
        Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
            hand.addCard(drawnCard);
        }
    }

    @Override
    public void stand() {
        // プレイヤーがスタンドする処理
    }

    @Override
    public Hand getHand() {
        return hand;
    }
}
