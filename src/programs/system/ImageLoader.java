package programs.system;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * 画像を読み込むためのシングルトンクラス
 * @author 田淵勇輝
 */
public class ImageLoader {
	// 定数
	private final String IMAGE_REGISTRY_LOCAL_PATH = "resources/images/";
	private final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	// インスタンス
	private static ImageLoader instance;
	
	/**
	 * プライベートコンストラクタ
	 */
	private ImageLoader() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static ImageLoader getInstance() {
		if(Objects.isNull(instance)) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	/**
	 * 画像を取得
	 * @param name 画像の名前,拡張子も記述する
	 * @return 取得した画像,取得できなかった場合、nullを返す
	 * @throws IOException
	 */
	public Image loadImage(String name) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(CLASS_PATH);
		sb.append(IMAGE_REGISTRY_LOCAL_PATH);
		sb.append(name);
		String path = sb.toString();
		File file = new File(path);
		
		if(file.exists()) {
			BufferedImage buffImg = ImageIO.read(file);
			return buffImg.getScaledInstance(buffImg.getWidth(), buffImg.getHeight(), Image.SCALE_DEFAULT);
		} else {
			return null;
		}
	}
}
