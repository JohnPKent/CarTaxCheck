package com.simplytesting.fw.actions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.simplytesting.fw.utils.RunObject;

public class Check {
	private static String thisclass = "Click";
	
	public static boolean ElementExists(RunObject runObject, By selector)	{
		String selectorName = selector.toString();
		
		String actionName = thisclass+ "on";
		String msg = " Checking element '" + selectorName + "' exists";
		runObject.logFile.write("ACTION: " + actionName + msg);
		
		try	{
			if (runObject.seleniumUtil.pageSynchronisation().equals("ERROR")) {
				runObject.logFile.write("WARNING: Page Sync issues.  Attempting to continue");
			}
			runObject.driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			runObject.driver.findElement(selector);
			runObject.driver.manage().timeouts().implicitlyWait(runObject.IMPICITWAITTIME, TimeUnit.SECONDS);
		}catch (Exception e)	{
			msg = "Resut=PASS: element does not exist " ;
			runObject.logFile.write(msg);
			return false;
		}
		runObject.logFile.write("Resut=PASS: element does exist");
		return true;
		
	}
}
