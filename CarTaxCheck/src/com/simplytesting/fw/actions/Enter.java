package com.simplytesting.fw.actions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import com.simplytesting.fw.utils.RunObject;

public class Enter {
	private static String thisclass = "Enter";
	
	public static void textInto(RunObject runObject, By selector, String text)	{
		String selectorName = selector.toString();
		
		String actionName = thisclass+ "on";
		String msg = "Entering Text: '" + text + "' into element '" + selectorName + "'";
		runObject.logFile.write("ACTION: " + actionName + msg);
		
		try	{
			if (runObject.seleniumUtil.pageSynchronisation().equals("ERROR")) {
				runObject.logFile.write("WARNING: Page Sync issues.  Attempting to continue");
			}
			WebDriverWait wait = new WebDriverWait(runObject.driver, 60);
			WebElement elementObj = wait.until(ExpectedConditions.elementToBeClickable(selector));
			elementObj.clear();
			elementObj.sendKeys(text);
		}catch (Exception e)	{
			msg = "Resut=FAIL: Exception: " + e.getMessage();
			runObject.logFile.write("ACTION: " + actionName + msg);
			Assert.fail(msg);
			
		}
		runObject.logFile.write("Resut=PASS: entered text");
		
	}
}
