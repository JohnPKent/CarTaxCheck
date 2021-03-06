package com.simplytesting.fw.utils;

public class Checks {
	private OutputFile logFile;
	private String SOURCE = this.getClass().getSimpleName();

	public Checks(OutputFile logFile)	{
		this.logFile = logFile;
	}
    //************************************************************************************************
    //Check Methods.
    //************************************************************************************************
	public boolean CheckText(String sAction, String sData, String sText, boolean bCaseIndependant)
	{
		String sTextCompare;
		String sDataCompare;
        if (bCaseIndependant)
        {
        	sTextCompare = sText.toLowerCase();
        	sDataCompare = sData.toLowerCase();
            logFile.write(SOURCE, String.format("%s: Performing case independent comparison", sAction));
        }else
        {
        	sTextCompare = sText;
        	sDataCompare = sData;
            logFile.write(SOURCE, String.format("%s: Performing case sensitive comparison", sAction));
        }

        if (sTextCompare.equals(sDataCompare))
        {
            logFile.write(SOURCE, String.format("%s=PASS Retrieved Value is: '%s' and it contains '%s'", sAction, sText, sData));
            return(true);
        }
        else    {
            logFile.write(SOURCE, String.format("%s=FAILURE: Retrieved Value is: '%s' - Expected text is: '%s'", sAction, sText, sData));
            return(false);
        }

	}
	public boolean CheckTextContains(String sAction, String sData, String sText, boolean bCaseIndependant)
	{
		String sTextCompare;
		String sDataCompare;
        if (bCaseIndependant)
        {
        	sTextCompare = sText.toLowerCase();
        	sDataCompare = sData.toLowerCase();
            logFile.write(SOURCE, String.format("%s: Performing case independent comparison", sAction));
        }else
        {
        	sTextCompare = sText;
        	sDataCompare = sData;
            logFile.write(SOURCE, String.format("%s: Performing case sensitive comparison", sAction));
        }

        if (sTextCompare.contains(sDataCompare))
        {
            logFile.write(SOURCE, String.format("%s=PASS: Retrieved Value is: '%s' and it contains '%s'", sAction, sText, sData));
            return(true);
        }else    
        {
            logFile.write(SOURCE, String.format("%s=FAILURE: Retrieved Value is: '%s' - Expected text is: '%s'", sAction, sText, sData));
            return(false);
        }

	}
	public boolean CheckNumber(String sAction, int dNumberExpected, int dNumberActual)
	{
       if (dNumberActual == dNumberExpected)
        {
            logFile.write(SOURCE, String.format("%s=PASS Retrieved Value is: '%d' and it equals expected value of '%d'", sAction, dNumberActual, dNumberExpected));
            return(true);
        }else    
        {
            logFile.write(SOURCE, String.format("%s=FAILURE: Retrieved Value is: '%d' - Expected value is: '%d'", sAction, dNumberActual, dNumberExpected));
            return(false);
        }
	}
}
