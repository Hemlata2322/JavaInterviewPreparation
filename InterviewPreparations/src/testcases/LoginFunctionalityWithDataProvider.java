package testcases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class LoginFunctionalityWithDataProvider {
	
	WebDriver driver;
	
	@BeforeClass
	public void setup()
	{
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "./Driver.chromedriver.exe");
		driver.manage().window().maximize();
		
	}
	@Test
	public void validatingLoginWithDifferentUserData()
	{
		driver.get("https://www.swiggy.com/");
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

}
