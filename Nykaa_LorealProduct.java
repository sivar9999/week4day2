package week4.Day2;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.time.Duration;

import io.github.bonigarcia.wdm.WebDriverManager;

//Week4 Day2 Assignment - 2
public class Nykaa_LorealProduct {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		// 1) Go to https://www.nykaa.com/
		driver.get("https://www.nykaa.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// 2) Mouseover on Brands and Search L'Oreal Paris
		Actions builder = new Actions(driver);
		WebElement eleBrands = driver.findElement(By.xpath("//a[text()='brands']"));
		builder.moveToElement(eleBrands).perform();

		// 3) Click L'Oreal Paris
		driver.findElement(By.xpath("//input[@id='brandSearchBox']")).sendKeys("L'Oreal Paris");

		List<WebElement> listOfBrand = driver.findElements(By.xpath("//div[@class='css-ov2o3v']/a"));

		for (WebElement li : listOfBrand) {
			if (li.getText().equals("L'Oreal Paris")) {

				builder.moveToElement(li).click().perform();
				// System.out.println("The Given("+li.getText()+") product is selected");
				break;
			}
			else
				System.out.println("failed");
		}
		// 4) Check the title contains L'Oreal Paris(Hint-GetTitle)
		System.out.println("The title of the page : " + driver.getTitle());

		// 5) Click sort By and select customer top rated
		driver.findElement(By.xpath("//span[text()='Sort By : popularity']")).click();
		driver.findElement(By.xpath("//label[@for='radio_customer top rated_undefined']//following-sibling::div"))
				.click();
		// 6) Click Category and click Hair->Click haircare->Shampoo
		driver.findElement(By.xpath("//span[text()='Category']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Hair']")).click();
		driver.findElement(By.xpath("//span[text()='Hair Care']")).click();
		driver.findElement(By.xpath("//label[contains(@for,'Shampoo')]//div[2]")).click();

		// 7) Click->Concern->Color Protection
		driver.findElement(By.xpath("//span[text()='Concern']")).click();
		// driver.findElement(By.xpath("//span[text()='Color Protection']")).click();
		driver.findElement(By.xpath("//label[contains(@for,'checkbox_Color Protection')]//div[2]")).click();

		// 8)check whether the Filter is applied with Shampoo
		List<WebElement> eleFilterValues = driver.findElements(By.xpath("//span[@class='filter-value']"));
		for (WebElement filterValue : eleFilterValues) {
			if (filterValue.getText().contains("Shampoo")) {
				System.out.println("The Filter is applied with : " + filterValue.getText());
				break;
			} else
				System.out.println("The Filter is not apllied");
		}

		// 9) Click on L'Oreal Paris Colour Protect Shampoo
		Thread.sleep(3000);
		WebElement shampoo = driver.findElement(By.xpath("//div[text()=\"L'Oreal Paris Colour Protect Shampoo\"]"));
		shampoo.click();

		// 10) GO to the new window and select size as 175ml
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowList = new ArrayList<String>(windowHandles);

		driver.switchTo().window(windowList.get(1));
		System.out.println("The title of the page : " + driver.getTitle());

		WebElement sel = driver.findElement(By.xpath("//select[@title='SIZE']"));
		Select op = new Select(sel);
		String defaultOption = op.getFirstSelectedOption().getText();

		if (defaultOption.equals("175ml"))
			System.out.println("deault");
		else {
			op.selectByVisibleText("175ml");
			System.out.println("selcted by user : " + op.getFirstSelectedOption().getText());
		}
		
		// 11) Print the MRP of the product
		String mrpOfProduct = driver.findElement(By.xpath("(//span[text()='MRP:']//following-sibling::span)[1]"))
				.getText();
		System.out.println("The MRP of Product is : " + mrpOfProduct);

		// 12) Click on ADD to BAG
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement waitToAddCart = driver
				.findElement(By.xpath("(//button[@type='button']//span[text()='Add to Bag'])[1]"));

		wait.until(ExpectedConditions.visibilityOf(waitToAddCart));
		waitToAddCart.click();

		// 13) Go to Shopping Bag
		driver.findElement(By.xpath("//div[@class='css-0 e1ewpqpu1']//button[@type='button']")).click();

		// 14) Print the Grand Total amount
		WebElement insideFrame = driver.findElement(By.className("css-acpm4k"));
		Thread.sleep(2000);
		driver.switchTo().frame(insideFrame);
		Thread.sleep(3000);

		String grandTotal = driver.findElement(By.xpath("//div[@class='value medium-strong']")).getText()
				.replaceAll("[^0-9]", "");
		System.out.println("Grand Total Amount of Product : " + grandTotal);

		// 15) Click Proceed
		driver.findElement(By.xpath("//span[text()='Proceed']")).click();

		// 16) Click on Continue as Guest
		driver.findElement(By.xpath("//button[text()='CONTINUE AS GUEST']")).click();

		// 17) Check if this grand total is the same in step 14
		String finalGrandTotal = driver.findElement(By.xpath("(//div[text()='Grand Total']/following::span)[1]"))
				.getText();
		System.out.println("Final Grand Total Amount of Product : " + finalGrandTotal);

		if (grandTotal.equals(finalGrandTotal))

			System.out.println("The Grand Total is Matched");
		else
			System.out.println("The Grand Total is Not Matched");
		// 18) Close all windows
		driver.quit();
	}
}
