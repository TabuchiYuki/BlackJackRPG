package programs.system;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Excelファイルを読み込む
 * @author 進藤颯斗
 */
public class ExcelLoader {
	// 定数
	private final String EXCEL_REGISTRY_LOCAL_PATH = "resources/excel/";
	private final String CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private final String EXCEL_PROJECT_EXTEND = ".xlsx";
	
	// 静的フィールド
	private static ExcelLoader instance;
	
	// フィールド
	private List<Workbook> excels = new ArrayList<Workbook>();
	
	/**
	 * プライベートコンストラクタ
	 */
	private ExcelLoader() { }
	
	/**
	 * インスタンスのゲッター
	 * 	@see {@link #instance}
	 * @return インスタンス
	 */
	public static ExcelLoader getInstance() {
		if(Objects.isNull(instance)) {
			instance = new ExcelLoader();
		}
		return instance;
	}
	/**
	 * Excelデータリストのゲッター
	 * @see {@link #excels}
	 * @return Excelデータリスト
	 */
	public List<Workbook> getExcels() { return excels; }
	
	/**
	 * Excelデータを読み込む
	 * @param fileName ファイル名
	 * @return 取得したExcelデータ
	 */
	public Workbook loadExcelData(String fileName) {
		Workbook wb;
		
		StringBuilder sb = new StringBuilder();
		sb.append(CLASS_PATH);
		sb.append(EXCEL_REGISTRY_LOCAL_PATH);
		sb.append(fileName);
		sb.append(EXCEL_PROJECT_EXTEND);
		
		String path = sb.toString();
		
		try(InputStream ist = new FileInputStream(path)) {
			wb = WorkbookFactory.create(ist);
			return wb;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Excelデータからセルを取り出す
	 * @param wb Excelデータ
	 * @param sheet シートのインデックス番号
	 * @param rowIndex 行のインデックス番号
	 * @param columnIndex 列のインデックス番号
	 * @return 取り出したセル
	 */
	public Cell getCell(Workbook wb, int sheet, int rowIndex, int columnIndex) {
		Sheet sh;
		Row row;
		Cell cell;
		
		sh = wb.getSheetAt(sheet);
		row = sh.getRow(rowIndex);
		cell = row.getCell(columnIndex);
		
		return cell;
	}
	
	/**
	 * Excelデータを読み込み、リストに追加する
	 * @param fileName ファイル名
	 */
	public void loadAndAddExcelDataList(String fileName) {
		Workbook wb = loadExcelData(fileName);
		excels.add(wb);
	}
}
