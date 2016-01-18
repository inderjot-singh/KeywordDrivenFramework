/**
 * 
 */
package utility;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author inderjot.singh
 *
 */
public class ExcelUtils {
  
  private static XSSFWorkbook ExcelWBook;
  private static XSSFSheet ExcelWSheet;
  private static XSSFCell Cell;
  
  public static void setExcelFile(String path, String sheetName) throws Exception{
    
    try {
      FileInputStream excelFile = new FileInputStream(path);
      ExcelWBook = new XSSFWorkbook(excelFile);
      ExcelWSheet = ExcelWBook.getSheet(sheetName);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw e;
    }
  }
  
  public static String getCellData(int rowNum, int colNum){
      
    Cell = ExcelWSheet.getRow(rowNum).getCell(colNum); 
    String CellData = null;
    if(Cell != null) {     
      CellData = Cell.getStringCellValue();
    }
    return CellData;
  }
}
