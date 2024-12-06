package testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.Duration;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class LoginFunctionalityWithDataProvider {

	WebDriver driver;
	String userName;
	String password;
	ExtentReports extent;
	ExtentSparkReporter spark;
	ExtentTest test;
	File desFile;

	@BeforeClass
	public void setup() {
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		driver = new ChromeDriver(option);
		System.setProperty("webdriver.chrome.driver", "./Driver.chromedriver.exe");
		driver.manage().window().maximize();
		extent = new ExtentReports();
		String srcPath = Paths.get(System.getProperty("user.dir") + "./Reports/Login-report-with-exceldata").toString();
		spark = new ExtentSparkReporter(srcPath);
		extent.attachReporter(spark);

		try {
			test = extent.createTest("Login with taking test data from excel file");
			test.info("Defining the test data file location");
			// Define the file location
			File src = new File("./TestData/TestData.xlsx");
			test.info("Loading the file data");
			// Load the file
			FileInputStream fis = new FileInputStream(src);
			test.info("Loading workbook");
			// Load workbook
			XSSFWorkbook wk = new XSSFWorkbook(fis);
			test.info("Loading the sheet");
			// load sheet
			XSSFSheet sh = wk.getSheet("UserLoginTestData");
			System.out.println(sh.getSheetName());
			test.info("Reading data from cell and storing into variable");

			userName = sh.getRow(1).getCell(0).getStringCellValue();
			password = sh.getRow(1).getCell(1).getStringCellValue();
			wk.close();

		} catch (Exception e) {
			test.fail("Unable to read data from test file due to exception " + e.getMessage());
		}

	}

	@Test
	public void validatingLoginWithDifferentUserData() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		test.info("Navigating to url");
		driver.get("https://www.facebook.com/");
		test.info("Entering username from excel file");
		driver.findElement(By.id("email")).sendKeys(userName);
		test.info("Entering password from excel file");
		driver.findElement(By.id("pass")).sendKeys(password);
		test.info("Clicking on login button");
		driver.findElement(By.name("login")).click();
		test.pass("Successfully logged in");
		try {
			test.info("Taking the screenshot");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text() = 'Kia Fatnani']")));
			TakesScreenshot screenshot = (TakesScreenshot) driver;
			File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
			desFile = new File("./Screenshots/loginwithexceldata.png");
			FileUtils.copyFile(srcFile, desFile);
			test.addScreenCaptureFromPath(desFile.getAbsolutePath());

		} catch (Exception e) {
			test.fail("Unable to capture screenshot due to exception" + e.getMessage());
		}

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		extent.flush();
	}

}
