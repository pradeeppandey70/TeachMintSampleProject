import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import dev.failsafe.internal.util.Assert;

public class Assignment {

	public static void main(String[] args) throws InterruptedException {
		
		WebDriver driver = new ChromeDriver();
		//driver.get("https://accounts.teachmint.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://accounts.teachmint.com/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		WebElement userName = driver.findElement(By.xpath("//input[@id='user-input']"));
		WebElement nextBtn = driver.findElement(By.cssSelector("input#send-otp-btn-id.get-otp-btn.mb-12"));
		
		userName.sendKeys("0000020232");
		//wait.until(ExpectedConditions.visibilityOf(nextBtn));
		Thread.sleep(2000);
		nextBtn.click();
		
		//driver.switchTo().frame(0);
		WebElement enterOtp = driver.findElement(By.xpath("//input[@data-group-idx ='0']"));
		
		WebElement submitOtp = driver.findElement(By.cssSelector("button#submit-otp-btn-id"));
		wait.until(ExpectedConditions.visibilityOf(enterOtp));
		enterOtp.sendKeys(String.valueOf(120992));
		submitOtp.click();
		
		List<WebElement> profiles = driver.findElements(By.xpath("//div[@class='profile-user-name']"));
		for(WebElement profile : profiles) {
			if(profile.getText().equalsIgnoreCase("@Automation-2")) {
				profile.click();
				break;
			}
		}
		
		List<WebElement> sideMenuOptions = driver.findElements(By.xpath("//div[contains(@class,'Sidebar_sidebarItem__3T049')]"));
		wait.until(ExpectedConditions.visibilityOfAllElements(sideMenuOptions));
		for(WebElement sideMenuOption : sideMenuOptions) {
			if(sideMenuOption.getText().equalsIgnoreCase("Administration")) {
				sideMenuOption.click();
				List<WebElement> sideMenuSubOptions = driver.findElements(By.xpath("//a[@class='Sidebar_sidebarLink__39rXm']"));
				for(WebElement sideMenuLink : sideMenuSubOptions) {
					if(sideMenuLink.getText().equalsIgnoreCase("Certificates")) {
						sideMenuLink.click();
						break;
					}
				}
				break;
			}
		}
		
		List<WebElement> certificateTypes = driver.findElements(By.xpath("//div[@class='Cards_cardDetails__WsZ-E']"));
		for(WebElement certificateType : certificateTypes) {
			if(certificateType.getText().equalsIgnoreCase("School leaving certificate")) {
				certificateType.click();
				break;
			}
		}
		
		WebElement generateButton = driver.findElement(By.xpath("//div[text()='Generate']/parent::button"));
		generateButton.click();
		
		WebElement studentSearchBox = driver.findElement(By.xpath("//input[@name='search']"));
		studentSearchBox.sendKeys("Sam");
		
		List<WebElement> tableRow = driver.findElements(By.xpath("//tr"));
		for(int i=1;i<tableRow.size();i++) {
			List<WebElement> tableData = driver.findElements(By.xpath("//td"));
			wait.until(ExpectedConditions.visibilityOfAllElements(tableData));
			tableData.get(1).click();
			tableData.get(4).click();
			break;
		}
		
		WebElement slcRemarkField = driver.findElement(By.xpath("//input[@placeholder='Remarks']"));
		//wait.until(ExpectedConditions.visibilityOf(slcRemarkField));
		Thread.sleep(500);
		slcRemarkField.sendKeys("This is a test Remark");
		Thread.sleep(2000);
		
		WebElement generateCertificateBtn = driver.findElement(By.xpath("//div[text()='Generate']/parent::button"));
		wait.until(ExpectedConditions.elementToBeClickable(generateCertificateBtn));
		generateCertificateBtn.click();
		
		WebElement downloadSlcButton = driver.findElement(By.xpath("//button[@id='download']"));
		wait.until(ExpectedConditions.visibilityOf(downloadSlcButton));
		downloadSlcButton.click();
		
		ArrayList tabs = new ArrayList (driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(0));
		
		WebElement certificatePageLink = driver.findElement(By.xpath("//a[text()='Certificates and Documents']"));
		certificatePageLink.click();
		
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		WebElement record = driver.findElement(By.xpath("//h6[contains(text(),'Recently generated certificates')]"));
		act.scrollToElement(record);
		WebElement viewAllButton = driver.findElement(By.xpath("(//span[text()='View All'])[2]"));
		viewAllButton.click();
		List<WebElement> recentCertificateTableRow = driver.findElements(By.xpath("//tr"));
		List<WebElement> recentCertificateTableFields = driver.findElements(By.xpath("//td"));
		for(int i=0;i<recentCertificateTableRow.size();i++) {
			Assert.isTrue((recentCertificateTableFields.get(0).getText().contains("Sam")),"Student name not Matching");
			Assert.isTrue((recentCertificateTableFields.get(0).getText().contains("91-0034256785")),"Student ID not Matching");
			Assert.isTrue((recentCertificateTableFields.get(1).getText().contains("12-A")),"Student Class not Matching");
			Assert.isTrue((recentCertificateTableFields.get(2).getText().contains("School leaving certificate")),"Students certificate type not Matching");
			break;
			
		}
		
		
		
		
	}

}
