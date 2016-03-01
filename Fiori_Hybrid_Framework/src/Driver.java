import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import utility.Xls_Reader;


public class Driver {
	
	public static Keyword_Impl kw = new Keyword_Impl();
	public static String testScenariosPath =  kw.currPrjLoc + "\\testScenarios\\Fiori.xls";
	public static Xls_Reader xls = new  Xls_Reader( testScenariosPath );
	Logger log = Logger.getLogger("Logger");
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		executeTestSuite();
	}
	
	public static void executeTestSuite() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(int rowNum=2; rowNum<=xls.getRowCount("TestSuite"); rowNum++ ) {
			if (xls.getCellData("TestSuite", "Flag", rowNum).equalsIgnoreCase("y")) {
				String testCase = xls.getCellData("TestSuite", "TC ID", rowNum);
				executeTestCase(testCase);
			}
		}
	}
	
	
	public static void executeTestCase( String testName ) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(int rowNum=2; rowNum<=xls.getRowCount("TestCases"); rowNum++ ) { 
			if (xls.getCellData("TestCases", "TC ID", rowNum).equalsIgnoreCase(testName)) {
				String keyword = xls.getCellData("TestCases", "Keyword", rowNum);
				String object = xls.getCellData("TestCases", "Object", rowNum);
				String data = xls.getCellData("TestCases", "Data", rowNum);
				String stepResults = "";
				
				Method[] methods = kw.getClass().getMethods();
				
				for(Method m: methods) {
					if (m.getName().equalsIgnoreCase(keyword)) {
						stepResults = (String) m.invoke(kw, object, data);
					}
				}
				
			/*	if (keyword.equalsIgnoreCase("launchBrowser"))
					stepResullts = kw.launchBrowser(object, data);
				else if (keyword.equalsIgnoreCase("navigateURL"))
					stepResullts = kw.navigateURL(object, data);*/
				
				System.out.println(keyword + "  ---  " + object + "  ---  " +  data + "  ---  " +  stepResults);
				xls.setCellData("TestCases", "StepResults", rowNum, stepResults);
			}
		}
	}

}
