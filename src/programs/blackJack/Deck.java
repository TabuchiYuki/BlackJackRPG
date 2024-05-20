//Deck.java
package programs.blackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ブラックジャックのカードデッキを表すクラスです。
 * このデッキはカードのリストを管理し、カードをシャッフルおよびドローする機能を提供します。
* @author 菅原 凜
 */
public class Deck {
    private List<Card> cards; // デッキ内のカード

    /**
     * デッキを初期化し、カードを生成してシャッフルします。
     */
    public Deck() {
        initializeDeck();
        shuffle();
    }

    /**
     * デッキを初期化し、全カードを生成して追加します。
     */
    private void initializeDeck() {
        cards = new ArrayList<>();
        // トランプのカードを生成して追加
        // ここに具体的なカード生成のロジックを追加
    }

    /**
     * デッキのカードをシャッフルします。
     */
    private void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * デッキからカードを一枚引きます。
     * @return 引いたカード。デッキが空の場合はnullを返します。
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;  // デッキが空の場合はnullを返す
        }
        return cards.remove(0);  // デッキの先頭のカードを取り出し、それを返す
    }
}
