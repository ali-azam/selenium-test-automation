package com.automation.util;

import java.io.File;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * this class is responsible to take screenshot
 * */
public class ScreenShot {
	
	private static String screenshotsFolder = AppConstant.SCREEN_SHOT_DIR;//".//assets//screenshots//"; 
	private static String imageExtention = ".png";
	
	/*
	 * this method will take the driver screenshot
	 */
	public static void captureScreen(WebDriver driver, String screenshotFileName)
	{
		String screenshotsFile = screenshotsFolder+screenshotFileName+imageExtention;
		
		File screenShot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//FileUtils.copyFile(screenShot, new File(screenshotsFile));		
	}
}
