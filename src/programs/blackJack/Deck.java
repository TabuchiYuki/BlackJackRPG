//Deck.java
package programs.blackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> cards;

    public Deck() {
        initializeDeck();
        shuffle();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        // トランプのカードを生成して追加
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            return null;  // デッキが空の場合はnullを返す
        }
        return cards.remove(0);  // デッキの先頭のカードを取り出し、それを返す
    }
}
