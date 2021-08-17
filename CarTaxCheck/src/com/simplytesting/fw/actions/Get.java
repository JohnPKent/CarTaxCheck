package com.simplytesting.fw.actions;

import org.junit.Assert;
import org.openqa.selenium.By;
import com.simplytesting.fw.utils.RunObject;

public class Get {
	private static String thisclass = "Get";
	
	public static String text(RunObject runObject, By selector)	{
		String selectorName = selector.toString();
		String textFound = "";
		String actionName = thisclass+ "Text";
		String msg = "Get Text from: element '" + selectorName + "'";
		runObject.logFile.write("ACTION: " + actionName + msg);
		
		try	{
			if (runObject.seleniumUtil.pageSynchronisation().equals("ERROR")) {
				runObject.logFile.write("WARNING: Page Sync issues.  Attempting to continue");
			}
			textFound = runObject.driver.findElement(selector).getText();
		}catch (Exception e)	{
			msg = "Resut=FAIL: Exception: " + e.getMessage();
			runObject.logFile.write(msg);
			Assert.fail(msg);
			
		}
		runObject.logFile.write("Resut=PASS: text found: '" + textFound + "'");
		return textFound;
	}
}
