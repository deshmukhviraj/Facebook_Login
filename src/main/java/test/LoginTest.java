package test;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {

	public static WebDriver driver;
	public static Properties prop;

	public static void main(String[] args) {

		ConfigFile();
		initBrowser();
		FacebookLogin();
		FacebookPost();
		FacebookLogout();
		CloseBrowser();
	}

	public static void ConfigFile() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\config\\config.properties");
			prop.load(fis);
			System.out.println("Config file intialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initBrowser() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\main\\java\\resources\\chromedriver.exe");
		driver = new ChromeDriver(options);
		System.out.println("Driver initialized");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		String URL = prop.getProperty("ApplicationURL");
		driver.get(URL);
		System.out.println("Navigated to URL");
	}

	private static void FacebookLogin() {

		WebElement username = driver.findElement(By.id("email"));
		username.clear();
		username.sendKeys(prop.getProperty("username"));

		WebElement password = driver.findElement(By.id("pass"));
		password.clear();
		password.sendKeys(prop.getProperty("password"));

		WebElement login = driver.findElement(By.name("login"));
		login.click();
		
		System.out.println("Logged in to Facebook");
		}

	private static void FacebookPost() {		
		
		WebElement create = driver.findElement(By.xpath("//div[@aria-label='Create']/i"));
		create.click();

		WebElement openPost = driver.findElement(By.xpath("//span[text() = 'Post']"));
		openPost.click();

		System.out.println("New Post added");

		WebElement closePost = driver.findElement(By.xpath("//div[@aria-label='Close']/i"));
		closePost.click();
	}

	private static void FacebookLogout() {
		WebElement dropdown = driver.findElement(By.xpath("//div[@aria-label='Account']/i"));
		dropdown.click();

		WebElement logout = driver.findElement(By.xpath("//span[text() = 'Log Out']"));
		logout.click();
		
		System.out.println("Logged out from facebook successfully");
	}

	private static void CloseBrowser() {
		//driver.close();
		System.out.println("Browser Closed");

	}
}
