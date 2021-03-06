package com.simplytesting.fw.utils;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class RunObject {
	private String SOURCE = this.getClass().getSimpleName();
	public OutputFile logFile;
	public WebDriver driver;
	public int IMPICITWAITTIME;
	public SeleniumUtils seleniumUtil;
	public StringUtils stringUtil;
	public String logsDir;
	public String screenDir;
	public String dataDir;
	public String baseURL;
	public String chromeDriverPath;
	String logFileName;
	String testFileName;
	
	public RunObject(String testFileName)	{
		this.testFileName = testFileName;
		String configFilePath = System.getProperty("user.dir") + "//src//config//config.properties";

		Properties prop = new Properties();
		try
		{
			prop.load(new FileInputStream(configFilePath));
		}
		catch(Exception e)
		{
			System.console().printf("Unable to load config file '" + configFilePath + "'aborting");
			Assert.fail();
		}
		try
		{
			IMPICITWAITTIME = Integer.parseInt(prop.getProperty("ImplicitWaitTime"));
			baseURL = prop.getProperty("CarTaxURL");
			
		}
		catch(Exception e)
		{
			System.console().printf("Unable to read a config item.... aborting.  Error:" + e.getMessage());
			Assert.fail();
		}
		logsDir = System.getProperty("user.dir") + "\\test-results\\";
		screenDir =  logsDir + "screenshots";
		dataDir = System.getProperty("user.dir") + "\\Data\\";
		chromeDriverPath = System.getProperty("user.dir") + "\\webdrivers\\chromedriver.exe";
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");
		Date date = new Date();
		logFileName =  logsDir + dateFormat.format(date) + testFileName + ".log";
		
		logFile = new  OutputFile(logFileName);
		logFile.OpenOutputFile();
		logFile.write(SOURCE, "*********************************************************************************");
		logFile.write(SOURCE, "*    Starting Tests");
		logFile.write(SOURCE, "*********************************************************************************");
		
		seleniumUtil = new SeleniumUtils(this);
		stringUtil = new StringUtils(logFile);

	}

	public void StartBrowser() {
		driver = seleniumUtil.StartBrowser(chromeDriverPath);
		
	}

	public void NavigateTo(String url) {
		try	{
			driver.get(url);
			
		}catch (Exception e)	{
			String msg = "Failure: unable to navigate to '" + url + "'";
			logFile.write(msg);
			Assert.fail(msg);
		}
		logFile.write("Navigated to '" + url + "'");
	}
	

}
