package testcases;

import java.io.File;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LoginFunctionalityWithScreenshotInExtentReport {
	
	WebDriver driver;
	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest test;
	WebDriverWait wait;
	
	@BeforeClass
	public void setup()
	{
		extent = new ExtentReports();
		String filePath = Paths.get(System.getProperty("user.dir") + "/login-extent-report-wiht-screenshot.html").toString();
		spark = new ExtentSparkReporter(filePath);
		extent.attachReporter(spark);
		
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		driver = new ChromeDriver(option);
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		driver.manage().window().maximize();
		
	}
	
	@Test
	public void validateLoginFunctionalityWithScreenshotInExtentReport()
	{
		test = extent.createTest("validating Login Functionality With Screenshot In Extent Report ");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		test.info("Navigating to URL");
		driver.get(("https://www.facebook.com/"));
		test.info("Entering username");
		WebElement userName = driver.findElement(By.id("email"));
		wait.until(ExpectedConditions.visibilityOf(userName));
		userName.sendKeys("Hems");
		test.info("Entering password");
		driver.findElement(By.id("pass")).sendKeys("pass");
		
		test.info("clicking on login button");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("login")));
		driver.findElement(By.name("login")).click();
		try
		{
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("login")));
		TakesScreenshot screenshot = (TakesScreenshot)driver;
		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
		
		File desFile = new File("./Screenshots/loginScreenshotInReport.jpg");
		FileUtils.copyFile(srcFile, desFile);
		test.pass("Screenshot is saved in Screenshot folder");
		test.addScreenCaptureFromPath(desFile.getAbsolutePath());
		}
		catch(Exception e)
		{
			test.fail("Failed to take screenshot due to exception "+ e.getMessage());
		}
	}

	@AfterClass
	public void tearDown()
	{
		driver.quit();
		extent.flush();
		
	}
}
