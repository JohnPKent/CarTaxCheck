package com.simplytesting.cartax.pages;

import org.openqa.selenium.By;

public class CarTax_Page_Check {

	public static By Make = By.xpath("//dt[text()='Make']/../dd");
	public static By Model = By.xpath("//dt[text()='Model']/../dd");
	public static By Colour = By.xpath("//dt[text()='Colour']/../dd");
	public static By Year = By.xpath("//dt[text()='Year']/../dd");
}
