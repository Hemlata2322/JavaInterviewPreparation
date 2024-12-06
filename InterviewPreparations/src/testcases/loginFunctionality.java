package testcases;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class loginFunctionality {
	
	WebDriver driver;
	@BeforeClass
	public void setup()
	{
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		driver = new ChromeDriver(option);
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		driver.manage().window().maximize();
	}
	
	@Test
	public void validateValidLogin()
	{
		try
		{		
		
		driver.get(("https://www.facebook.com/"));
		driver.findElement(By.id("email")).sendKeys("Hems");
		Thread.sleep(2000);
		driver.findElement(By.id("pass")).sendKeys("pass");
		Thread.sleep(1000);
		driver.findElement(By.name("login")).click();
		
		String displayedName = driver.findElement(By.xpath("//span[text() = 'Kia Fatnani']")).getText();
		Assert.assertEquals(displayedName, "Kia Fatnani", "Successfully Logged in");
		
		}
		catch(Exception e)
		{
			
		}
		
	}
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}

}
