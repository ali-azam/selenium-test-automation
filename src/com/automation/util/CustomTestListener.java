/**
 * 
 */
package com.automation.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * @author se-mac-usa2
 *
 */
public class CustomTestListener extends TestListenerAdapter {
	ExtentReports extReport = null;
	ExtentTest extTest;
	String categoryName = null;
	
	public PropertySettings pSettings;
	
	public CustomTestListener(PropertySettings pSettings) {
		this.pSettings = pSettings;
	}

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext testContext) {
		categoryName = testContext.getName();
		
		if(extReport == null){
	    		extReport = ExtentManager.createInstance(AppConstant.ASSETS_PATH+"extentReport/"+pSettings.getExtentReportPath());
	    		//extReport.setSystemInfo("Device", pSettings.getOsname());
	    		//extReport.setSystemInfo("Browser", pSettings.getBrowser());
	    	}
		
		testContext.setAttribute("setting", pSettings);
		super.onStart(testContext);
		System.out.println("TestCase execute with browser : "+pSettings.getBrowser()+"\n");
	}

	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult result) {
		super.onTestStart(result);
		
		String testName = result.getMethod().getMethodName();		
		extTest = extReport.createTest(testName).assignCategory(categoryName);
		
		SimpleDateFormat d = new SimpleDateFormat("dd-MMM-yy_HH-mm-ss");
		ScreenShot.captureScreen(pSettings.currentDriver, testName + "_before_"+ d.format(new Date()));
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		String testName = tr.getMethod().getMethodName();
		SimpleDateFormat d = new SimpleDateFormat("dd-MMM-yy_HH-mm-ss");
		
		System.out.println("TestCase : "+testName+" is Passed");
		
		if (pSettings.getCurrentDriver() != null ) {
			ScreenShot.captureScreen(pSettings.currentDriver, testName + "_after_success_"+ d.format(new Date()));
		}
			
		extTest.log(Status.PASS, "TestCase : "+testName+" is Passed");
		
		extReport.flush();	
	}
	
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		String testName = tr.getMethod().getMethodName();
		SimpleDateFormat d = new SimpleDateFormat("dd-MMM-yy_HH-mm-ss");
		
		String tmpName = testName + "_after_failure_"+d.format(new Date());
		System.out.println("TestCase : "+testName+" is Failed");
		
		if (pSettings.getCurrentDriver() != null ) {
			pSettings.setScreenImgList(tmpName+".png");
			//ScreenShot.captureScreen(pSettings.currentDriver, testName + "_after_failure_"+ d.format(new Date()));
			ScreenShot.captureScreen(pSettings.currentDriver, tmpName);
		}
		
		String testError = "Failure Reason: <br>"+tr.getThrowable().toString();
		extTest.log(Status.FAIL, ""+testName+ " is Failed");
		extTest.log(Status.ERROR, testError);		
	}
	
	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
		
		String testName = tr.getMethod().getMethodName();
		System.out.println("TestCase : "+testName+" is Skipped"); 
		
		String skipError = tr.getThrowable().toString();
		extTest.log(Status.SKIP, "TestCase : "+testName+" is Skipped");
		extTest.log(Status.SKIP, skipError);		
	}
	
	@Override
	public void onFinish(ITestContext testContext) {		
		extReport.flush();
	}
	
}
