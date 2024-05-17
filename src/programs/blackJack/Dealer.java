//Dealer.java
package programs.blackJack;

/**
 * ブラックジャックのディーラーを表すクラスです。
 * ディーラーは手札を持ち、カードを引いたり、スタンドする処理を管理します。
 * @author 菅原 凜 
 */
public class Dealer {
    private Hand hand; // ディーラーの手札

    /**
     * ディーラーのインスタンスを初期化します。
     */
    public Dealer() {
        hand = new Hand();
    }

    /**
     * ディーラーの手札を取得します。
     * @return ディーラーの持っている手札
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * ディーラーがカードを引く処理を行います。
     */
    public void hit() {
        // ディーラーがカードを引く処理
        // ここに実際のカードをデッキから手札に追加するロジックを記述
    }

    /**
     * ディーラーがスタンドする処理を行います。
     * スタンドすると、ディーラーはこれ以上カードを引かずに現在の手札で勝負します。
     */
    public void stand() {
        // ディーラーがスタンドする処理
        // ここにスタンドした場合のロジックを記述
    }

    /**
     * プレイヤーに初期のカードを2枚配る処理を行います。
     * @param player カードを配る対象のプレイヤー
     */
    public void dealInitialCards(Player player) {
        // 初期のカードを配る処理
        // ここにプレイヤーに2枚のカードを配るロジックを記述
    }
}
