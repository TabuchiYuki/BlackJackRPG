package programs.system;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
	
	private static ExcelLoader instance;
	
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
	 * Excelデータを読み込む
	 * @param fileName ファイル名
	 * @param sheet シートの番号
	 * @param rowIndex 行の番号
	 * @param columnIndex 列の番号
	 * @return 取得した値
	 */
	public int loadExcelData(String fileName, int sheet, int rowIndex, int columnIndex) {
		Workbook wb;
		Sheet sh;
		Row row;
		Cell cell;
		
		StringBuilder sb = new StringBuilder();
		sb.append(CLASS_PATH);
		sb.append(EXCEL_REGISTRY_LOCAL_PATH);
		sb.append(fileName);
		sb.append(EXCEL_PROJECT_EXTEND);
		
		String path = sb.toString();
		
		try(InputStream ist = new FileInputStream(path)) {
			wb = WorkbookFactory.create(ist);
			sh = wb.getSheetAt(sheet);
			row = sh.getRow(rowIndex);
			cell = row.getCell(columnIndex);
			
			if(cell.getCellType() == CellType.NUMERIC) {
				return (int) cell.getNumericCellValue();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
