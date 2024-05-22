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
		
		BufferedImage bg = ImageLoader.getInstance().loadImage("BackGround.png");
		BufferedImage cardFrame = ImageLoader.getInstance().loadImage("trumpframe.png");
		BufferedImage card1 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 1, 2);
		BufferedImage card2 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 3, 3);
		BufferedImage card3 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 0, 0);
		BufferedImage card4 = ImageLoader.getInstance().imageSplit(cardImage, 5, 13, 1, 12);
		
		GraphicData bgGra = new GraphicData(bg, -1, new Vector2(0.0d, 0.0d), new Vector2(0.0d, 0.0d), new Vector2(1.0d, 1.0d), 0.0d, new Vector2(0.0d, 0.0d), true);
		GraphicData frame1 = new GraphicData(cardFrame, 1, new Vector2(320.0d, 100.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData frame2 = new GraphicData(cardFrame, 1, new Vector2(480.0d, 100.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData frame3 = new GraphicData(cardFrame, 1, new Vector2(320.0d, 500.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData frame4 = new GraphicData(cardFrame, 1, new Vector2(480.0d, 500.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData c1 = new GraphicData(card1, 2, new Vector2(320.0d, 100.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData c2 = new GraphicData(card2, 2, new Vector2(480.0d, 100.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData c3 = new GraphicData(card3, 2, new Vector2(320.0d, 500.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		GraphicData c4 = new GraphicData(card4, 2, new Vector2(480.0d, 500.0d), new Vector2(0.3d, 0.3d), 0.0f, new Vector2(0.0d, 0.0d), true);
		
		graMgr.getGraphicData().add(bgGra);
		graMgr.getGraphicData().add(frame1);
		graMgr.getGraphicData().add(frame2);
		graMgr.getGraphicData().add(frame3);
		graMgr.getGraphicData().add(frame4);
		graMgr.getGraphicData().add(c1);
		graMgr.getGraphicData().add(c2);
		graMgr.getGraphicData().add(c3);
		graMgr.getGraphicData().add(c4);
		graMgr.sortLayer();
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	WindowManager.getInstance().createWindow("Black Jack Quest", 800, 600);
		    	WindowManager.getInstance().getFrame().add(graMgr);
		    }
		});
	}
}
