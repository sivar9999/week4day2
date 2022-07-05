package week4.Day2;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

//Week4 Day2 Assignment -1
public class Resizable {

	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

		//Scenario 1 - Resizable 
		driver.get("https://jqueryui.com/resizable");

		// Thread.sleep(2000);
		WebElement frameTarget = driver.findElement(By.className("demo-frame"));
		driver.switchTo().frame(frameTarget);
		WebElement eleResize = driver.findElement(By.xpath("//div[contains(@class,'ui-icon-gripsmall-diagonal-se')]"));

		Actions builder = new Actions(driver);

		builder.clickAndHold(eleResize).moveByOffset(100, 80).release().build().perform();
		System.out.println(" Resizable Completed ");
		// driver.close();

		//Scenario 2 - Drag and Drop 
		driver.get("http://www.leafground.com/pages/drag.html");
		WebElement dragMeAround = driver.findElement(By.xpath("//p[text()='Drag me around']"));
		builder.dragAndDropBy(dragMeAround, 40, 40).perform();
		System.out.println(" DragMeAround Completed ");

		//Scenario 3 - Dropable 
		driver.get("http://www.leafground.com/pages/drop.html");
		WebElement eleDrag = driver.findElement(By.xpath("//p[text()='Drag me to my target']"));
		WebElement eleDrop = driver.findElement(By.xpath("//p[text()='Drop here']"));
		// builder.doubleClick(eleDrag).clickAndHold().release(eleDrop).perform();
		builder.dragAndDrop(eleDrag, eleDrop).perform();
		System.out.println(" DropMe Completed ");

		//Scenario 4 - Selectable
		driver.get("http://www.leafground.com/pages/selectable.html");
		List<WebElement> listOfElements = driver.findElements(By.xpath("//div[@id='mydiv']//li"));
		for (WebElement li : listOfElements) {
			builder.keyDown(Keys.CONTROL).click(li).keyUp(Keys.CONTROL).perform();
		}
		System.out.println(" Selectable Completed ");

		/*
		 * for (int i = 1; i < listOfElements.size(); i=i+2) {
		 * builder.keyDown(Keys.CONTROL).click(listOfElements.get(i)).keyUp(Keys.CONTROL
		 * ).perform(); }
		 */

		//Scenario 5 - Sortable
		driver.get("http://www.leafground.com/pages/sortable.html");
		WebElement item1 = driver.findElement(By.xpath("//li[text()='Item 1']"));
		WebElement item3 = driver.findElement(By.xpath("//li[text()='Item 3']"));
		WebElement item6 = driver.findElement(By.xpath("//li[text()='Item 6']"));
		WebElement item7 = driver.findElement(By.xpath("//li[text()='Item 7']"));

		builder.dragAndDrop(item7, item1).perform();
		Thread.sleep(3000);
		builder.dragAndDrop(item6, item3).perform();
		Thread.sleep(3000);
		System.out.println(" Sortable Completed ");

		/*
		 * List<WebElement> eleToSort =
		 * driver.findElements(By.xpath("//ul[@id='sortable']/li")); int length =
		 * eleToSort.size(); System.out.println(length); for (int j = length-1; j>=0;
		 * j--) { Thread.sleep(2000); builder.clickAndHold(eleToSort.get(j))
		 * .moveToElement(eleToSort.get(0)). release().perform(); }
		 */
		driver.quit();
	}
}
