package programs.data;

import java.awt.image.BufferedImage;

/**
 * グラフィックのデータ
 * @author 田淵勇輝
 */
public class GraphicData {
	private BufferedImage image;
	private int layer;
	private Vector2 pivot;
	private Vector2 position;
	private Vector2 scale;
	private double rotation;
	private double radian;
	private Vector2 shear;
	private boolean show;
	
	/**
	 * 全てのデータを初期化するコンストラクタ
	 * @param img 画像
	 * @param layer レイヤー番号
	 * @param pivot ピボット座標
	 * @param position 座標
	 * @param scale 拡大率
	 * @param rotation 回転角(度数法)
	 * @param shear せん断
	 * @param show 表示/非表示
	 */
	public GraphicData(BufferedImage image, int layer, Vector2 pivot, Vector2 position, Vector2 scale, double rotation, Vector2 shear, boolean show) {
		this.image = image;
		this.layer = layer;
		this.pivot = pivot;
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		radian = Math.toRadians(rotation);
		this.shear = shear;
		this.show = show;
	}
	
	/**
	 * コンストラクタ
	 * ピボットの位置は画像の中心になる
	 * @param image 画像
	 * @param layer レイヤー番号
	 * @param position 座標
	 * @param scale 拡大率
	 * @param rotation 回転角(度数法)
	 * @param shear せん断
	 * @param show 表示/非表示
	 */
	public GraphicData(BufferedImage image, int layer, Vector2 position, Vector2 scale, double rotation, Vector2 shear, boolean show) {
		this.image = image;
		this.layer = layer;
		pivot = new Vector2((double)image.getWidth() / 2, (double)image.getHeight() / 2);
		this.position = position;
		this.scale = scale;
		this.rotation = rotation;
		rotation = circle(rotation, 0.0d, 360.0d);
		radian = Math.toRadians(rotation);
		this.shear = shear;
		this.show = show;
	}
	
	/**
	 * コンストラクタ
	 * 画像以外は自動的に初期化される
	 * @param image 画像
	 * @param layer レイヤー番号
	 * @param show 表示/非表示
	 */
	public GraphicData(BufferedImage image, int layer, boolean show) {
		this.image = image;
		this.layer = layer;
		pivot = new Vector2(image.getWidth() / 2, image.getHeight() / 2);
		position = new Vector2(0.0d, 0.0d);
		scale = new Vector2(1.0d, 1.0d);
		rotation = 0.0d;
		radian = Math.toRadians(rotation);
		shear = new Vector2(0.0d, 0.0d);
		this.show = show;
	}
	
	/**
	 * 画像のゲッター
	 * @see {@link #image}
	 * @return 画像
	 */
	public BufferedImage getImage() { return image; }
	/**
	 * レイヤー番号のゲッター
	 * @see {@link #layer}
	 * @return
	 */
	public int getLayer() { return layer; }
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
	 * せん断のゲッター
	 * @see {@link #shear}
	 * @return せん断
	 */
	public Vector2 getShear() { return shear; }
	/**
	 * 表示/非表示のゲッター
	 * @return 表示/非表示
	 */
	public boolean isShow() { return show; }
	
	/**
	 * 画像のセッター
	 * @see {@link #image}
	 * @param image 画像
	 */
	public void setImage(BufferedImage image) { this.image = image; }
	/**
	 * レイヤー番号のセッター
	 * @see {@link #layer}
	 * @param layer レイヤー番号
	 */
	public void setLayer(int layer) { this.layer = layer; }
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
		rotation = circle(rotation, 0.0d, 360.0d);
		radian = Math.toRadians(rotation);
	}
	/**
	 * せん断のセッター
	 * @see {@link #shear}
	 * @param shear せん断
	 */
	public void setShear(Vector2 shear) { this.shear = shear; }
	/**
	 * 表示/非表示のセッター
	 * @param show 表示/非表示
	 */
	public void setShow(boolean show) { this.show = show; }
	
	//-----------------アクセサここまで-----------------
	
	/**
	 * ピボットを加算
	 * @param piv ピボットに加算する値
	 */
	public void addPivot(Vector2 piv) {
		pivot.setX(pivot.getX() + piv.getX());
		pivot.setY(pivot.getY() + piv.getY());
	}
	
	/**
	 * 座標を加算
	 * @param pos 座標に加算する値
	 */
	public void addPosition(Vector2 pos) {
		position.setX(position.getX() + pos.getX());
		position.setY(position.getY() + pos.getY());
	}
	
	/**
	 * 拡大率を加算
	 * @param sca 拡大率に加算する値
	 */
	public void addScale(Vector2 sca) {
		scale.setX(scale.getX() + sca.getX());
		scale.setY(scale.getY() + sca.getY());
	}
	
	/**
	 * 回転角(度数法)を加算
	 * @param rot 回転角(度数法)に加算する値
	 */
	public void addRotation(double rot) {
		rotation += rot;
		rotation = circle(rotation, 0.0d, 360.0d);
		radian = Math.toRadians(rotation);
	}
	
	/**
	 * せん断を加算
	 * @param shr せん断に加算する値
	 */
	public void addShear(Vector2 shr) {
		shear.setX(shear.getX() + shr.getX());
		shear.setY(shear.getY() + shr.getY());
	}
	
	/**
	 * 値の正規化
	 * @param value 正規化する値
	 * @param min 最小値
	 * @param max 最大値
	 * @return 正規化された値
	 */
	private double circle(double value, double min, double max) {
		double range = max - min;
		double normalized = (value - min) % range;
        if (normalized < 0) {
            normalized += range;
        }
        return normalized + min;
	}
}
