package programs.system;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * 画像を読み込むためのシングルトンクラス
 * @author 田淵勇輝
 */
public class ImageLoader{
	// 定数
	private final String IMAGE_REGISTRY_LOCAL_PATH = "resources/images/";
	private final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static ImageLoader instance;
	
	private Map<String, BufferedImage> imagesMap = new HashMap<>();
	
	/**
	 * プライベートコンストラクタ
	 */
	private ImageLoader() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return
	 */
	public static ImageLoader getInstance() {
		if(Objects.isNull(instance)) {
			instance = new ImageLoader();
		}
		return instance;
	}
	/**
	 * 画像のデータマップのゲッター
	 * @see {@link #imagesMap}
	 * @return 画像のデータマップ
	 */
	public Map<String, BufferedImage> getImagesMap() { return imagesMap; }
	
	/**
	 * 画像を取得
	 * @param name 画像の名前,拡張子も記述する
	 * @return 取得した画像,取得できなかった場合、nullを返す
	 * @throws IOException
	 */
	public BufferedImage loadImage(String name) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(CLASS_PATH);
		sb.append(IMAGE_REGISTRY_LOCAL_PATH);
		sb.append(name);
		String path = sb.toString();
		File file = new File(path);
		
		if(file.exists()) {
			return ImageIO.read(file);
		} else {
			return null;
		}
	}
	
	/**
	 * 画像をセルに分割
	 * @param img 分割する画像
	 * @param vertical 縦分割数
	 * @param horizontal 横分割数
	 * @param getVertical 取得するセルの縦インデックス
	 * @param getHorizontal 取得するセルの横インデックス
	 * @return 分割した画像
	 */
	public BufferedImage imageSplit(BufferedImage img, int horizontal, int vertical, int getHorizontal, int getVertical) {
		if(vertical <= 0 || horizontal <= 0 || getVertical < 0 || getHorizontal < 0
			|| getVertical > vertical || getHorizontal > horizontal) {
			System.out.print("不正な値が入力されています");
			return null;
		}
		
		int height = img.getHeight();
		int width = img.getWidth();
		
		int splitHeight = height / vertical;
		int splitWidth = width / horizontal;
		
		
		if(splitWidth < 1 || splitHeight < 1) {
			System.out.print("分割できません");
			return null;
		}
		
		int startX = getHorizontal * splitWidth;
		int startY = getVertical * splitHeight;
        
		BufferedImage splitImage = img.getSubimage(startX, startY, splitWidth, splitHeight);
		return splitImage;
	}
	
	/**
	 * 画像を読み込み、データマップに追加
	 * @param name 画像のファイル名
	 * @param key マップデータのキー
	 */
	public void loadAndAddImageMap(String name, String key) {
		try {
			BufferedImage image = loadImage(name);
			imagesMap.put(key, image);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
