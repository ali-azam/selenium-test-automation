package com.automation.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    
    private static ExtentReports extent;
    
    public static ExtentReports getInstance() {
    	if (extent == null)
    		createInstance("test-output/ExtentReport.html");
    	
        return extent;
    }
    
    public static ExtentReports createInstance(String reportFileName) { 	
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFileName);
        htmlReporter.setAppendExisting(true); 
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setTheme(Theme.STANDARD);        
        htmlReporter.config().setDocumentTitle("ExtentReports");        
        htmlReporter.config().setReportName("Automation Report");
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        
        return extent;
        
    }
}
