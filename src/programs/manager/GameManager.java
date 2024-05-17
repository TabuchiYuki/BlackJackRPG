package programs.manager;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

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
		
		BufferedImage cardImage = ImageLoader.getInstance().loadImage("cards.png");
		
		BufferedImage image = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 2, 12);
		GraphicData graphic = new GraphicData(image, new Vector2(400.0d, 300.0d), new Vector2(0.5d, 0.5d), 0.0d, new Vector2(0.0d, 0.0d));
		graphicDrawer.getGraphicData().add(graphic);
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	WindowManager.getInstance().createWindow("Black Jack Quest", 800, 600);
		    	WindowManager.getInstance().getFrame().add(graphicDrawer);
		    }
		});
		
		while(true) {
			Thread.sleep(1000/REFRESH_RATE);
			graphic.addRotation(30.0d * REFRESH_TIME);
			graphicDrawer.repaint();
		}
	}
}
