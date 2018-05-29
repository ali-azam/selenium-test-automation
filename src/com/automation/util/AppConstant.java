package com.automation.util;

public class AppConstant {
	private AppConstant () { } // prevents instantiation
	
	private static String USER_DIR = System.getProperty("user.name");
	public static final String CURRENT_DIR = System.getProperty("user.dir");
	
	public static final String ERROR_LOG_PATH = CURRENT_DIR+"/assets/log/error.log";
	public static final String TEST_LOG_PATH = CURRENT_DIR+"/assets/log/";
	public static final String SETTING_PATH = CURRENT_DIR+"/config/setting.conf";
	//public static final String TEST_SUITE_XML = CURRENT_DIR+"/config/testng.xml";
	public static final String FTP_DOWNLOAD = CURRENT_DIR+"/resource/FTPDownload";
	//public static final String TEST_SUITE_XML = CURRENT_DIR+"/config/";
	public static final String APP_CONFIG = CURRENT_DIR+"/config/";
	public static final String SCREEN_SHOT_DIR = CURRENT_DIR+"/assets/screenshots/";
	public static final String ASSETS_PATH = CURRENT_DIR + "/assets/";
	public static final String RESOURCE_DIR = CURRENT_DIR+"/resource/";
	public static final String CHROME_DRIVER = CURRENT_DIR+"/lib/driver/chromedriver";
	//public static final String CHROME_DRIVER = CURRENT_DIR+"/lib/driver/chromedriver.exe";
	public static final String DOWNLOAD_DIR = "/Users/"+ USER_DIR +"/Downloads/";
	public static final String TEST_CACHE_PATH = CURRENT_DIR+"/assets/log/testCache.txt";
	
}
