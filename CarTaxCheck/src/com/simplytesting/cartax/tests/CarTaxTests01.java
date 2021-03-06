package com.simplytesting.cartax.tests;


import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.simplytesting.cartax.tasks.CarTax_Tasks;
import com.simplytesting.fw.utils.RunObject;

public class CarTaxTests01 {
//	private String SOURCE = this.getClass().getSimpleName();
	private static WebDriver driver;
	private RunObject runObject;


	@Test
	public void InputTests()	{
		runObject = new RunObject("CarTaxTests01");
		runObject.StartBrowser();
		driver = runObject.driver;
		//--------------------------
		//Extract Reg Numbers
		//--------------------------
		CarTax_Tasks tasks = new CarTax_Tasks(runObject);
		List<String> regNumbers = tasks.getReg(runObject.dataDir + "car_input.txt");
		//--------------------------
		//Run actions
		//--------------------------
		for (String regNum : regNumbers)	{
			runObject.NavigateTo(runObject.baseURL);
			tasks.RunNextReg(regNum);
		}
	}
	
	@AfterClass
	public static void TearDown()	{
		driver.quit();	
	}
}
