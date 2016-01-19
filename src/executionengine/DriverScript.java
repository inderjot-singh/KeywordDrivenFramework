/**
 * 
 */
package executionengine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;


/**
 * @author inderjot.singh
 *
 */
public class DriverScript {

  public static ActionKeywords actionKeywords;
  public static String         sActionKeyword;
  public static String         sPageObject;
  public static Method         method[];
  public static Properties     OR;
  public static String         testCaseID;
  public static String         runMode;
  public static int            testStep;
  public static int            testLastStep;
  public static boolean        bResult;
  public static XSSFWorkbook ExcelWBook;
  public static String data;

  public static void main(String args[]) throws Exception {

    System.out.println("Test started.");
    DOMConfigurator.configure("log4j.xml");
    ExcelWBook = ExcelUtils.setExcelFile(Constants.excelFilePath);
    actionKeywords = new ActionKeywords();
    method = actionKeywords.getClass().getMethods();
    FileInputStream fs = new FileInputStream(Constants.OR_Path);
    OR = new Properties(System.getProperties());
    OR.load(fs);
    executeTestCase();
  }

  private static void executeTestCase() throws Exception {

    int totalTestCases = ExcelUtils.getRowCount(Constants.Sheet_TestCases);

    for (int testcase = 1; testcase <= totalTestCases; testcase++) {

      bResult = true;
      testCaseID = ExcelUtils.getCellData(testcase, Constants.Col_TestCaseID, Constants.Sheet_TestCases);

      Log.startTestCase(testCaseID);

      runMode = ExcelUtils.getCellData(testcase, Constants.Col_RunMode, Constants.Sheet_TestCases);

      if (runMode.equalsIgnoreCase("Yes")) {

        testStep = ExcelUtils.getRowNumber(testCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
        testLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, testCaseID, testStep);

        bResult = true;
        for (; testStep < testLastStep; testStep++) {

          sActionKeyword = ExcelUtils.getCellData(testStep, Constants.Col_ActionKeyword, Constants.Sheet_TestSteps);
          sPageObject = ExcelUtils.getCellData(testStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
          data = ExcelUtils.getCellData(testStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
          executeActions();

          if (bResult == false) {
            ExcelUtils.setCellData(Constants.KEYWORD_FAIL, testcase, Constants.Col_Result, Constants.Sheet_TestCases);
            Log.endTestCase(testCaseID);
            break;
          }

        }
        if (bResult == true) {
          ExcelUtils.setCellData(Constants.KEYWORD_PASS, testcase, Constants.Col_Result, Constants.Sheet_TestCases);
          Log.endTestCase(testCaseID);
        }
      }
    }
  }

  private static void executeActions() throws Exception {

    try {
      for (int i = 0; i < method.length; i++) {

        if (method[i].getName().equals(sActionKeyword)) {
          method[i].invoke(actionKeywords, sPageObject, data);
          if (bResult == true) {
            ExcelUtils.setCellData(Constants.KEYWORD_PASS, testStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
            break;
          }else{
            ExcelUtils.setCellData(Constants.KEYWORD_FAIL, testStep, Constants.Col_TestStepResult, Constants.Sheet_TestSteps);
            ActionKeywords.closeBrowser("","");
            break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
