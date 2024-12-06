package testcases;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LoginFunctionalityWithScreenshot {
	
	WebDriver driver;
		
	@BeforeClass
	public void setup()
	{
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		driver.manage().window().maximize();
		driver.get("https://www.facebook.com/");
		
	}
	
	@Test
	public void validateLoginFunctionalityAndTakingScreenshot()
	{
		driver.get(("https://www.facebook.com/"));
		driver.findElement(By.id("email")).sendKeys("hems");
		
		driver.findElement(By.id("pass")).sendKeys("pass");
		
		driver.findElement(By.name("login")).click();
		try
		{
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File srcPath = screenshot.getScreenshotAs(OutputType.FILE);
		File destinationPath = new File("./Screenshots/loginScreenshot.png");
		FileUtils.copyFile(srcPath, destinationPath);
		}
		catch(Exception IOException)
		{
			
		}
	}
	
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

}
