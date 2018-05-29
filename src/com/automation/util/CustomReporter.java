package com.automation.util;

import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomReporter implements ISuiteListener, ITestListener, IReporter {
	
	protected PropertySettings pSettings = null;
	private String startDateTime;
	private String endDateTime;
	
	@Override
	public void onStart(ISuite suite) {
		
		startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println("Execution Start: "+startDateTime);
	}
	
	@Override
	public void onFinish(ISuite suite) {
		
		endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());		
		System.out.println("Execution End: "+endDateTime);
		
		String execDuration = getDateTimeDiff(startDateTime, endDateTime);
		pSettings.setDuration(execDuration);
		
		System.out.println("\nTotal Duration = "+execDuration);
		
	}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish(ITestContext arg0) {}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext context) {
		if (pSettings == null) {
			pSettings = (PropertySettings)context.getAttribute("setting");
		}
	}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult arg0) {}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult itr) {
		String tcName = itr.getMethod().getMethodName();
		String msg = "";
		try{
			if (itr.getAttribute("scmoId") != null) {
				tcName = itr.getAttribute("scmoId").toString();
				System.out.println(tcName);
				System.out.println("");
			}
			if (itr.getAttribute("msgNotSupport") != null) {
				msg = itr.getAttribute("msgNotSupport").toString();
				System.out.println(msg);
			}
			
			String successString = pSettings.getExecutionId();
			String jsonFormatOfsuccessString = "{\"id\": \"Testid-"+ pSettings.getTestId() +"\", \"tcname\": \""+ tcName +"\", \"stmt\": \"" + successString + "\", \"image\": \"\"}";
			
			//System.out.println(jsonFormatOfsuccessString);
			pSettings.setNoteMessage(jsonFormatOfsuccessString);
			
			System.out.println(itr.getAttribute("msg").toString());
			
		} catch (Exception e){}
		
		/*if (!msg.equals(pSettings.getMessageNotSupport())) {
			WorkcloudCommon wc = new WorkcloudCommon();
			wc.insertTestResultToDB(tcName, "p", pSettings);
		}*/
	}
	
	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult itr) {
		String tcName = itr.getMethod().getMethodName();
		String msg = "";
		try{
			if (itr.getAttribute("scmoId") != null) {
				tcName = itr.getAttribute("scmoId").toString();
				System.out.println(tcName);
				System.out.println("");
			}
			if (itr.getAttribute("msgNotSupport") != null) {
				msg = itr.getAttribute("msgNotSupport").toString();
				System.out.println(msg);
			}
			
			/* Failure details stored in DB */
			String errString = itr.getThrowable().toString();
			String[] temp = errString.split("\n");
			String firstLineOferrString = temp[0].toString();
			String imgName = pSettings.getScreenImgList().get(pSettings.getScreenImgList().size()-1);
			String jsonFormatOferrString = "{\"id\": \"Testid-"+ pSettings.getTestId() +"\", \"tcname\": \""+ tcName +"\", \"stmt\": \"" + firstLineOferrString + "\", \"image\": \""+imgName+"\"}";

			//System.out.println(jsonFormatOferrString);		
			pSettings.setNoteMessage(jsonFormatOferrString);

			System.out.println(itr.getAttribute("msg").toString());

			} catch (Exception e) {
		}

		/*if (!msg.equals(pSettings.getMessageNotSupport())) {
			WorkcloudCommon wc = new WorkcloudCommon();
			wc.insertTestResultToDB(tcName, "f", pSettings);
		}*/
	}

	/* (non-Javadoc)
	 * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult itr) {
		try{
			System.out.println(itr.getAttribute("msg").toString());
		} catch (Exception e){}
	}
	
	/**
	 * generate customize report overriding the testng generateReport() method 
	 */
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
    	
    	String suiteWisereportSummary = "";
    	String summaryReport = "\n***"+pSettings.getBaseUrl()+"***\n";
    	String summaryLog = "";
	    
    	int totalPassed = 0;
	    int totalSkipped = 0;
	    int totalFailed = 0;
	    int totalRun = 0;
    	
    	/* Iterating over each suite included in the test */
        for (ISuite suite : suites)
        {
		        /* Following code gets the suite name */
		        String suiteName = suite.getName();
			    /* Getting the results for the said suite */
			    Map<String, ISuiteResult> suiteResults = suite.getResults();
			    
			    suiteWisereportSummary += "\nTests suite '" + suiteName + "' for "+pSettings.getBrowser()+" browser";
			    suiteWisereportSummary += "\n===============================================================\n";
			    
			    String testName = "";
			    for (Map.Entry<String, ISuiteResult> sr: suiteResults.entrySet())
			    {	
			    	ITestContext tc = sr.getValue().getTestContext();
			    	testName = tc.getName();
			    			
			    	int testWisePassed = tc.getPassedTests().getAllResults().size();
			    	int testWiseSKipped = tc.getSkippedTests().getAllResults().size();
			    	int testWiseFailed = tc.getFailedTests().getAllResults().size();
			    	int testWiseTotal = tc.getAllTestMethods().length;
			    	/*		        
			        suiteWisereportSummary += "\nPassed tests for '" + testName + "' is: " + testWisePassed;
			        suiteWisereportSummary += "\nSkipped tests for '" + testName + "' is: " + testWiseSKipped;
			        suiteWisereportSummary += "\nFailed tests for '" + testName + "' is: " + testWiseFailed;
			        suiteWisereportSummary += "\nTotal tests Run for '" + testName + "' is: " + testWiseTotal+"\n";
			        suiteWisereportSummary +="-----------------------------------------------";
			        */
			    	suiteWisereportSummary +="\nSummary for Test: "+testName+"\n";
			    	suiteWisereportSummary +="-----------------------------------------------";
			        suiteWisereportSummary += "\nPassed - " + testWisePassed;
			        suiteWisereportSummary += "\nSkipped - " + testWiseSKipped;
			        suiteWisereportSummary += "\nFailed - " + testWiseFailed;
			        suiteWisereportSummary += "\nTotal test case run - " + testWiseTotal+"\n";
			        suiteWisereportSummary +="-----------------------------------------------";
			        
			        //counts suitewise passed, skipped, failed & total testcases
			        totalPassed += testWisePassed;
			        totalSkipped += testWiseSKipped;
			        totalFailed += testWiseFailed;
			        totalRun += testWiseTotal;
			        
			      }
		    
			//reportSummary += suiteWisereportSummary;
			
			summaryReport += "\n==================================\n";
		    summaryReport += "Test Result Summary: "+pSettings.getBrowser()+" browser\n";
		    summaryReport += "----------------------------------\n";
		    summaryReport += "Total Passed: "+totalPassed;
		    summaryReport += "\nTotal Skipped: "+totalSkipped;
		    summaryReport += "\nTotal Failed: "+totalFailed;
		    summaryReport += "\nTotal Run: "+totalRun;
		    summaryReport += "\n==================================\n";
		    summaryLog = suiteWisereportSummary + summaryReport;
        }
        
        System.out.println(summaryReport);
        pSettings.setSummaryReportList(summaryReport);   //Set test summary for writing in the email body//
        
        CreateLogger.enterLogData(AppConstant.TEST_CACHE_PATH, summaryReport);
        logWrite(suites, summaryLog);
    }
    
    /**
	 * Writing log
	 */
    private void logWrite(List<ISuite> suites, String summaryLog) {
    	String logSummary = "-----------------------"+ new Date() +"----------------------\n";
    	
    	for (ISuite suite : suites)
        {
		        /* Following code gets the suite name */
    			logSummary += "\nExecuted Suite Name : "+suite.getName()+"\n";
			    /* Getting the results for the said suite */
			    Map<String, ISuiteResult> suiteResults = suite.getResults();

			    for (Map.Entry<String, ISuiteResult> sr: suiteResults.entrySet())
			    {	
			    	//String testName = sr.getMethod().getMethodName();
			    	ITestContext tc = sr.getValue().getTestContext();
			    	logSummary += "\nExecuted Test Name : "+tc.getName()+"\n";
			    	
			    	logSummary += "\n----------------------------------\n";
			    	for ( ITestResult t: tc.getPassedTests().getAllResults()) {
			    		logSummary += "\nExecuted TestCase Name : "+t.getMethod().getMethodName()+", Status : Passed\n";
			    		try{
			    			logSummary += "Message: "+t.getAttribute("msg")+"\n";
			    		} catch (Exception e){}
			    	}
			    	logSummary += "\n----------------------------------\n";
			    	for ( ITestResult t: tc.getFailedTests().getAllResults()) {
			    		logSummary += "\nExecuted TestCase Name : "+t.getMethod().getMethodName()+", Status : Failed,"+t.getMethod().getDescription()+"\n";
			    		try{
			    			logSummary += "Message: "+t.getAttribute("msg")+"\n";
			    		} catch (Exception e){}
			    	}
			    	logSummary += "\n----------------------------------\n";
			    	for ( ITestResult t: tc.getSkippedTests().getAllResults()) {
			    		logSummary += "\nExecuted TestCase Name : "+t.getMethod().getMethodName()+", Status : Skipped\n";
			    		try{
			    			logSummary += "Message: "+t.getAttribute("msg")+"\n";
			    		} catch (Exception e){}
			    	}
			      }
        }
    	logSummary = logSummary + "\n" + summaryLog+"\n----------------------------------\n";
    	CreateLogger.refreshLogData(AppConstant.TEST_LOG_PATH+pSettings.getBrowser()+".log");
    	CreateLogger.enterLogData(AppConstant.TEST_LOG_PATH+pSettings.getBrowser()+".log", logSummary);
    	
    }
    
	/* HH converts hour in 24 hours format (0-23), day calculation */
	public String getDateTimeDiff(String dateStart, String dateStop) {	        
		String time_difference = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            
            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            
            time_difference =  diffHours+"h "+diffMinutes+"m "+diffSeconds+"s ";
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return time_difference;
    }
	
}
