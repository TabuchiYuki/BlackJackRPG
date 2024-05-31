package programs.blackJack;

import programs.data.CharacterData;

/**
 * ブラックジャックゲームにおけるプレイヤーやディーラーの共通行動を定義するインターフェースです。
 * このインターフェースを通じて、カードを引く、スタンドする、手札を取得するといった基本的なアクションを規定します。
 * @author 菅原 凜
 */
public interface PlayerActions {
	/**
	 * キャラクターデータのゲッター
	 * @return キャラクターデータ
	 */
	CharacterData getData();
	
    /**
     * ゲームのデッキからカードを一枚引き、手札に追加する行動を定義します。
     */
    void hit();

    /**
     * これ以上カードを引かずに現在の手札で勝負を決めるスタンドの行動を定義します。
     */
    void stand();
}
