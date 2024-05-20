package programs.blackJack;

import programs.data.BlackJackResult;

/**
 * ブラックジャックの勝敗を判定するクラスです。
 * プレイヤーとディーラーの手札を比較して勝敗を決定します。
 * @author 菅原 凜
 */
public class GameRules {

    /**
     * プレイヤーとディーラーの手札を比較して勝敗を判定します。
     * @param playerHand プレイヤーの手札
     * @param dealerHand ディーラーの手札
     * @return 勝敗結果の列挙型 (BlackJackResult)
     */
    public static BlackJackResult determineWinner(Hand playerHand, Hand dealerHand) {
        int playerScore = playerHand.calculateHandValue();
        int dealerScore = dealerHand.calculateHandValue();

        if (playerScore > 21) {
            return BlackJackResult.DEFEAT;  // プレイヤーがバスト
        } else if (dealerScore > 21) {
            return BlackJackResult.VICTORY;  // ディーラーがバスト
        } else if (playerScore > dealerScore) {
            return BlackJackResult.VICTORY;  // プレイヤーのスコアが高い
        } else if (playerScore < dealerScore) {
            return BlackJackResult.DEFEAT; // ディーラーのスコアが高い
        } else {
            return BlackJackResult.DRAW;  // スコアが同じ
        }
    }
}
