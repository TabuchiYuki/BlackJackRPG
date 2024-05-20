package programs.data;

/**
 * 二次元ベクトル
 * @author 田淵勇輝
 */
public class Vector2 {
	private double x;
	private double y;
	
	/**
	 * X軸とY軸を初期化するコンストラクタ
	 * @param x X軸の値
	 * @param y Y軸の値
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * X軸のゲッター
	 * @see {@link #x}
	 * @return X軸の値
	 */
	public double getX() { return x; }
	/**
	 * Y軸のゲッター
	 * @see {@link #y}
	 * @return Y軸の値
	 */
	public double getY() { return y; }
	
	/**
	 * X軸のセッター
	 * @see {@link #x}
	 * @param x X軸の値
	 */
	public void setX(double x) { this.x = x; }
	/**
	 * Y軸のセッター
	 * @see {@link #y}
	 * @param y Y軸の値
	 */
	public void setY(double y) { this.y = y; }
	
	// 以下静的メソッド
	
	/**
	 * 正規化
	 * @param vec 正規化する二次元ベクトル
	 * @return 正規化した二次元ベクトル
	 */
	public static Vector2 normalize(Vector2 vec) {
		float length = (float) Math.sqrt(vec.getX() * vec.getX() + vec.getY() * vec.getY());
		if(length != 0) {
			return new Vector2(vec.getX() / length, vec.getY() / length);
		} else {
			return vec;
		}
	}
}
