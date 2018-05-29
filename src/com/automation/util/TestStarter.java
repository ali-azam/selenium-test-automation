package com.automation.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

/*
 * this class will help to execute test suite
 */
public class TestStarter {

	private List<String> suites;
	private TestListenerAdapter tla;
	private TestNG testng;
	protected PropertySettings setting;
	
	public TestStarter(PropertySettings setting) {
		this.setting = setting;
	}
	
	/** this will run the whole test suite */
	public void start() {
		testng = new TestNG();
		testng.setVerbose(0);
		try	{
			suites = new ArrayList<String>();
			//tla = new TestListenerAdapter();
			tla = new CustomTestListener(setting);
			
			testng.setOutputDirectory(AppConstant.ASSETS_PATH+setting.getBrowser()+"TestReport/"+setting.getYear()+"/"+setting.getMonth()+"/"+setting.getReportTime()); //yyyy-MM-dd-hh-mm-ss
			suites.add(AppConstant.APP_CONFIG+setting.getTestngxml());
			
			testng.setTestSuites(suites);
			testng.addListener(tla);
			testng.run();
		  }
		catch(Exception ex) {
			//System.out.println(ex.getMessage());
			String errorMsg = "Error reported on: "+new Date()+"\nEither Testsuite '"+testng.getDefaultSuiteName()+"' and TestCases in '"+testng.getDefaultTestName()+"' does not exist.\n";
			 System.out.println(errorMsg);
			 ex.printStackTrace();
			 CreateLogger.enterLogData(AppConstant.ERROR_LOG_PATH,errorMsg);
		}
		this.stop();
	}
	
	/** this will quit driver and stop execution */
	public void stop() {
		if (setting.currentDriver != null) {
			setting.getWaitTime();
			setting.currentDriver.quit();
			setting.setCurrentDriver(null);
		} else {
			if (setting.getState() != 1) {
				System.out.println("**Driver Problem**");
				CreateLogger.enterLogData(AppConstant.ERROR_LOG_PATH,"\n**Driver Problem**"+new Date());
			}
		}
	}
}
