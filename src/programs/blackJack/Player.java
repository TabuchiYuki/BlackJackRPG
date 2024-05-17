//Player.java
package programs.blackJack;

/**
 * ブラックジャックのプレイヤーを表すクラスです。
 * プレイヤーは手札を持ち、ゲーム中にカードを引くかスタンドするかを選択します。
 * @author 菅原 凜
 */
public class Player {
    private Hand hand; // プレイヤーの手札

    /**
     * プレイヤーのインスタンスを初期化します。
     */
    public Player() {
        hand = new Hand();
    }

    /**
     * プレイヤーの手札を取得します。
     * @return プレイヤーの持っている手札
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * プレイヤーがカードを引く処理を行います。
     * このメソッドはデッキからカードを一枚引き、プレイヤーの手札に追加します。
     * @param deck ゲームのデッキ
     */
    public void hit(Deck deck) {
        Card drawnCard = deck.drawCard(); // デッキからカードを一枚引く
        if (drawnCard != null) {
            hand.addCard(drawnCard); // 手札にカードを追加
        }
    }

    /**
     * プレイヤーがスタンドする処理を行います。
     * スタンドすると、プレイヤーはこれ以上カードを引かずに現在の手札で勝負します。
     */
    public void stand() {
        // スタンド処理をここに記述
    }
}
