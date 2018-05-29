package com.automation.util;

import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;

/*
 * this class will provide common functionality and properties for test suite
 */
public class TestHelper {
	
	public static WebDriver driver = null;
	protected PropertySettings settings = null;
	protected long rand = 0;
	protected String xpathStr = "//*[@id='login-form']//*[contains(@class,'errorlist')]/li";
	protected String uid = "id_username";
	protected String pwd = "id_password";
	protected String btnSubmit = "submit-button";
	protected String creatorUser = "";
	protected String creatorPass = "";
		
	protected String baseUrl = "";
	protected int waitTime = 6;
	protected int timeoutInSeconds = 60;
	protected JavascriptExecutor jse = null;
	
	@BeforeTest
	public void testBeforeTest(ITestContext pSettings) {
		
		if (settings == null) {
			settings = (PropertySettings)pSettings.getAttribute("setting");
			this.setCredentials();
		}
		
		if (driver == null) {
			driver = settings.getDriver();
			jse = (JavascriptExecutor)driver;
			driver.get(this.baseUrl);
			makeWait(this.waitTime + 4);
		} else {
			driver.get(driver.getCurrentUrl());
			makeWait(this.waitTime - 1);
		}
		
		if (jse == null && driver != null) {
			jse = (JavascriptExecutor)driver;
		}
		
		rand = new Date().getTime();
		
	}
	
//	@BeforeMethod
//	public void setUp(ITestContext pSettings) {
//		refreshBrowser();
//	}
	
	protected void setMainWindow() {
		try{
			int numberOfWindow = driver.getWindowHandles().size();
			boolean tmpIndex = true;
			String mainWindowHandler = driver.getWindowHandle();
			if (numberOfWindow > 1) {
				for(String winHandle : driver.getWindowHandles()){
				    driver.switchTo().window(winHandle);
				    if (tmpIndex) {
				    	mainWindowHandler = winHandle;
				    }
				    tmpIndex = false;
				}
				driver.close();
				driver.switchTo().window(mainWindowHandler);
			}
		} catch (Exception e) {}
	}
	
	/*
	 * this method will clear text element
	 */
	protected void clearTxt(By by){
		driver.findElement(by).clear();
	}
	
	/*
	 * this method will set userName, Password and url
	 */
	protected void setCredentials(){
		this.baseUrl = settings.getBaseUrl();
		
		this.creatorUser = settings.getCreatorUser();
		this.creatorPass = settings.getCreatorPass();
		
		if (settings.getWaitTime() != 0)
			this.waitTime = settings.getWaitTime();
		
	}
	
	/*
	 * make some wait the execution
	 */
	public void makeWait(int waitForSecond)
	{
		try {
			Thread.sleep(1000 * waitForSecond);
		} catch (InterruptedException ie) {
			System.out.println(ie.getMessage());
		}
	}
	
	/*
	 * wait until expected element is visible
	 */
	public void waitForElement(By expectedElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
			makeWait(1);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	/**
	 * wait until expected element is visible
	 *
	 * @param	expectedElement		element to be expected
	 * @param	timeout				Maximum timeout time
	 */
	public void waitForElement(By expectedElement, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(expectedElement));
			makeWait(1);
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}
	
