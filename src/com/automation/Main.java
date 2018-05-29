package com.automation;

import java.util.Map;

import com.automation.util.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Prepare Setting");
		PropertySettings setting = new PropertySettings();
		String extentReportPath = setting.getReportTime()+"_";
		extentReportPath += "ExtentReport.html";
		setting.setExtentReportPath(extentReportPath);
		
		// run test for all browser one by one
		Map<Integer, String> browsers = setting.getBrowserList();
		for (int i = 0; i < browsers.size(); i++) {
			if (!browsers.get(i).equals("")) {
				setting.setBrowser(browsers.get(i));
				setting.setDriver();
				TestStarter testStarter = new TestStarter(setting);
				testStarter.start();
			}
		}
		
		GenerateReport generateReport = new GenerateReport(setting);
		generateReport.designExtendReport();	//will design Extent Report Dashboard
		
		//SendMail.sendmail(setting);    /* send the emailable-report through mail */
		SendMail.sendmail(setting, "Hello, this is for testing purpose only...", "");  /* send extentreports through mail */
	}

}
