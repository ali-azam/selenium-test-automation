package com.automation.util;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

public class PropertySettings {
	
	public WebDriver currentDriver = null;
	
	private Map<Integer, String> browserList;
	private String browser;
	private String baseUrl;
	private String creatorUser;
	private String creatorPass;
	private String mailFrom;
	private String fileDownloadDir;
		
	private String mailTo;
	private String mailPassword;
	private int waitTime;
	private int state;
	private boolean loged;
	private boolean appletDelete;
	private String testngxml;
	
	private String reportTime; //yyyy-MM-dd-hh-mm-ss
	private String year;
	private String month;
	private String testId;
	private String executionId;
	private String osname;
	private static String execDuration;
	
	private static String extentReportPath = null;
	private String messageNotSupport = "SafariNotSupported";
	private String noteMessage;
	public static ArrayList<String> uploadFileList;
	
	public static ArrayList<String> screenImgList = new ArrayList<String>();
	private static ArrayList<String> summaryReportList = new ArrayList<String>();
	
	/** 
	 * construct setting information
	 **/
	public PropertySettings()
	{
		Properties prop = null;
		try{
            prop = new Properties();
            prop.load(new FileInputStream(AppConstant.SETTING_PATH));
            
            this.state = 0;
            this.loged = false;
            this.baseUrl = prop.getProperty("url");
            this.creatorUser = prop.getProperty("creatorUser");
            this.creatorPass = prop.getProperty("creatorPass");
                      
            this.mailFrom = prop.getProperty("mailfrom");
            this.mailPassword = prop.getProperty("mailpass");
            this.mailTo = prop.getProperty("mailto");
            this.fileDownloadDir = prop.getProperty("fileDownloadDir");
            this.testngxml = prop.getProperty("testngxml");
            this.waitTime = Integer.parseInt(prop.getProperty("waitTime"));
            this.browser = prop.getProperty("browser");
            this.browserList = parseBrowserList(this.browser);
            this.browser = this.browserList.get(0);
            
            this.appletDelete = prop.getProperty("appletDelete").equalsIgnoreCase("yes");          
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
			//System.out.println(sdf.format(new Date()));
            this.reportTime = sdf.format(new Date());
            SimpleDateFormat ydf = new SimpleDateFormat("yyyy");
            this.year = ydf.format(new Date());
            SimpleDateFormat mdf = new SimpleDateFormat("MMMM");
            this.month = mdf.format(new Date());
            this.testId = "";
            this.executionId = "ExecutionId-"+getRandomValueFixedLength(6);
            this.noteMessage = "";
            this.osname = System.getProperty("os.name");
            CreateLogger.refreshLogData(AppConstant.TEST_CACHE_PATH);
		}catch (Exception e) {           
           System.out.println(e.getMessage());
	    }
	}
	
	/**
	 * @return the osname
	 */
	public String getOsname() {
		return osname;
	}

	/**
	 * @return the appletDelete
	 */
	public boolean isAppletDelete() {
		return appletDelete;
	}

	/**
	 * @return the messageNotSupport
	 */
	public String getMessageNotSupport() {
		return messageNotSupport;
	}

	/**
	 * @return the noteMessage
	 */
	public String getNoteMessage() {
		return noteMessage;
	}

	/**
	 * @param noteMessage the noteMessage to set
	 */
	public void setNoteMessage(String noteMessage) {
		this.noteMessage = noteMessage;
	}
	
