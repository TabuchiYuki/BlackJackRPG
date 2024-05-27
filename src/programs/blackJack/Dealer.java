package programs.blackJack;

import programs.data.CharacterData;
import programs.data.master.Card;

/**
 * ブラックジャックのディーラーを表すクラスです。
 * ディーラーは手札を持ち、ゲームのルールに基づきカードを引くかスタンドします。
 * @author 菅原 凜
 */
public class Dealer implements PlayerActions {
    private CharacterData data;
    private Deck deck;

    /**
     * Dealer オブジェクトを初期化します。
     * @param deck このディーラーが使用するデッキ。
     */
    public Dealer(Deck deck, CharacterData data) {
        this.deck = deck;
        this.data = data;
    }
    
    /**
     * キャラクターデータのゲッター
     * @see {@link #data}
     * @retrun キャラクターデータ
     */
    @Override
    public CharacterData getData() { return data; }

    /**
     * ディーラーがカードを引きます。引いたカードがあれば手札に追加します。
     * ディーラーの手札の合計値が16以下の場合にのみカードを引きます。
     */
    @Override
    public void hit() {
    	Card drawnCard = deck.drawCard();
        if (drawnCard != null) {
        	data.getCards().add(drawnCard);
        	data.updateScore();
        }
    }

    /**
     * ディーラーがスタンドします。これ以上カードを引かないことを示します。
     */
    @Override
    public void stand() {
        // ディーラーがスタンドする処理
    }
}
