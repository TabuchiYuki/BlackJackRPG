package programs.blackJack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards; // 手札のカードリスト

    public Hand() {
        cards = new ArrayList<>(); // 手札の初期化
    }

    public void addCard(Card card) {
        cards.add(card); // 新しいカードを手札に追加
    }

    // 手札の合計値を計算するメソッド
    public int calculateHandValue() {
        int totalValue = 0;
        int aceCount = 0;  // エースのカードの数を数える

        for (Card card : cards) {
            int rank = card.getRank();
            if (rank == 1) {
                totalValue += 11;
                aceCount++;
            } else if (rank > 10) {
                totalValue += 10;
            } else {
                totalValue += rank;
            }
        }

        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10;
            aceCount--;
        }

        return totalValue;
    }
}
