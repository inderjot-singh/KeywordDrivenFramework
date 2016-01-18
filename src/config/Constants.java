/**
 * 
 */
package config;

/**
 * @author inderjot.singh
 *
 */
public class Constants {
  
  public static final String URL = "https://www.google.co.in/?gfe_rd=cr&ei=mY6cVuPDMqLP8gfy55v4DQ";
  
  public static final String excelFilePath = System.getProperty("user.dir") + "\\src\\dataengine\\DataEngine.xlsx";
  
  public static final int Col_TestCaseID = 0;   
  
  public static final int Col_TestScenarioID =1 ;
  
  public static final int Col_ActionKeyword =4 ;
  
  public static final int Col_PageObject =3 ;

  public static final String Sheet_TestSteps = "Test Steps";
  
  public static final String OR_Path = System.getProperty("user.dir") + "\\src\\config\\OR.properties";
  
}
