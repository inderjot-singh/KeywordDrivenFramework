/**
 * 
 */
package config;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import executionengine.DriverScript;
import utility.Log;
import static executionengine.DriverScript.OR;

/**
 * @author inderjot.singh
 *
 */
public class ActionKeywords {

  public static WebDriver driver;

  public static void openBrowser(String object, String data) {

    try {
      Log.info("Opening Browser");
      driver = new FirefoxDriver();
    } catch (Exception e) {
      Log.info("Not able to open Browser --- " + e.getMessage());
      DriverScript.bResult = false;
    }
  }

  public static void navigate(String object, String data) {

    try {
      Log.info("Navigating to URL");
      driver.manage().window().maximize();
      driver.get(Constants.URL);
    } catch (Exception e) {
      Log.info("Not able to navigate --- " + e.getMessage());
      DriverScript.bResult = false;
    }
  }

  public static void inputSearchKeyword(String object, String data) {

    try {
      driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);;
    } catch (Exception e) {
      Log.error("Not able to Enter search word --- " + e.getMessage());
      DriverScript.bResult = false;
    }
  }

  public static void click(String object, String data) throws InterruptedException {

    try {
      Log.info("Clicking on Webelement " + object);
      driver.findElement(By.xpath(OR.getProperty(object))).click();
      Thread.sleep(2000L);
    } catch (Exception e) {
      Log.error("Not able to click --- " + e.getMessage());
      DriverScript.bResult = false;
    }
  }

  public static void closeBrowser(String object, String data) {

    try {
      Log.info("Closing the browser");
      driver.close();
      System.out.println("Test ended.");
    } catch (Exception e) {
      Log.error("Not able to Close the Browser --- " + e.getMessage());
      DriverScript.bResult = false;
    }
  }
}
