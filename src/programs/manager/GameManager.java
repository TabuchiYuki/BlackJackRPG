package programs.manager;

import java.awt.image.BufferedImage;

import programs.data.GraphicData;
import programs.data.Vector2;
import programs.graphic.GraphicDrawer;
import programs.system.ImageLoader;

/**
 * ゲーム進行を管理するマネージャークラス
 * @author 田淵勇輝
 */
public class GameManager {
	/**
	 * エントリポイント
	 * @param args コマンドライン引数:使用しない
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception  {
		final int REFRESH_RATE = 30;
		final double REFRESH_TIME = 1.0d / (double)REFRESH_RATE;
		
		GraphicDrawer graphicDrawer = new GraphicDrawer();
		
		BufferedImage image = ImageLoader.getInstance().loadImage("sample.png");
		GraphicData graphic = new GraphicData(image, new Vector2(100.0d, 200.0d), new Vector2(1.0d, 1.0d), 0.0d, new Vector2(0.0d, 0.0d));
		graphicDrawer.getGraphicData().add(graphic);
		
		WindowManager.getInstance().createWindow("Test", 800, 600);
		WindowManager.getInstance().setIcon(ImageLoader.getInstance().loadImage("testicon.png"));
		WindowManager.getInstance().getFrame().add(graphicDrawer);
		
		while(true) {
			Thread.sleep(1000/REFRESH_RATE);
			graphicDrawer.repaint();
		}
	}
}
