package programs.blackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import programs.data.master.Card;
import programs.data.master.CardDatabase;

/**
 * ブラックジャックのカードデッキを表すクラスです。
 * このデッキはカードのリストを管理し、カードをシャッフルおよびドローする機能を提供します。
 * @author 菅原 凜
 */
public class Deck {
    private List<Card> cards; // デッキ内のカード

    public Deck() {
        initializeDeck();
        shuffle();
    }

    /**
     * デッキを初期化し、全カードを生成して追加します。
     */
    private void initializeDeck() {
        cards = new ArrayList<>();
        // CardDatabaseから全カードを取得してデッキに追加
        Collections.addAll(cards, CardDatabase.getInstance().getCards());
    }

    /**
     * デッキのカードをシャッフルします。
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * デッキからカードを一枚引きます。
     * @return 引いたカード。デッキが空の場合はnullを返します。
     */
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);  // デッキの先頭のカードを取り出し、それを返す
        }
        return null;
    }
}
