package week4.Day2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
//Week4 Day2 Assignment - 4 
public class WebTables {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("http://www.leafground.com/pages/table.html");

		// Get the count of number of columns
		int noOfCol = driver.findElements(By.xpath("//tbody//tr//th")).size();
		System.out.println("Number of Column : " + noOfCol);

		// Get the count of number of rows
		int noOfRow = driver.findElements(By.xpath("//tbody//tr")).size();
		System.out.println("Number of Row : " + noOfRow);

		// Get the progress value of 'Learn to interact with Elements'
		List<WebElement> eleLearnToInteracts = driver.findElements(
				By.xpath("//table//tr/td[normalize-space()='Learn to interact with Elements']/following::td[1]"));

		for (WebElement eleLearnToInteract : eleLearnToInteracts) {
			String pValueToInteract = eleLearnToInteract.getText();
			System.out.println("The progress value of 'Learn to interact with Elements : " + pValueToInteract);
		}

		// Check the vital task for the least completed progress.
		List<WebElement> listOfLeastValue = driver.findElements(By.xpath("//table//tr//td[2]"));
		List<Integer> progressValue = new ArrayList<Integer>();

		for (WebElement textOfValue : listOfLeastValue) {
			String text = textOfValue.getText().replaceAll("[^0-9]", "");
			progressValue.add(Integer.parseInt(text));

		}
		Collections.sort(progressValue);
		int leastValue = Collections.min(progressValue);
		System.out.println("The Least Progress value list : " + progressValue);

		String s = driver.findElement(By.xpath("//table//tr//td[normalize-space()='" + leastValue + "%" + "']"))
				.getText();
		System.out.println(s);
		WebElement vitalTask = driver.findElement(
				By.xpath("//table//tr//td[normalize-space()='" + leastValue + "%" + "']/following::input"));
		vitalTask.click();
		if (vitalTask.isSelected() == true)
			System.out.println("The Vital Task is Checked");
		else
			System.out.println("The Vital Task is not Checked");
		driver.close();
	}

}
