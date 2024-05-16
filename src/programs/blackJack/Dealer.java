// Dealer.java
package programs.blackJack;

public class Dealer {
    private Hand hand;

    public Dealer() {
        hand = new Hand();
    }

    public Hand getHand() {
        return hand;
    }

    public void hit() {
        // ディーラーがカードを引く処理
    }

    public void stand() {
        // ディーラーがスタンドする処理
    }

    public void dealInitialCards(Player player) {
        // 初期のカードを配る処理
    }
}