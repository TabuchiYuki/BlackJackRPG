package programs.data;

import java.util.ArrayList;
import java.util.List;

import programs.data.master.Card;

/**
 * キャラクターデータ
 * @author 進藤颯斗
 */
public class CharacterData {
	private List<Card> cards = new ArrayList<Card>();
	
	private int hp;
	private int atk;
	private int score;
	
	/**
	 * コンストラクタ
	 * @param hp HP
	 * @param atk 攻撃力
	 */
	public CharacterData(int hp, int atk) {
		this.hp = hp;
		this.atk = atk;
	}
	
	/**
	 * 手札のリストのゲッター
	 * @see {@link #cards}
	 * @return 手札のリスト
	 */
	public List<Card> getCards() { return cards; }
	/**
	 * HPのゲッター
	 * @see {@link #hp}
	 * @return HP
	 */
	public int getHp() { return hp; }
	/**
	 * 攻撃力のゲッター
	 * @see {@link #atk}
	 * @return 攻撃力
	 */
	public int getAtk() { return atk; }
	/**
	 * スコアのゲッター
	 * @see {@link #score}
	 * @return スコア
	 */
	public int getScore() { return score; }
	
	/**
	 * HPのセッター
	 * @see {@link #hp}
	 * @param hp HP
	 */
	public void setHp(int hp) { this.hp = hp; }
	
	/**
	 * スコアの更新
	 */
	public void updateScore() {
		int aceCount = 0;
		score = 0;
		
		for(int i = 0; i < cards.size(); i++) {
			int rank = cards.get(i).rank();
			if(rank == 1) {
				score += 11;
				aceCount++;
			} else if (rank > 10) {
				score += 10;
			} else {
				score += rank;
			}
		}
		
		while(score > 21 && aceCount >= 1) {
			score -= 10;
			aceCount--;
		}
	}
}
