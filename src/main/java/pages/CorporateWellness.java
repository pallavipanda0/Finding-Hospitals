package pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import base.Base;

public class CorporateWellness extends Base {
	WebElement Name, OrgName, Email, Contact;

	By providers = By.xpath("//span[normalize-space()='Wellness Plans']");
	By name = By.xpath("//*[@id='name']");
	By orgName = By.xpath("//*[@id='organizationName']");
	By email = By.xpath("//header[@id='header']//input[@id='officialEmailId']");
	By contact = By.xpath("//header[@id='header']//input[@id='contactNumber']");
	By button = By
			.xpath("//header[@class='c-header-mobile u-position--relative dweb-hide']//button[@type='submit'][normalize-space()='Schedule a demo']");
	By organizationSize = By.xpath("//*[@id='organizationSize']");
	By dropDown = By.xpath("//option[text()='<500']");
    By InterestedIn = By.id("interestedIn");
    By dropdown2 = By.xpath("//option[text()='Taking a demo']");
	@SuppressWarnings("resource")
	public void formFill() throws InterruptedException, IOException {

		logger = report.createTest("Corporate Wellness");

		// Initializing the Excel Sheet
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		XSSFSheet sheet = workbook.getSheet("Data");

		// Selecting the corporate option
		try {
			findElement(providers).click();
			// findElement(corporate).click();
			reportPass("Corporate Wellness Link Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// Switching to new tab
		try {
			Set<String> currentHandle = driver.getWindowHandles();
			Iterator<String> iterator = currentHandle.iterator();
			iterator.next();
			String corporate = iterator.next();
			driver.switchTo().window(corporate);
			reportPass("Switched to new tab Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		// Filling the form
		try {
			Name = driver.findElement(name);
			OrgName = driver.findElement(orgName);
			Email = driver.findElement(email);
			Contact = driver.findElement(contact);

			Name.sendKeys(sheet.getRow(3).getCell(0).getStringCellValue());
			Thread.sleep(3000);
			OrgName.sendKeys(sheet.getRow(3).getCell(1).getStringCellValue());
			Thread.sleep(3000);
			Contact.sendKeys("" + (long) sheet.getRow(3).getCell(3).getNumericCellValue());
			Thread.sleep(3000);
			Email.sendKeys(sheet.getRow(3).getCell(2).getStringCellValue());
			Thread.sleep(3000);

			driver.findElement(organizationSize).click();
			Thread.sleep(3000);
			driver.findElement(dropDown).click();
			Thread.sleep(3000);
			driver.findElement(InterestedIn).click();
			Thread.sleep(3000);
			driver.findElement(dropdown2).click();
			Thread.sleep(3000);
			reportPass("Data entered successfully");
			
			Screenshot("dataEntered");
			if(driver.findElement(button).isEnabled())
			{
				System.out.println("***************************");
				System.out.println("Valid Details");
				driver.findElement(button).click();
		     }
			else {
				System.out.println("***************************");
				System.out.println("Invalid Details ");
				}
		//	wait.until(ExpectedConditions.urlToBe("https://www.practo.com/plus/corporate?submitted=true"));
			Screenshot("dataSubmitted");
			reportPass("Data submitted successfully");

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
