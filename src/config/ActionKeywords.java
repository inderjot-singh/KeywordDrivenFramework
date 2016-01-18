/**
 * 
 */
package config;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static executionengine.DriverScript.OR;

/**
 * @author inderjot.singh
 *
 */
public class ActionKeywords {

  public static WebDriver driver;

  public static void openBrowser(String object) {
    
    driver = new FirefoxDriver();
  }

  public static void navigate(String object) {
    
    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    driver.get(Constants.URL);
  }

  public static void inputSearchKeyword(String object) {

   // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.findElement(By.id(OR.getProperty(object))).sendKeys("Java");;
  }

  public static void click(String object) throws InterruptedException {

   // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.findElement(By.className(OR.getProperty(object))).click();
    Thread.sleep(2000L);
  }
  
  public static void closeBrowser(String object) {

    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.close();
    System.out.println("Test ended.");
  }
}
