package programs.manager;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import programs.data.GraphicData;
import programs.data.Vector2;
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
		
		GraphicManager graMgr = GraphicManager.getInstance();
		
		BufferedImage cardImage = ImageLoader.getInstance().loadImage("cards.png");
		
		BufferedImage card1 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 2, 12);
		BufferedImage card2 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 0, 3);
		GraphicData graphic1 = new GraphicData(card1, 2, new Vector2(400.0d, 300.0d), new Vector2(0.5d, 0.5d), 0.0d, new Vector2(0.0d, 0.0d), true);
		GraphicData graphic2 = new GraphicData(card2, 1, new Vector2(300.0d, 400.0d), new Vector2(0.5d, 0.5d), 180.0d, new Vector2(1.0d, 0.0d), true);
		graMgr.getGraphicData().add(graphic1);
		graMgr.getGraphicData().add(graphic2);
		graMgr.sortLayer();
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	WindowManager.getInstance().createWindow("Black Jack Quest", 800, 600);
		    	WindowManager.getInstance().getFrame().add(graMgr);
		    }
		});
		
		while(true) {
			Thread.sleep(1000/REFRESH_RATE);
			graphic1.addRotation(30.0d * REFRESH_TIME);
			graMgr.repaint();
		}
	}
}
