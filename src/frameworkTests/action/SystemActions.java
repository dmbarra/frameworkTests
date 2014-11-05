package frameworkTests.action;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import frameworkTests.utils.DriverManager;

public class SystemActions {

	/**
	 * waiting status element 
	 */
	public static void waitingObject(WebElement object) {
		boolean regex = object.isEnabled();
		while(!regex){
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriverInstance(), 10);
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(object));
			regex = element.isEnabled();
		}
	}

	/**
	 * waiting Browser
	 */
	public static void waitingBrowserReady(){
		Wait<WebDriver> wait = new WebDriverWait(DriverManager.getDriverInstance(), 30);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
			}
		});
	}

	/**
	 * input value at element 
	 */	
	public static void inputKeysObject(WebElement object,String value) {
		object.clear();
		object.sendKeys(value);
	}

	/**
	 * click at element 
	 */	
	public static void clickObject(WebElement object) {
		object.click();
	}

	/**
	 * get text at element 
	 */	
	public static String getValueObject(WebElement object){
		return object.getText().toString();
	}

	/**
	 * select value at element comboBox 
	 */	
	public static void selectValueObject(WebElement object,String value) {
		Select select=new Select(object);
		select.selectByValue(value);
	}

	/**
	 * select position at element comboBox 
	 */	
	public static void selectPositionObject(WebElement object,int position) {
		Select select=new Select(object);
		select.selectByIndex(position);
	}

	/**
	 * click at position element table of results 
	 */	
	public static void clickResultObject(WebElement object,int position) {

	}

	/**
	 * Navigate Menu 
	 */	
	public static void navigateHoverClick(WebElement menu,WebElement object) {
		Actions builder = new Actions(DriverManager.getDriverInstance()); 
		Actions hoverOverRegistrar = builder.moveToElement(menu);
		hoverOverRegistrar.perform();
		object.click();
	}


	/**
	 * select value at element radioBox 
	 */	
	public static void clickOptionObject(List<WebElement> object,int position) {
		if(object!=null){
			object.get(position).click();
		}
	}
}
