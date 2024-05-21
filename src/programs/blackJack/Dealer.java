package programs.blackJack;

/**
 * ブラックジャックのディーラーを表すクラスです。
 * ディーラーは手札を持ち、ゲームのルールに基づきカードを引くかスタンドします。
 * @author 菅原 凜
 */
public class Dealer implements PlayerActions {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    @Override
    public void hit(Deck deck) {
        // ディーラーのカード引きルールを実装
        // 例: ディーラーの手札が16以下の場合にカードを引く
        if (getHand().calculateHandValue() <= 16) {
            BlackJackCard drawnCard = deck.drawCard();
            if (drawnCard != null) {
                hand.addCard(drawnCard);
            }
        }
    }

    @Override
    public void stand() {
        // ディーラーがスタンドする処理
    }

    @Override
    public Hand getHand() {
        return hand;
    }
}
