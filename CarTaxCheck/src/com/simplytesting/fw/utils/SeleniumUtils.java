//************************************************************************************
//***                       Test Liberation Framework
//***                 Copyright Simply Testing Ltd  2016 to 2021
//************************************************************************************
package com.simplytesting.fw.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;


import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumUtils {
	private String SOURCE = this.getClass().getSimpleName();
	private String screenshotDir;
	RunObject runObject;
	private WebDriver driver;
	private OutputFile logFile;
	private JavascriptExecutor jsEx;


	public SeleniumUtils(RunObject runObject) {
		this.runObject = runObject;
		this.screenshotDir = runObject.screenDir;
		this.logFile = runObject.logFile;
	}
	public WebDriver getDriver()
	{
		return driver;
	}
	public void TakePicture()
	{
		if (driver == null)
		{
			logFile.write(SOURCE, "Driver is not instantiated so not taking picture" );
			return;
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");
		Date date = new Date();
		  
		String sSSFileName =  screenshotDir + "Screenshot_" + dateFormat.format(date) + ".png";
		try
		{
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(sSSFileName));			
			logFile.write(SOURCE, "Picture saved to " + sSSFileName);
		}
		catch (Exception e)
		{
			 logFile.write(SOURCE, "FAILURE: when taking picture.  Exception:" + e.getMessage());
		}
		//TakePicture_FullScreen();
	}
	public void TakePicture_FullScreen()
	{
		  DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmssSSSS");
		  Date date = new Date();
		  
		  String sSSFileName =  screenshotDir + "ScreenshotFullScr_" + dateFormat.format(date) + ".jpg";
		  try 
		  {
	            Robot robot = new Robot(); 
	            Rectangle scrRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	            BufferedImage screenFullImage = robot.createScreenCapture(scrRect);
	            ImageIO.write(screenFullImage, "jpg", new File(sSSFileName));
	             
				logFile.write(SOURCE, "Picture saved to " + sSSFileName);
		  }
			catch (Exception e)
			{
				 logFile.write(SOURCE, "FAILURE: when taking full screen picture.  Exception:" + e.getMessage());
		    }
	}
	public WebDriver StartBrowser(String chromeDriverPath) {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		logFile.write("Starting ChromeDriver");
		try	{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			
		}catch (Exception e)	{
			String msg = "Failure: unable to start ChromeDriver";
			logFile.write(msg);
			Assert.fail(msg);
		}
		logFile.write("ChromeDriver Started");
		return driver;
		
	}
	
	public String pageSynchronisation() throws InterruptedException	 {
        //=====================================================
        //Check Page readyState
        //=====================================================
        int i;
        long iIFramesCount = 0;
        String sJavaScriptCode;
        String sTitle = null;
        String sReadyState = "";

        JavascriptExecutor jsEx = ((JavascriptExecutor)driver); 

        //=====================================================
        //Page readyState
        //=====================================================
        for (i = 1; i < 10; i++)
        {
            try
            {
                sReadyState = (String)jsEx.executeScript("return document.readyState");
            }
            catch (Exception exp)
            {
                logFile.write(SOURCE, "Problem with JavaScript getting document.readyState.  Error=" + exp.getMessage());
                //Return error here as wasting hours waiting for JavaScript failure
	            return ("ERROR");
            }
            logFile.write(SOURCE, "Readystate of page=" + sReadyState);
            //sReadyState will be null if ExecuteScript fails first time (can have error: System.InvalidOperationException: Session not started or terminated)
            if ((sReadyState != null) && (sReadyState.equals("complete")))
            {
                break;
            }
			Thread.sleep(2000);
        }
        if ((sReadyState != null) && !(sReadyState.equals("complete")))
        {
            logFile.write(SOURCE, "FAILURE: Page ReadyState did not reach 'complete");
            return ("ERROR");
        }

        //=====================================================
        //Get Page Title
        //=====================================================
        try
        {
        	sTitle = (String)jsEx.executeScript("return document.title");
        }
        catch (Exception exp)
        {
            logFile.write(SOURCE, "Problem with executing JavaScript to get doc title.  Error=" + exp.getMessage());
        }
        logFile.write(SOURCE, "Title of page=" + sTitle);

        //===========================================================================================================================
        //Check All Frames readyState
        //===========================================================================================================================
        try
        {
	        sJavaScriptCode = "return(top.frames.length)";
	        iIFramesCount = (long)jsEx.executeScript(sJavaScriptCode);
            logFile.write(SOURCE, "Number of IFrames=" + Long.toString(iIFramesCount));
        }
        catch (Exception exp)
        {
            logFile.write(SOURCE, "WARNING on getting frame count: " + exp.toString());
        } 

        try
        {
            for ( i= 0; i<iIFramesCount; i++)
            {
                sTitle = "";
                sReadyState = "";
                // switch to every frame
                driver.switchTo().frame(i);
                sJavaScriptCode = "return (document.title)";
                sTitle = (String)jsEx.executeScript(sJavaScriptCode);
                sJavaScriptCode = "document.readyState";
                sReadyState = (String)jsEx.executeScript(sJavaScriptCode);
                logFile.write(SOURCE, "iFrame Title=" + sTitle);
                logFile.write(SOURCE, "iFrame ReadyState=" + sReadyState);
                //logFile.write(SOURCE, "Number of child iFrames=" + weIFrames.Count());
                driver.switchTo().defaultContent();
            }
        }
        catch (Exception ex)
        {
            sTitle = "Unknown";
            logFile.write(SOURCE, "WARNING on getting frame readyState: " + ex.toString());
        }
        //Switch Back to main page
        driver.switchTo().defaultContent();

        //===========================================================================================================================
        //Wait for JQuery.active == 0
        //===========================================================================================================================
    	//This method waits for all jQuery to have 0 active connections.  JQuery.active==0
        if(IsAJaxLoading() )
        {
            logFile.write(SOURCE, "JQuery is active after all iterations" );
        }
        return (" ");
    }
	private boolean IsAJaxLoading() throws InterruptedException
	{
        int i;
        int iJQueryActive;
        boolean bJQueryPresent = false;
        boolean bAjaxLoading = false;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); 
		//Debug: Is JQuery active?
		try
        {
            bJQueryPresent = (boolean)jsEx.executeScript("return !!window.jQuery");
        }
        catch(Exception e)
        {
	        logFile.write(SOURCE, "WARNING: Exception on JavaScript: 'return !!window.jQuery'.  Exception = " + e.getMessage());
        }
        if (bJQueryPresent)
        { 
            for (i = 1; i < 50; i++)
            {
                try
                {
                    iJQueryActive = (int)jsEx.executeScript("return jQuery.active");
                }
                catch (Exception e)
                {
                    logFile.write(SOURCE, "Problem with JavaScript getting jQuery.active.  Problem=" + e.getMessage());
                    iJQueryActive = 0;
                }
			    if (iJQueryActive ==0)
			    {
	                logFile.write(SOURCE, "JQuery.active==0");
	                bAjaxLoading = false;
                    break;
			    }else
			    {
	                logFile.write(SOURCE, "JQuery.active=" + Integer.toString(iJQueryActive));
	                bAjaxLoading = true;
			    }
			    Thread.sleep(100);
            }
            logFile.write(SOURCE, "JQuery active complete at iteration =" + Integer.toString(i));
        }else
        {
        	bAjaxLoading = false;
        }
	    driver.manage().timeouts().implicitlyWait(runObject.IMPICITWAITTIME, TimeUnit.SECONDS);
        return(bAjaxLoading);
	} 

}
