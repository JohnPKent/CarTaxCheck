package com.simplytesting.fw.utils;
//*******************************************************************************************************************************
//©Simply Testing 2016
//*******************************************************************************************************************************

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;

public class OutputFile {
	private String fileName;
//	private File file;
//	private FileOutputStream fileStream;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	private String  newLine = System.getProperty("line.separator"); 
	private static final String SOURCE = "OUTPUT";

	public OutputFile (String fullFileName) 
	{
		fileName = fullFileName;
//	    file = new File(fileName);
	    //fileStream = new FileOutputStream(file);
	}
  public int OpenOutputFile()
  {
      try
      {
      	fileWriter = new FileWriter(fileName);
      	bufferedWriter = new BufferedWriter(fileWriter);
          return 0;
      }
      catch (IOException e)
      {
      	System.out.println(SOURCE + ": Unable to open OutputFile: '" + fileName + "' Exception: " + e.getMessage());
          return 30;
      }
  }
  public int OpenOutputFileAppend()
  {
      try
      {
      	fileWriter = new FileWriter(fileName, true);
      	bufferedWriter = new BufferedWriter(fileWriter);
          return 0;
      }
      catch (IOException e)
      {
      	System.out.println(SOURCE + ": Unable to open OutputFile: '" + fileName + "' Exception: " + e.getMessage());
          return 30;
      }
  }

	public void write (String outputLine) 
	{
		try	
		{
			outputLine = outputLine + newLine;
			bufferedWriter.write(outputLine);
			bufferedWriter.flush();
//		    byte[] data = outputLine.getBytes();
//		    fileStream.write(data, 0 , data.length);
		}
		catch	(IOException e)	{
		   System.out.println(SOURCE + ": Exception on write to file OutputFile: Exception: " + e.getMessage());
		}
	}
	public void write (String sSource, String outputLine) 
	{
		try	
		{
			outputLine = sSource + ": " + outputLine;
			outputLine = outputLine + newLine;
			bufferedWriter.write(outputLine);
			bufferedWriter.flush();
//		    byte[] data = outputLine.getBytes();
//		    fileStream.write(data, 0 , data.length);
		}
		catch	(IOException e)	{
		   System.out.println(SOURCE + ": Exception on write to file OutputFile: Exception: " + e.getMessage());
		}
	}

	public void closeFile () throws IOException	
	{
		bufferedWriter.close();
		fileWriter.close();
	}

}


