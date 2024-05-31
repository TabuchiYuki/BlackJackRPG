package programs.blackJack;

import java.util.ArrayList;
import java.util.List;

import programs.data.master.Card;

/**
 * ブラックジャックのプレイヤーの手札を管理するクラスです。
 * 手札にカードを追加し、合計値を計算する機能を提供します。
 * @author 菅原 凜
 * @deprecated {@link programs.data.CharacterData CharacterData}に統合
 */
public class Hand {
    private List<Card> cards; // 手札のカードリスト

    /**
     * 手札を初期化するコンストラクタです。
     */
    public Hand() {
        cards = new ArrayList<>(); // 手札の初期化
    }

    /**
     * 手札にカードを追加します。
     * @param card 追加するカードオブジェクト
     */
    public void addCard(Card card) {
        cards.add(card); // 新しいカードを手札に追加
    }

    /**
     * 手札のカードの合計値を計算します。エースは1または11として扱われます。
     * 手札の合計が21を超える場合、エースは1として計算されます。
     * @return 手札のカードの合計値
     */
    public int calculateHandValue() {
        int totalValue = 0;
        int aceCount = 0;  // エースのカードの数を数える

        for (Card card : cards) {
            int rank = card.rank();
            if (rank == 1) {
                totalValue += 11; // エースはとりあえず11として加算
                aceCount++;        // エースの数をカウント
            } else if (rank > 10) {
                totalValue += 10; // 絵札(J, Q, K)は10として計算
            } else {
                totalValue += rank; // それ以外のカードはその数字を直接加算
            }
        }

        // 合計が21を超えている場合、エースを1として計算し直す
        while (totalValue > 21 && aceCount > 0) {
            totalValue -= 10; // エースを11から1へ調整
            aceCount--;       // 調整後のエースの数を減らす
        }

        return totalValue;
    }
}
