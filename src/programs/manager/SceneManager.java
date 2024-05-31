package programs.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import programs.data.ClickEventData;
import programs.data.GraphicData;
import programs.data.SceneData;
import programs.data.Vector2;
import programs.system.ExcelLoader;
import programs.system.GameObject;
import programs.system.ImageLoader;

/**
 * シーンの管理をするクラス
 * @author 田淵勇輝
 */
public class SceneManager {
	private static SceneManager instance;
	
	private SceneData scene;
	private boolean firstFrame = false;
	
	/**
	 * プライベートコンストラクタ
	 */
	private SceneManager() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static SceneManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new SceneManager();
		}
		return instance;
	}
	/**
	 * シーンのゲッター
	 * @see {@link #scene}
	 * @return シーン
	 */
	public SceneData getScene() { return scene; }
	/**
	 * 実行されているシーンの最初のフレーム判定のゲッター
	 * @return 最初のフレーム判定
	 */
	public boolean isFirstFrame() { return firstFrame; }
	
	/**
	 * シーンを読み込む
	 * @param index 読み込むシーンのインデックス番号
	 */
	public void loadScene(int index) {
		// グラフィックを初期化
		GraphicManager.getInstance().getGraphicDataList().clear();
		GraphicManager.getInstance().getTextDataList().clear();
		
		// クリックイベントを初期化
		ClickEventManager.getInstance().getClickEventsMap().clear();
		
		Workbook wb = ExcelLoader.getInstance().getExcels().get(index);
		Sheet classSheet = wb.getSheetAt(0);
		Sheet graphicSheet = wb.getSheetAt(1);
		Sheet clickSheet = wb.getSheetAt(2);
		
		scene = new SceneData();
		
		// ゲームオブジェクトのロード
		boolean zeroRow = true;
		Iterator<Row> rows = classSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell classCell = row.getCell(0);
			if(classCell.getStringCellValue().isEmpty()) continue;
			
			String className = classCell.getStringCellValue();
			List<Object> args = new ArrayList<>();
			List<Class<?>> argTypes = new ArrayList<>();
			
			// コンストラクタの引数を取得
			for(int i = 1; i < row.getPhysicalNumberOfCells(); i++) {
				Cell cell = row.getCell(i);
				if(cell == null) continue;
				
				switch(cell.getCellType()) {
				case BOOLEAN:
					args.add(cell.getBooleanCellValue());
					argTypes.add(boolean.class);
					break;
				case STRING:
					args.add(cell.getStringCellValue());
					argTypes.add(String.class);
					break;
				case NUMERIC:
					args.add(cell.getNumericCellValue());
					argTypes.add(double.class);
					break;
				default:
					throw new IllegalArgumentException("Unsupported cell type: " + cell.getCellType());
				}
			}
			
			// ゲームオブジェクトを取得
			try {
				Class<?> clazz = Class.forName(className);
				
				if(GameObject.class.isAssignableFrom(clazz)) {
					Constructor<?> constructor = clazz.getConstructor(argTypes.toArray(new Class[0]));
					GameObject obj = (GameObject) constructor.newInstance(args.toArray());
					scene.getGameObjects().add(obj);
				} else {
					System.out.println(className + " does not implement GameObject");
				}
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		// グラフィックのロード
		zeroRow = true;
		rows = graphicSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell keyCell = row.getCell(0);
			if(keyCell.getStringCellValue().isEmpty()) continue;
			
			Cell layerCell = row.getCell(1);
			List<Double> args = new ArrayList<>();
			
			for(int i = 2; i < 9; i++) {
				args.add(row.getCell(i).getNumericCellValue());
			}
			
			GraphicData graphic = new GraphicData(
				ImageLoader.getInstance().getImagesMap().get(keyCell.getStringCellValue()),
				(int) layerCell.getNumericCellValue(),
				new Vector2(args.get(0), args.get(1)),
				new Vector2(args.get(2), args.get(3)),
				args.get(4),
				new Vector2(args.get(5), args.get(6)),
				true
			);
			GraphicManager.getInstance().getGraphicDataList().add(graphic);
		}
		GraphicManager.getInstance().sortLayer();
		
		// クリックイベントのロード
		zeroRow = true;
		rows = clickSheet.rowIterator();
		while(rows.hasNext()) {
			Row row = rows.next();
			
			if(zeroRow) {
				zeroRow = false;
				continue;
			}
			
			Cell keyCell = row.getCell(0);
			if(keyCell.getStringCellValue().isEmpty()) continue;
			
			List<Double> args = new ArrayList<>();
			Cell cursorCell = row.getCell(5);
			
			for(int i = 1; i < 5; i++) {
				args.add(row.getCell(i).getNumericCellValue());
			}
			
			ClickEventData clickEventData = new ClickEventData(
				new Vector2(args.get(0), args.get(1)),
				new Vector2(args.get(2), args.get(3)),
				cursorCell.getBooleanCellValue()
			);
			
			ClickEventManager.getInstance().getClickEventsMap().put(keyCell.getStringCellValue(), clickEventData);
		}
		
		// 全てのロードが終わったら最初のフレームとする
		firstFrame = true;
	}
	
	/**
	 * ゲームオブジェクトの初期化メソッドを呼び出す
	 */
	public void callInit() {
		scene.getGameObjects().forEach(object -> {
			object.init();
		});
		firstFrame = false;
	}
	
	/**
	 * ゲームオブジェクトのアップデートメソッドを呼び出す
	 */
	public void callUpdate() {
		List<GameObject> gameObjects = scene.getGameObjects();
		for(int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).update();
		}
	}
}
