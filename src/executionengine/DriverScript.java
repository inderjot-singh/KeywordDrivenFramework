/**
 * 
 */
package executionengine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;



/**
 * @author inderjot.singh
 *
 */
public class DriverScript {
  
  public static ActionKeywords actionKeywords;
  public static String sActionKeyword;
  public static String sPageObject;
  public static Method method[];
  public static Properties OR;

  public static void main(String args[]) throws Exception {

    System.out.println("Test started.");
    actionKeywords = new ActionKeywords();
    method = actionKeywords.getClass().getMethods();
    String path = Constants.excelFilePath;
    ExcelUtils.setExcelFile(path, Constants.Sheet_TestSteps);
    
    FileInputStream fs = new FileInputStream(Constants.OR_Path);
    OR = new Properties(System.getProperties());
    OR.load(fs);

    for (int row = 0; row <= 5; row++) {

      sActionKeyword = ExcelUtils.getCellData(row, Constants.Col_ActionKeyword);
      sPageObject = ExcelUtils.getCellData(row, Constants.Col_PageObject);
      executeActions();
    }
  }
  
  private static void executeActions() throws Exception{
    
    try {
      for(int i=0; i<method.length; i++){
        
        if(method[i].getName().equals(sActionKeyword)){
          method[i].invoke(actionKeywords, sPageObject);
          break;
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }    
}
