//Wessam Samer Lahloub
//wessam.lahloub1@gmail.com
//962782379185
//1/15/2022
//Bayt's Technical Assessment

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class QA_Assignment_P1 {

	public WebDriver driver;

	@BeforeTest

	public void Initialize_and_login() throws IOException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

	}

	@Test(description = "Register & apply for a job", enabled = true) // Tested on 20 times and all passed
	public void PART1() throws InterruptedException, IOException {

		// Open the Bayt.com Website
		driver.get("https://www.bayt.com/en/jordan/");
		driver.manage().window().maximize();
		TakesScreenshot(driver); // Method Declaration is after all the Tests

		// Scroll down to the page footer and click on the “About Us” link
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)"); // Scrolling method, to scroll to the footer
		driver.findElement(By.xpath("//*[@id=\"yw3\"]/li[1]/a")).click(); // About Us
		TakesScreenshot(driver);

		// Click on one of the jobs appearing under the “Apply to Bayt.com” section
		driver.findElement(By.xpath("/html/body/section/div[2]/div/div[1]/div[3]/div/div[1]/h5/a")).click(); // Apply
		driver.navigate().to("https://www.bayt.com/en/pakistan/jobs/software-engineer-4617616/");
		TakesScreenshot(driver);

		String originalWindow = driver.getWindowHandle(); // To prevent the driver from switching to other tab
		driver.switchTo().window(originalWindow);

		// Apply for the job, then fill out the registration form
		Thread.sleep(1000); // Easy apply button takes some time to load
		driver.findElement(By.xpath("//*[@id=\"applyLink_1\"]")).click(); // Easy Apply
		driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_firstName\"]")).sendKeys("Wessam"); // FirstName
		driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_lastName\"]")).sendKeys("Lahloub"); // LastName

		// Check Validation Message
		driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_email\"]")).sendKeys("wessam.lahloub1@gmai.com");
		String valMSG = driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_email_em_\"]")).getText();
		if (valMSG.contains("already registered")) {
			System.out.println("Validation Message Tested Succesfully");

			// dynamicEmail
			driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_email\"]")).clear();
			String dynaEmail = "wessam.lahloub1" + System.currentTimeMillis() + "@gmail.com"; // to generate a random
																								// dynamic emails based
																								// on the current time
																								// zone
			driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_email\"]")).sendKeys(dynaEmail);
		} else {
			System.err.println("Validation Message didn't work, an already registered email got accepted");
		}
		// fill the Mobile number
		driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_password\"]")).sendKeys("Wessam1999"); // password
		// driver.findElement(By.xpath("//*[@id=\"mobPhoneAreaCode__r\"]")).click();
		// driver.findElement(By.xpath("/html/body/div[6]/div[1]/div[2]/div/input")).sendKeys("jordan"+Keys.ENTER);
		// driver.findElement(By.xpath("//*[@id=\"JsApplicantRegisterForm_mobPhone\"]")).sendKeys("0782379185");
		TakesScreenshot(driver);
//This code won't confirm the Email Validation message, and couldn't access the preferred job section due to the reCAPTCHA method, 
	}

	@Test(description = "login into Bayt.com & delete the account", enabled = true)
	public void PART2() throws IOException, InterruptedException {
		driver.get("https://www.bayt.com/en/login/");
		driver.manage().window().maximize();

		// Log In with the account that you registered in the first test case, from the
		// Log In page
		// Since i got blocked while performing the first test due to the reCAPTCHA,
		// i'll not log in with the same account

		driver.findElement(By.xpath("//*[@id=\"LoginForm_username\"]")).sendKeys("email4testbaytonly@gmail.com"); // Email
		driver.findElement(By.xpath("//*[@id=\"LoginForm_password\"]")).sendKeys("wessam1999"); // Pass
		TakesScreenshot(driver);
		driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

		Thread.sleep(1000);

		// Verify that you logged in successfully

		String welcomeMessage = driver.findElement(By.xpath("//*[@id=\"yw0\"]/section[1]/p")).getText();

		if (welcomeMessage.contains("Tell us about yourself")) {
			System.out.println("You Are Now Signed In");
		}

		else {
			System.err.println("Something is Wrong");
		}
		TakesScreenshot(driver);

		driver.findElement(By.cssSelector("#yw1 > li.is-first > ul > li:nth-child(7) > a")).click(); // three dots
		driver.findElement(
				By.cssSelector("#yw1 > li.is-first > ul > li.popover-owner.is-active > div > ul > li:nth-child(1) > a"))
				.click(); // Account settings
		TakesScreenshot(driver);
		driver.findElement(By.xpath("/html/body/div[4]/section/div/div[13]/div[2]/p/a")).click(); // delete my account
		TakesScreenshot(driver);
		driver.findElement(By.xpath("/html/body/div[3]/section/div/div[2]/ul/li[1]/button")).click();
		 driver.findElement(By.xpath("/html/body/div[4]/div/div/div[3]/div/button[2]")).click();
		// //***DELETES THE ACCOUNT AND YOU HAVE TO CREATE A NEW ONE***

		TakesScreenshot(driver);

	}

	@Test(description = "Searching for QA job in mobile-sized device", enabled = true)
	public void PART3() throws InterruptedException, IOException {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		// Open the Bayt.com home page & resize the window to mobile screen size
		driver.get("https://www.bayt.com/en/jordan/");
		Dimension dim = new Dimension(500, 900);
		driver.manage().window().setSize(dim);

		// Search for “Quality Assurance Engineer” in the United Arab Emirates
		driver.findElement(By.id("keyword")).sendKeys("Quality Assurance Engineer");
		driver.findElement(By.id("lmy")).sendKeys("United Arab Emirates");
		TakesScreenshot(driver);
		driver.findElement(By.id("searchSubmit")).click();

		// apply for the first job in the search results
		driver.findElement(By.cssSelector("a.job-title-link")).click();
		TakesScreenshot(driver);

		driver.findElement(By.id("apply-now-button")).click();
		TakesScreenshot(driver);

		// verify that the Applicant registration page appears
		String currentUrl = driver.getCurrentUrl();
		assert currentUrl.contains("/applicant-registration");
		TakesScreenshot(driver);

	}

	private void TakesScreenshot(WebDriver driver) throws IOException {
		Date currentDate = new Date();
		String enhancedDate = currentDate.toString().replace(":", "_"); // Windows don't accept ":" symbol in saving the
																		// files
		TakesScreenshot src = ((TakesScreenshot) driver); // Automated ScreenShot Identification
		File SrcFile = src.getScreenshotAs(OutputType.FILE);
		File Dest = new File(".//myScreenShots/" + enhancedDate + ".png"); // To Create a new file named "myScreenShots"
																			// on the same project, refresh the file if
																			// the screen shots didn't appear
		FileUtils.copyFile(SrcFile, Dest);

	}

}