	/*
	 * wait until Invisibility of element is completed
	 */
	protected void waitForInvisibility(By element){
		try{
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			makeWait(1);
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
	}
	
	/**
	 * wait until Invisibility of element is completed
	 *
	 * @param	element		element to be invisible
	 * @param	timeout		Maximum timeout time
	 */
	protected void waitForInvisibility(By element, int timeout){
		try{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
			makeWait(1);
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
	}
	
	protected void quitDriver(){
	  driver.manage().deleteAllCookies();
	  driver.quit();
	  driver = null;
	  settings.setCurrentDriver(null);
	}
	
	/*
	 * The method will help to scroll browser 
	 */
	protected void browserScroll(int x, int y) {
		String jsCode = "window.scrollBy("+x+","+y+")";
		jse.executeScript(jsCode, "");
	}
	
	/*
	 * The method will return same pattern number of files value a folder
	 */
	protected int countFiles(String dirName, String fileName) {
		fileName = fileName.replace(' ', '_');
		File dir = new File(dirName);
		String[] fileNameParts = fileName.split("\\.");
		String regex = "^" + fileNameParts[0] + ".*?\\." + fileNameParts[1] + "$";
		int count = 0;

		File[] fileList = dir.listFiles();
		if (fileList != null) {
			for (File file : fileList) {
				String name = file.getName();
				if (file.isFile() && name.matches(regex)) {
					// System.out.println(name);
					count++;
				}
			}
		}
		return count;
	}
	
	//* Selenium helper method that will be shifted to another class *//
	/**
	 * use this method to enter text into text fields. first clears and then sends keys.
	 *
	 * @param	by		element location
	 * @param	text	text message information
	 */
	public void enterText(By by, String text){
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(text);
		makeWait(1);
	}
	
	/**
	 * returns element text.
	 */
	public String getElementText(By by){
		return driver.findElement(by).getText();
	}
	
	/**
	 * sleep for few seconds after click.
	 */
	public void tryClick(By by, int s){
		driver.findElement(by).click();
		makeWait(s);
	}
	
	/**
	 * wait until expected element is shown after click.
	 *
	 * @param	clickableElement 	element location to be clicked
	 * @param	expectedElement 	element location to be expected
	 * @param   timeout				maximum timeout time in seconds
	 */
	public void tryClick(By clickableElement, By expectedElement){
		driver.findElement(clickableElement).click();
		waitForElement(expectedElement);
	}
	
	/**
	 * this method returns true/false for displaying
	 *
	 * @param	by 	element location
	 * @return     	returns whether the element is visible or not.
	 */
	public boolean elementIsVisible(By by){
		return driver.findElement(by).isDisplayed();
	}
	
	/**
	 * this method returns true/false based on progress bar Visibility
	 *
	 * @param	by 	element location
	 * @return     	returns whether the progress bar is visible or not.
	 */
	public boolean progressBarIsVisible(By by){
		return driver.findElements(by).size() > 0;		
	}
	
	/**
	 * waits up to few seconds to perform action.
	 */
	public void waitFor(double second) {
		try {
			Thread.sleep((int)(1000 * second));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Switch to new window opened
	 */
	protected void switchToNewestWindow(String main, Set<String> winHandles)
	{
	   for(String winHandle : winHandles)
	   {
		   if(!winHandle.equals(main))
		   {
			   driver.switchTo().window(winHandle);  		
		   }       	
	   }
		
	}
	
	protected String getPageSource(String url) {
		driver.get(url);
		makeWait(this.waitTime);
		return driver.getPageSource();
	}
	
	//this method return a fixed length random number specified with the length arggument
	protected long getRandomValueFixedLength(int length){
		Random random = new Random();
		long number = random.nextInt((int)(Math.pow(10, length)));
		
		return number;
	}
	
	//this method is used to refresh the current page
	protected void refreshBrowser(){
		driver.navigate().refresh();
		makeWait(3);
	}
	
	protected void goBack(){
  		//driver.navigate().back();
		jse.executeScript("history.go(-1)", "");
  		makeWait(this.waitTime+2);
  	}
	
	//press delete button
	protected void pressDeleteKey(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.BACK_SPACE).build().perform();
		makeWait(2);
	}
	
	//press delete button
	protected void pressDeleteKey(int waitTime){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.BACK_SPACE).build().perform();
		makeWait(waitTime);
	}
	
	protected void selectToEnd(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.SHIFT,Keys.END)).build().perform();
		makeWait(2);
	}
	
	protected void pressHome(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.HOME).build().perform();
		makeWait(2);
	}
	
	protected void pressEnter(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.ENTER).build().perform();
		makeWait(2);
	}
	
	protected void selectToHome(){
	  Actions actions = new Actions(driver);
	  actions.sendKeys(Keys.chord(Keys.SHIFT,Keys.HOME)).build().perform();
	  makeWait(2);
	 }
	 
	protected void pressBackspaceKey(){
	  Actions actions = new Actions(driver);
	  actions.sendKeys(Keys.BACK_SPACE).build().perform();
	  makeWait(2);
	 }
	
	protected void pressRightArrow(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.RIGHT).build().perform();
	}
	
	protected void pressLeftArrow(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.LEFT).build().perform();
	}
	
	protected void pressDownArrow(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.DOWN).build().perform();
	}
	
	protected void pressUpArrow(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.UP).build().perform();
	}
	
	protected void copy() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.COMMAND, "c")).build().perform();
		makeWait(2);
	}

	protected void paste() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.COMMAND, "v")).build().perform();
		makeWait(2);
	}

	protected void undo(){
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.COMMAND,"z")).build().perform();
		makeWait(2);
	}
	
	protected void redo(){
		 Actions actions = new Actions(driver);
		 actions.sendKeys(Keys.chord(Keys.COMMAND,Keys.SHIFT,"z")).build().perform();
		 makeWait(2);
	}
	
	protected void pushText(String txt) {
		Actions actions = new Actions(driver);
		actions.sendKeys(txt).build().perform();
		makeWait(2);
	}
	
	//perform a shift click on a item
	protected Actions performShiftClick(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).sendKeys(Keys.COMMAND).click().build().perform();
		makeWait(2);
		
		return actions;
	}
	
	protected void performRightClick(By element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(element));
		actions.contextClick(driver.findElement(element)).build().perform(); /* this will perform right click */
		makeWait(3);
	}
	
	protected void selectAll() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.COMMAND, "a")).build().perform();
		makeWait(2);
	}

	protected void cut() {
		Actions actions = new Actions(driver);
		actions.sendKeys(Keys.chord(Keys.COMMAND, "x")).build().perform();
		makeWait(2);
	}
	
}
