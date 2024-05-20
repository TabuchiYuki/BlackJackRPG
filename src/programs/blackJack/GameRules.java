package programs.blackJack;

public class GameRules {

    /**
     * プレイヤーとディーラーの手札を比較して勝敗を判定します。
     * @param playerHand プレイヤーの手札
     * @param dealerHand ディーラーの手札
     * @return 勝敗結果の文字列 ("Win", "Lose", "Draw")
     */
    public static String determineWinner(Hand playerHand, Hand dealerHand) {
        int playerScore = playerHand.calculateHandValue();
        int dealerScore = dealerHand.calculateHandValue();

        if (playerScore > 21) {
            return "Lose";  // プレイヤーがバスト
        } else if (dealerScore > 21) {
            return "Win";  // ディーラーがバスト
        } else if (playerScore > dealerScore) {
            return "Win";  // プレイヤーのスコアが高い
        } else if (playerScore < dealerScore) {
            return "Lose"; // ディーラーのスコアが高い
        } else {
            return "Draw";  // スコアが同じ
        }
    }
}
