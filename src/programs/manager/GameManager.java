package programs.manager;

import java.awt.image.BufferedImage;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import programs.data.GraphicData;
import programs.data.Vector2;
import programs.system.ExcelLoader;
import programs.system.ImageLoader;

/**
 * ゲーム進行を管理するマネージャークラス
 * @author 田淵勇輝
 */
public class GameManager {
	private static final int REFRESH_RATE = 30;
	private static final double REFRESH_TIME = 1.0d / (double)REFRESH_RATE;
	
	public double getRefreshTime() { return REFRESH_TIME; }
	
	/**
	 * エントリポイント
	 * @param args コマンドライン引数:使用しない
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception  {
		GraphicManager graMgr = GraphicManager.getInstance();
		
		BufferedImage joker = ImageLoader.getInstance().loadImage("card_joker.png");
		GraphicData jokerGra = new GraphicData(joker, 0, true);
		graMgr.getGraphicData().add(jokerGra);
		
		WindowManager.getInstance().createWindow("Black Jack Quest", 800, 600);
		WindowManager.getInstance().getFrame().add(graMgr);
		
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() {
				// 初期化処理はここで行う
				Workbook initData = ExcelLoader.getInstance().loadExcelData("InitializeData");
				Sheet imageSheet = initData.getSheetAt(0);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
		    	return "show window";
			}
		};
		
		worker.execute();
		
		jokerGra.setPosition(new Vector2(740.0d, 520.0d));
		jokerGra.setScale(new Vector2(0.15d, 0.15d));
		jokerGra.setShear(new Vector2(0.0d, 0.0d));
		
		boolean jokerRotateHalfLoop = false;
		double jokerRotateProgress = 0.0d;
		
		// 初期化処理中の処理
		while(!worker.isDone()) {
			try {
				if(jokerRotateHalfLoop) {
					if(jokerRotateProgress <= -0.5d) {
						jokerRotateHalfLoop = false;
						jokerRotateProgress = -0.5d;
					} else {
						jokerRotateProgress -= REFRESH_TIME;
					}
					jokerGra.setShear(new Vector2(0.0d, 0.15d - (0.3d * Math.abs(jokerRotateProgress))));
				} else {
					if(jokerRotateProgress >= 0.5d) {
						jokerRotateHalfLoop = true;
						jokerRotateProgress = 0.5d;
					} else {
						jokerRotateProgress += REFRESH_TIME;
					}
					jokerGra.setShear(new Vector2(0.0d, -0.15d + (0.3d * Math.abs(jokerRotateProgress))));
				}
				jokerGra.setScale(new Vector2(0.4d * jokerRotateProgress, 0.2d));
				graMgr.repaint();
				Thread.sleep(1000/REFRESH_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		graMgr.getGraphicData().clear();
		jokerGra = null;
		
		// ゲーム処理
		try {
			
			// 描画更新
			graMgr.repaint();
			Thread.sleep(1000/REFRESH_RATE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
