package testcases;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class LoginFunctionalityWithExtentReport {
	
	WebDriver driver;
	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest test;
	
	@BeforeClass
	public void setup()
	{
		extent = new ExtentReports();
		String filePath = Paths.get(System.getProperty("user.dir") + "/login-extent-report.html").toString();
		
		spark = new ExtentSparkReporter(filePath);
		extent.attachReporter(spark);		
		
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");			
		driver.manage().window().maximize();				
	}
	
	@Test
	public void validateLoginFunctionality()
	{
		try
		{
			test = extent.createTest("Validating login functionality");
			test.info("Navigating to facebook login page");
			driver.get("https://www.facebook.com/");
			
			test.info("Entering Username");
			driver.findElement(By.id("email")).sendKeys("Hems");
			Thread.sleep(1000);
			
			test.info("Entering password");
			driver.findElement(By.id("pass")).sendKeys("pass");
			Thread.sleep(1000);
			
			test.info("Clicking on login button");
			driver.findElement(By.name("login")).click();
			
			String displayedName = driver.findElement(By.xpath("//span[text() = 'Kia Fatnani']")).getText();
			if(displayedName.equalsIgnoreCase("Kia Fatnani"))
			{
				test.pass("Successfully logged in");
			}
			else
			{
				test.fail("Login failed");
			}
		}
		catch(Exception e)
		{
			test.fail("Login failed due to exception "+e.getMessage());
		}
		
	}
	
	@AfterClass
	public void tearDown()
	{
		extent.flush();
		driver.quit();
		
	}

}
