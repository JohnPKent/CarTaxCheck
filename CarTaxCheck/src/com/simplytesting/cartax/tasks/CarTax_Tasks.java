package com.simplytesting.cartax.tasks;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.simplytesting.cartax.pages.CarTax_Page_Check;
import com.simplytesting.cartax.pages.CarTax_Page_InputReg;
import com.simplytesting.cartax.pages.CarTax_Page_VehicleNotFound;
import com.simplytesting.fw.actions.Check;
import com.simplytesting.fw.actions.Click;
import com.simplytesting.fw.actions.Enter;
import com.simplytesting.fw.actions.Get;
import com.simplytesting.fw.utils.ExpectedResults;
import com.simplytesting.fw.utils.InputFile;
import com.simplytesting.fw.utils.OutputFile;
import com.simplytesting.fw.utils.RunObject;

public class CarTax_Tasks {
	private RunObject runObject;
	private OutputFile logFile;
	private String inputFileName;
	private InputFile inputDataFile;
	private String expectedResultsFileName;
	private InputFile expectedResultsFile;
	private WebDriver driver;

	public CarTax_Tasks(RunObject runObject)	{
		this.runObject = runObject;
		this.logFile = runObject.logFile;
		this.driver = runObject.driver;
	}

	public void setinputFileName(String inFileName)	{
		this.inputFileName = inFileName;
	}
	public void setExpectedDataFile(String inFile)	{
		this.expectedResultsFileName = inFile;
	}

	public List<String> getReg(String inFileName)	{
		this.inputFileName = inFileName;
	    List<String> result =  new ArrayList<String>();
		String inputFileText = null;
		String matchedItem = "";
		String regExpressionPattern = "[A-Z]{2}[0-9]{2}[ ]{0,1}[A-Z]{3}";
		// Open file:
		inputDataFile = new InputFile(inputFileName);
		try {
			inputFileText = inputDataFile.readAllFile();
		}catch (Exception e)	{
			String msg = "Failure: unable to open Input Data File '" + e.getMessage() + "'";
			logFile.write(msg);
			Assert.fail(msg);
		}
	    Pattern pattern = Pattern.compile(regExpressionPattern);
	    Matcher matcher = pattern.matcher(inputFileText);
	    if (matcher.find()) {
	        result.add(matcher.group());
	        while (matcher.find()) {
	        	matchedItem = runObject.stringUtil.removeSpaces(matcher.group());
	            result.add(matchedItem);
	        }
	    }
	    return result;				
	}


	public void RunNextReg(String regNumb) {
		Enter.textInto(runObject, CarTax_Page_InputReg.Registration, regNumb);
		Click.on(runObject, CarTax_Page_InputReg.FreeCarCheck_BTN);
		
		if (!Check.ElementExists(runObject, CarTax_Page_VehicleNotFound.VehicleNotFound))	{
			String make = Get.text(runObject, CarTax_Page_Check.Make);
			String model = Get.text(runObject, CarTax_Page_Check.Model);
			String colour = Get.text(runObject, CarTax_Page_Check.Colour);
			String year = Get.text(runObject, CarTax_Page_Check.Year);
			
			if (!ExpectedResults.Check(regNumb, make, model, colour, year))	{
				String msg = "Result=FAIL: Car Reg '" + regNumb +  "' failed the expected results comparison";
				logFile.write(msg);
				Assert.fail(msg);
				
			}
		}
	}

}
