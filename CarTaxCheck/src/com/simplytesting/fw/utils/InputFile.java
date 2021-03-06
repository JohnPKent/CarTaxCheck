//*******************************************************************************************************************************
//?Simply Testing 2016
//*******************************************************************************************************************************
package com.simplytesting.fw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class InputFile {
	private String fileName;	
	private FileReader fReader;
	private BufferedReader bReader;

	private static final String SOURCE = "INFILE";

	public InputFile (String fileName) {
			this.fileName = fileName;
	}
	
	public void setFileName (String fileName) {
			this.fileName = fileName;
	}
		
	public int openFile () {
		try	{
	  		fReader = new FileReader(fileName);
	  		bReader = new BufferedReader(fReader);
	  		return(0);
	  	}
	  	catch (FileNotFoundException e)	{
	  	   	System.out.println(SOURCE + ": ERROR: opening file: " + fileName + " Exception=" + e.getMessage());
	  	   	return(10);
	  	}
	}
	public void closeFile () {
	try	{
		bReader.close();
		fReader.close();
	}
	catch (Exception e)	{
	   	System.out.println(SOURCE + ": ERROR: Error on Close File: " + fileName + " Exception: " + e.getMessage());
	}
		
	}
	public String readLine() {
		String readLine = null;
	try	{
		readLine = bReader.readLine();
	   	return(readLine);
	}
	catch (Exception e)	{
	   	return(SOURCE + ": ERROR: File Read Error: " + fileName+ e.getMessage());
	}
	}
	
	public String readAllFile() throws IOException 
	{
		try {
			File file = new File(fileName);
			FileInputStream fis;
			fis = new FileInputStream(file);		
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String strData = new String(data, "UTF-8");
			return strData;

		} catch (FileNotFoundException e) {
			System.out.println(SOURCE + ": ERROR: Error on readAllFile: " + fileName + " Exception: " + e.getMessage());
			return "";
		}
	
	}
	public String findLineBegining(String sText)	{
		String sLine = this.readLine();
		while (sLine  != null)	{
	   	//System.out.println("sText: " + sText.length() + " sLine: " + sLine.length() + sText);
			if (sLine.length() >= sText.length())	{
				//Do case independent compare
				if (sText.toLowerCase().compareTo(sLine.substring(0, sText.length()).toLowerCase())==0)	{
					return(sLine);
				}
			}
			//Read Next Line
			sLine = this.readLine();
		}
		this.closeFile();
		return(null);
	}

}
