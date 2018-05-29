package com.automation.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automation.util.TestHelper;

public class GoogleSearch_Test extends TestHelper  {	
	
	@Test
	public void test_GoogleTitle() {
		boolean isTitleOk = driver.getTitle().contains("Google");
		Assert.assertTrue(isTitleOk);	
	}
	
	@Test
	public void test_GmailLinkAndSignInButton() {
		boolean isGmailLinkVisible = driver.findElement(By.linkText("Gmail")).isDisplayed();
		
		String signinText = driver.findElement(By.id("gb_70")).getText();
		boolean isSigninBtnDisplay = signinText.equals("Sign in");		
		
		Assert.assertTrue(isGmailLinkVisible & isSigninBtnDisplay);
	}
	
	@Test
	public void test_GooglePageImageLink() {
		driver.findElement(By.linkText("Images")).click();
		
		String imagesTxt = driver.findElement(By.cssSelector("#hplogo > div.logo-subtext > span")).getText();
		Assert.assertEquals(imagesTxt,"images");
		makeWait(1);
	}
	
	@Test
	public void test_SearchByHelloWorld() {
		
		//goBack();  Navigate back to previous window
		jse.executeScript("history.go(-1)", "");
		makeWait(3);
		
		driver.findElement(By.id("lst-ib")).clear();
		driver.findElement(By.id("lst-ib")).sendKeys("hello world");
		pressEnter();
		
		WebElement elem = driver.findElement(By.cssSelector("#rso > div:nth-child(3) > div > div:nth-child(4) > div > div > h3 > a"));
		
		String youtubeLinkTxt = elem.getText();
		boolean isHelloWorldTxtinYoutube = youtubeLinkTxt.equals("hello world - YouTube");
		
		String youtubeLink = elem.getAttribute("href");
		boolean isYoutubeLinkMathches = youtubeLink.contains("youtube.com");
		
		Assert.assertTrue(isHelloWorldTxtinYoutube & isYoutubeLinkMathches);
	}
	
}
