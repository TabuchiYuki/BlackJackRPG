package programs.data;

import java.awt.image.BufferedImage;

/**
 * グラフィックのデータ
 * @author 田淵勇輝
 */
public class GraphicData {
	private BufferedImage image;
	private Vector2 pivot;
	private Vector2 position;
	private Vector2 scale;
	private double rotation;
	private double radian;
	
	/**
	 * 全てのデータを初期化するコンストラクタ
	 * @param img 画像
	 * @param pivot ピボット座標
	 * @param position 座標
	 * @param scale 拡大率
	 * @param rotation 回転角(度数法)
	 */
	public GraphicData(BufferedImage image, Vector2 pivot, Vector2 position, Vector2 scale, double rotation) {
		this.image = image;
		this.pivot = pivot;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		radian = Math.toRadians(rotation);
	}
	
	/**
	 * コンストラクタ
	 * ピボットの位置は画像の中心になる
	 * @param image
	 * @param position
	 * @param scale
	 * @param rotation
	 */
	public GraphicData(BufferedImage image, Vector2 position, Vector2 scale, double rotation) {
		this.image = image;
		pivot = new Vector2((double)image.getWidth() / 2, (double)image.getHeight() / 2);
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		radian = Math.toRadians(rotation);
	}
	
	/**
	 * コンストラクタ
	 * 画像以外は自動的に初期化される
	 * @param image 画像
	 */
	public GraphicData(BufferedImage image) {
		this.image = image;
		pivot = new Vector2(image.getWidth() / 2, image.getHeight() / 2);
		position = new Vector2(0.0d, 0.0d);
		scale = new Vector2(1.0d, 1.0d);
		rotation = 0.0d;
		radian = Math.toRadians(rotation);
	}
	
	/**
	 * 画像のゲッター
	 * @see {@link #image}
	 * @return 画像
	 */
	public BufferedImage getImage() { return image; }
	/**
	 * ピボットのゲッター
	 * @see {@link #pivot}
	 * @return ピボット
	 */
	public Vector2 getPivot() { return pivot; }
	/**
	 * 座標のゲッター
	 * @see {@link #position}
	 * @return 座標
	 */
	public Vector2 getPosition() { return position; }
	/**
	 * 拡大率のゲッター
	 * @see {@link #scale}
	 * @return 拡大率
	 */
	public Vector2 getScale() { return scale; }
	/**
	 * 回転角(度数法)のゲッター
	 * @see {@link #rotation}
	 * @return 回転角(度数法)
	 */
	public double getRotation() { return rotation; }
	/**
	 * 回転角(弧度法)のゲッター
	 * @see {@link #radian}
	 * @return 回転角(弧度法)
	 */
	public double getRadian() { return radian; }
	
	/**
	 * ピボットのセッター
	 * @see {@link #pivot}
	 * @param pivot ピボット
	 */
	public void setPivot(Vector2 pivot) { this.pivot = pivot; }
	/**
	 * 座標のセッター
	 * @see {@link #position}
	 * @param position 座標
	 */
	public void setPosition(Vector2 position) { this.position = position; }
	/**
	 * 拡大率のセッター
	 * @see {@link #scale}
	 * @param scale 拡大率
	 */
	public void setScale(Vector2 scale) { this.scale = scale; }
	/**
	 * 回転角(度数法)のセッター
	 * @see {@link #rotation}
	 * @param rotation 回転角(度数法)
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
		radian = Math.toRadians(rotation);
	}
	/**
	 * 回転角(弧度法)のセッター
	 * @see {@link #radian}
	 * @param radian 回転角(弧度法)
	 */
	public void setRadian(double radian) {
		this.radian = radian;
		rotation = Math.toDegrees(radian);
	}
}
