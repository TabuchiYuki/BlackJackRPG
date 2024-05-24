package programs.manager;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import programs.data.ClickEventData;
import programs.data.GraphicData;
import programs.data.TextGraphicData;
import programs.data.Vector2;
import programs.system.ExcelLoader;
import programs.system.FontLoader;
import programs.system.ImageLoader;

/**
 * ゲーム進行を管理するマネージャークラス
 * @author 田淵勇輝
 */
public class GameManager {
	private static final int REFRESH_RATE = 30;
	private static final double REFRESH_TIME = 1.0d / (double)REFRESH_RATE;
	private static final int FIRST_LOAD_SCENE_INDEX = 0;
	
	/**
	 * リフレッシュタイムのゲッター
	 * @see {@link #REFRESH_TIME}
	 * @return リフレッシュタイム
	 */
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
		graMgr.getGraphicDataList().add(jokerGra);
		TextGraphicData loadText = new TextGraphicData("Now Loading", 24, new Vector2(520.0d, 550.0d), Color.BLACK);
		graMgr.getTextDataList().add(loadText);
		
		WindowManager.getInstance().getFrame().getContentPane().add(graMgr);
		WindowManager.getInstance().createWindow("「21」～ギャンブラーの戦い～", 800, 600);
		
		// マルチスレッドで初期化処理を行う
		SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() {
				initLoad();
		    	return "show window";
			}
		};
		
		worker.execute();
		
		jokerGra.setPosition(new Vector2(740.0d, 520.0d));
		jokerGra.setScale(new Vector2(0.15d, 0.15d));
		jokerGra.setShear(new Vector2(0.0d, 0.0d));
		
		boolean jokerRotateHalfLoop = false;
		double jokerRotateProgress = 0.0d;
		double loadTextTimer = 0.0d;
		
		// 初期化処理中の処理
		while(!worker.isDone()) {
			try {
				// 画像表示
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
				
				// テキスト表示
				if(loadTextTimer < 1.2d) {
					String text = "Now Loading";
					for(int i = 0; i < (int) (loadTextTimer / 0.3d); i++) {
						text += ".";
					}
					loadText.setText(text);
					loadTextTimer += REFRESH_TIME;
				} else {
					loadTextTimer -= 1.2d;
				}
				
				// 描画
				graMgr.repaint();
				Thread.sleep(1000/REFRESH_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		graMgr.getGraphicDataList().clear();
		graMgr.getTextDataList().clear();
		
		/*
		System.out.println(SaveManager.getInstance().getSaveData().getGrade());
		SaveManager.getInstance().getSaveData().setGrade(DifficultyGrade.MIDDLE);
		SaveManager.getInstance().save();
		System.out.println(SaveManager.getInstance().getSaveData().getGrade());
		*/
		
		// 最初のシーンをロードする
		SceneManager.getInstance().loadScene(FIRST_LOAD_SCENE_INDEX);
		
		// ゲーム処理
		while(true) {
			try {
				// クリックイベントの更新メソッドを呼び出す
				Map<String,ClickEventData> clickEvents = ClickEventManager.getInstance().getClickEventsMap();
				clickEvents.forEach((k, v) ->{
					v.update();
				});
				
				// ゲームオブジェクトの初期化メソッドと更新メソッドを呼び出す
				if(SceneManager.getInstance().isFirstFrame()) {
					SceneManager.getInstance().callInit();
				}
				SceneManager.getInstance().callUpdate();
				
				// 描画更新
				graMgr.repaint();
				Thread.sleep(1000/REFRESH_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初期化ロード
	 */
	private static void initLoad() {
		Workbook initData = ExcelLoader.getInstance().loadExcelData("InitializeData");
		Sheet imageSheet = initData.getSheetAt(0);
		boolean zeroRow = true;
		
		// 画像ロード
		Iterator<Row> rows = imageSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell fileNameCell = row.getCell(0);
			Cell keyNameCell = row.getCell(1);
			
			ImageLoader.getInstance().loadAndAddImageMap(fileNameCell.getStringCellValue(), keyNameCell.getStringCellValue());
		}
		
		Sheet fontSheet = initData.getSheetAt(1);
		zeroRow = true;
		
		// フォントロード
		rows = fontSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell fileNameCell = row.getCell(0);
			
			FontLoader.getInstance().loadAndAddFontList(fileNameCell.getStringCellValue());
		}
		
		
		Sheet sceneSheet = initData.getSheetAt(2);
		zeroRow = true;
		
		// シーンデータロード
		rows = sceneSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell nameCell = row.getCell(0);
			
			ExcelLoader.getInstance().loadAndAddExcelDataList(nameCell.getStringCellValue());
		}
		
		// セーブデータロード
		SaveManager.getInstance().load();
	}
}
