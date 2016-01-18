/**
 * 
 */
package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Constants;
import executionengine.DriverScript;

/**
 * @author inderjot.singh
 *
 */
public class ExcelUtils {

  private static XSSFWorkbook ExcelWBook;
  private static XSSFSheet    ExcelWSheet;
  private static XSSFCell     Cell;
  private static XSSFRow      Row;

  public static XSSFWorkbook setExcelFile(String path) throws Exception {

    try {
      FileInputStream excelFile = new FileInputStream(path);
      ExcelWBook = new XSSFWorkbook(excelFile);
      // ExcelWSheet = ExcelWBook.getSheet(sheetName);
    } catch (Exception e) {
      Log.error("Class Utils | Method setExcelFile | Exception desc : " + e.getMessage());
      DriverScript.bResult = false;
    }
    return ExcelWBook;
  }

  public static String getCellData(int rowNum, int colNum, String sheetName) {

    try {
      ExcelWSheet = ExcelWBook.getSheet(sheetName);
      String CellData = null;
      Cell = ExcelWSheet.getRow(rowNum).getCell(colNum);
      CellData = Cell.getStringCellValue();
      return CellData;
    } catch (Exception e) {
      return "";
    }
  }

  public static int getRowCount(String sheetName) {

    int number = 0;
    try {
      ExcelWSheet = ExcelWBook.getSheet(sheetName);
      number = ExcelWSheet.getLastRowNum() + 1;
    } catch (Exception e) {
      Log.error("Class Utils | Method getRowCount | Exception desc : " + e.getMessage());
      DriverScript.bResult = false;
    }
    return number;
  }

  public static int getRowNumber(String sTestCaseName, int colNum, String sheetName) {

    int i = 0;
    try {
      ExcelWSheet = ExcelWBook.getSheet(sheetName);
      int rowCount = ExcelUtils.getRowCount(sheetName);

      for (i = 0; i < rowCount; i++) {
        if (ExcelUtils.getCellData(i, colNum, sheetName).equalsIgnoreCase(sTestCaseName)) {
          break;
        }
      }
    } catch (Exception e) {
      Log.error("Class Utils | Method getRowNumber | Exception desc : " + e.getMessage());
      DriverScript.bResult = false;
    }
    return i;
  }

  public static int getTestStepsCount(String sheetName, String sTestCaseID, int iTestCaseStart) throws Exception {

    try {
      for (int i = iTestCaseStart; i <= ExcelUtils.getRowCount(sheetName); i++) {
        if (!sTestCaseID.equals(ExcelUtils.getCellData(i, Constants.Col_TestCaseID, sheetName))) {
          int number = i;
          return number;
        }
      }
      ExcelWSheet = ExcelWBook.getSheet(sheetName);
      int number = ExcelWSheet.getLastRowNum() + 1;
      return number;
    } catch (Exception e) {
      Log.error("Class Utils | Method getTestStepsCount | Exception desc : " + e.getMessage());
      DriverScript.bResult = false;
      return 0;
    }
  }

  @SuppressWarnings("static-access")
  public static void setCellData(String Result, int RowNum, int ColNum, String SheetName) throws Exception {
    try {
      
      ExcelWSheet = ExcelWBook.getSheet(SheetName);
      Row = ExcelWSheet.getRow(RowNum);
      Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
      if (Cell == null) {
        Cell = Row.createCell(ColNum);
        Cell.setCellValue(Result);
      } else {
        Cell.setCellValue(Result);
      }
      // Constant variables Test Data path and Test Data file name
      FileOutputStream fileOut = new FileOutputStream(Constants.excelFilePath);
      ExcelWBook.write(fileOut);
      // fileOut.flush();
      fileOut.close();
      ExcelWBook = new XSSFWorkbook(new FileInputStream(Constants.excelFilePath));
    } catch (Exception e) {
      DriverScript.bResult = false;
    }
  }

}