	/**
	 * @return the reportTime
	 */
	public String getReportTime() {
		return reportTime;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return the extentReportPath
	 */
	public String getExtentReportPath() {
		return extentReportPath;
	}

	/**
	 * @param extentReportPath the extentReportPath to set
	 */
	public void setExtentReportPath(String extentReportPath) {
		this.extentReportPath = extentReportPath;
	}
	
	/**
	 * @return the testId
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @param testId the testId to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	/**
	 * @return the executionId
	 */
	public String getExecutionId() {
		return executionId;
	}


	/**
	 * @return the uploadFileList
	 */
	public ArrayList<String> getUploadFileList() {
		return uploadFileList;
	}

	/**
	 * @param uploadFileList the uploadFileList to set
	 */
	public void setUploadFileList(ArrayList<String> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}
	
	/**
	 * @return the uploadFileList
	 */
	public ArrayList<String> getScreenImgList() {
		return screenImgList;
	}

	/**
	 * @param uploadFileList the uploadFileList to set
	 */
	public void setScreenImgList(String imageName) {
		this.screenImgList.add(imageName);
	}

	/**
	 * @return the waitTime
	 */
	public int getWaitTime() {
		return waitTime;
	}
		
	/**
	 * @return the currentDriver
	 */
	public WebDriver getCurrentDriver() {
		return currentDriver;
	}

	/**
	 * @param currentDriver the currentDriver to set
	 */
	public void setCurrentDriver(WebDriver currentDriver) {
		this.currentDriver = currentDriver;
	}

	/**
	 * @return the testngxml
	 */
	public String getTestngxml() {
		return testngxml;
	}

	/**
	 * @param testngxml the testngxml to set
	 */
	public void setTestngxml(String testngxml) {
		this.testngxml = testngxml;
	}
	
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return the isLogedout
	 */
	public boolean getLoged() {
		return loged;
	}

	/**
	 * @param isLogedout the isLogedout to set
	 */
	public void setLoged(boolean loged) {
		this.loged = loged;
	}

	/**
	 * @return the fileDownloadDir
	 */
	public String getFileDownloadDir() {
		return fileDownloadDir;
	}

	/**
	 * @return the browsers
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * @return the browserList
	 */
	public Map<Integer, String> getBrowserList() {
		return browserList;
	}
	
	/*
	 * prepare and return browser list
	 */
	public Map<Integer, String> parseBrowserList(String browsers) {
		Map<Integer, String> tmpBrowserList = new HashMap<Integer, String>();
		
		String [] temp = browsers.split(",");				
		int i = 0;
		for(String browserString : temp) {
			tmpBrowserList.put(i, browserString.trim());
			i++;
		}
		return tmpBrowserList;
	}
	
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * @return the creatorUser
	 */
	public String getCreatorUser() {
		return creatorUser;
	}
	
	/**
	 * @return the creatorPass
	 */
	public String getCreatorPass() {
		return creatorPass;
	}
	
	/**
	 * @return the mailFrom
	 */
	public String getMailFrom() {
		return mailFrom;
	}
	
	/**
	 * @return the mailTo
	 */
	public String getMailTo() {
		return mailTo;
	}
	
	/**
	 * @return the mailPassword
	 */
	public String getMailPassword() {
		return mailPassword;
	}
	
	/**
	 * To print setting information in terminal
	 */
	public void print(){
		System.out.println(getBrowser());
		System.out.println(getBaseUrl());
		System.out.println(getCreatorUser());
		System.out.println(getCreatorPass());
		System.out.println(getMailFrom());
		System.out.println(getMailPassword());
		System.out.println(getMailTo());
	}

	/** returns web driver */
	public WebDriver getDriver()
	{	
		if (this.currentDriver == null) {
			currentDriver = setDriver();
		}
		
		return currentDriver;
	}
	
	/** returns web driver */
	public WebDriver setDriver()
	{	
		if (browser.equals("safari")) {
			currentDriver = new SafariDriver();
		} else if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", AppConstant.CHROME_DRIVER);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			options.addArguments("--disable-extensions");
			currentDriver = new ChromeDriver(options);
		} else {
			System.err.println("\nUnknown Browser :: either no such browser installed or browser string is invalid.\n");
			System.exit(0);
		}
				
		currentDriver.manage().window().setPosition(new Point(0, 0));
		currentDriver.manage().window().setSize(new org.openqa.selenium.Dimension(1280, 800));
	
		return currentDriver;
	}
	
	private long getRandomValueFixedLength(int length){
		Random random = new Random();
		long number = random.nextInt((int)(Math.pow(10, length)));
		
		return number;
	}


	/**
	 * @param browserList the browserList to set
	 */
	public void setBrowserList(Map<Integer, String> browserList) {
		this.browserList = browserList;
	}
	
	/**
	 * @return the execDuration
	 */
	public String getDuration() {
		return execDuration;
	}

	/**
	 * @param execDuration the execDuration to set
	 */
	public void setDuration(String execDuration) {
		this.execDuration = execDuration;
	}
	
	/**
	 * @return the summaryReportList
	 */
	public ArrayList<String> getSummaryReportList() {
		return summaryReportList;
	}

	/**
	 * @param summaryReport the summaryReportList to set
	 */
	public void setSummaryReportList(String summaryReport) {
		this.summaryReportList.add(summaryReport);
	}
	
	/**
	 * @param String api url
	 * @param String database host
	 * @return String ip
	 */
	private String getDbHost(String httpUrl, String DbHost) {

		if (httpUrl.contains("127") || httpUrl.contains("localhost")) {
			return DbHost;
		}

		String[] tmpArr = httpUrl.split("/");
		String ip = "";
		for (int i = 0; i < tmpArr.length; i++) {
			if (isIp(tmpArr[i])) {
				ip = tmpArr[i];
				break;
			}
		}

		String[] arrDbHost = DbHost.split(":");
		String[] arrIp = ip.split(":");
		ip = arrIp[0];
		if (arrDbHost.length > 1) {
			ip = ip + ":" + arrDbHost[1];
		}

		return ip;
	}
	
	/**
	 * @param String ip
	 * @return boolean
	 */
	private Boolean isIp(String ip) {
		String[] arrIp = ip.split("\\.");
		if (arrIp.length == 4) {
			return true;
		} else {
			return false;
		}
	}

}
