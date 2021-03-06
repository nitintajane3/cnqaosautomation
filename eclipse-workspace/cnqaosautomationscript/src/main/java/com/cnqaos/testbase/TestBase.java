package com.cnqaos.testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cnqaos.utils.TestUtils;
import com.cnqaos.utils.WebEventListener;
import com.google.common.base.Function;

public class TestBase 
{
	public static WebDriver driver;
	public static Properties prob;
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static final int invocationcount = 4;
	
	
	
	
	public TestBase() throws IOException
	{
		try {
			prob = new Properties();
			FileInputStream fileinput = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/cnqaos/config/config.properties");	
			prob.load(fileinput);
			
		} catch (FileNotFoundException notfound) 
		{
			notfound.printStackTrace();
		}catch (IOException expetion) 
		{
			expetion.printStackTrace();
			// TODO: handle exception
		}	
		
	}
	
	public void initializebrowser() throws IOException 
	{
		String browsers = prob.getProperty("browser");
		String browserpath = System.getProperty("user.dir");
		
		if(browsers.equals("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver",browserpath + "/src/main/java/drivers/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browsers.equals("firefox")) 
		
		{
		System.setProperty("webdriver.gecko.driver",browserpath + "/src/main/java/drivers/geckodriver.exe");
		driver = new FirefoxDriver();	
		}
		else if (browsers.equals("ie")) 
				
		{
			System.setProperty("webdriver.ie.driver",browserpath + "/src/main/java/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	
		driver.manage().timeouts().pageLoadTimeout(TestUtils.PAGELOAD, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtils.IMPLICIT, TimeUnit.SECONDS);
		
		driver.get(prob.getProperty("url"));	
		
	}
	
	public void widnowsAlertAccept() throws InterruptedException 
	{
		webdriverExplicitWaitForAlert();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	public void widowsAlertDismiss() throws InterruptedException 
	{
		webdriverExplicitWaitForAlert();
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}
	
	public String widowsAlertGetText() throws InterruptedException 
	{	
		webdriverExplicitWaitForAlert();
	
		/*wait.withTimeout(5000, TimeUnit.MILLISECONDS);
		 //Sepcify polling time
		 wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		 //Specify what exceptions to ignore
		 wait.ignoring(NoSuchElementException.class);*/
		 
		 
		 
		 
		//Thread.sleep(6000);
		Alert alert = driver.switchTo().alert();
		String gettextfromalert = alert.getText();
		return gettextfromalert;
	}
	
	public void webdriverExplicitWaitForAlert() 
	{
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void webdriverExplicitWaitElementVisible(WebElement element) 
	{
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeSelected(element));
	}
	
	public void webdriverExplicitWaitElementToBeClickable(WebElement element) 
	{
		WebDriverWait wait = new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	}
