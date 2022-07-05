package week4.Day2;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

//Week4 Day2 Assignment - 3
public class SnapDeal {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		
		//1. Launch URL
		driver.get("https://www.snapdeal.com/");

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		//2. Go to Mens Fashion
		WebElement eleOfMenFashion = driver.findElement(By.xpath("//span[text()=\"Men's Fashion\"]"));
		
		//3. Go to Sports Shoes
		Actions builder = new Actions(driver);
		builder.moveToElement(eleOfMenFashion).perform();
		Thread.sleep(2000);
		WebElement shoes = driver.findElement(By.xpath("(//span[text()='Sports Shoes'])[1]"));
		builder.click(shoes).perform();
		
		//4. Get the count of the sports shoes
		String countOfSportShoes = driver.findElement(By.xpath("//div[@class='sub-cat-name ']//following-sibling::div"))
				.getText();
		System.out.println("The Count of the Sports Shoes : " + countOfSportShoes);
		
		//5. Click Training shoes
		driver.findElement(By.xpath("//div[text()='Training Shoes']")).click();

		//6. Sort by Low to High
		driver.findElement(By.xpath("//span[text()='Sort by:']")).click();

		//7. Check if the items displayed are sorted correctly
		driver.findElement(By.xpath("//li[text()[normalize-space()='Price Low To High']]")).click();
		Thread.sleep(3000);
		
		List<WebElement> elePriceDetails = driver.findElements(By.xpath("//span[@class='lfloat product-price']"));
		List<String> productPriceList = new ArrayList<String>();

		for (WebElement elePriceDetail : elePriceDetails) {
			Thread.sleep(3000);
			String editList = elePriceDetail.getText().replaceAll("[^0-9]", "");
			productPriceList.add(editList);
			}
		System.out.println("Original Price list : " + productPriceList );
		
		String[] arr = productPriceList.toArray(new String[productPriceList.size()]);	
	
		List<String> tempSortedList = new ArrayList<String>();
		tempSortedList.addAll(Arrays.asList(arr));
		System.out.println("Temporary Price list : " + tempSortedList );

		if (productPriceList.equals(tempSortedList))
			System.out.println("The items displayed are sorted correctly");
		else
			System.out.println("The items displayed are not sorted");

		//8.Select the price range (900-1200)
		WebElement fromRange = driver.findElement(By.name("fromVal"));
		fromRange.clear();
		fromRange.sendKeys("900");
		WebElement toRange = driver.findElement(By.name("toVal"));
		toRange.clear();
		toRange.sendKeys("1200");
		driver.findElement(By.xpath("//div[text()[normalize-space()='GO']]")).click();
		Thread.sleep(3000);
		
		//9.Filter with color Navy 
		driver.findElement(By.xpath("//label[@for='Color_s-Blue']")).click();

		WebElement priceFilter = driver
				.findElement(By.xpath("//div[@class='filters-top-selected']//a[text()='Rs. 900 - Rs. 1200']"));
		String priceFilterText = priceFilter.getText();

		WebElement colorFilter = driver.findElement(By.xpath("//div[@class='filters-top-selected']//a[text()='Blue']"));
		String colorFilterText = colorFilter.getText();
		
		//10 verify the all applied filters 
		if ((priceFilterText.contains("Rs. 900 - Rs. 1200")) && (colorFilterText.contains("Blue")))
			System.out.println("All Requested Filters are Applied");
		else
			System.out.println("All Requested Filters are Not Applied");

		//11. Mouse Hover on first resulting Training shoes
		WebElement firstProduct = driver.findElement(By.xpath("//img[@title='Force 10 By Liberty Blue Sports Shoes']"));
		
		//12. click QuickView button
		WebElement quickView = driver.findElement(By.xpath("//div[text()[normalize-space()='Quick View']]"));
		builder.moveToElement(firstProduct).click(quickView).perform();

		//13. Print the cost and the discount percentage
		Thread.sleep(1000);
		String cost = driver.findElement(By.xpath("//span[@class='payBlkBig']")).getText();
		System.out.println("The Cost of Product : " + cost);
		String percentage = driver.findElement(By.xpath("//span[@class='percent-desc ']")).getText();
		System.out.println("The Percentage of product : " + percentage);
		
		//14. Take the snapshot of the shoes.
		File source = driver.getScreenshotAs(OutputType.FILE);
		File destination = new File("./TrainingShoes.png");
		FileUtils.copyFile(source, destination);

		driver.close();
		driver.quit();
	}

}
