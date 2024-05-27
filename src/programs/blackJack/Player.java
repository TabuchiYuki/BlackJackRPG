package programs.blackJack;

import programs.data.CharacterData;
import programs.data.master.Card;

/**
 * ブラックジャックのプレイヤーを表すクラスです。
 * プレイヤーは手札を持ち、ゲーム中にカードを引くかスタンドするかを選択します。
 * @author 菅原 凜
 */
public class Player implements PlayerActions {
    private CharacterData data;
    private Deck deck;

    /**
     * Player オブジェクトを初期化します。
     * @param deck このプレイヤーが使用するデッキ。
     */
    public Player(Deck deck, CharacterData data) {
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
     * プレイヤーがカードを引きます。引いたカードがあれば手札に追加します。
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
     * プレイヤーがスタンドします。これ以上カードを引かないことを示します。
     */
    @Override
    public void stand() {
        // プレイヤーがスタンドする処理
    }
}
