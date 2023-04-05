package common_method;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

	public class getdata{
	public static ArrayList <String> getDataExcel(String testSheetname, String testCaseName) throws IOException 
	{
	ArrayList<String>arrayData = new ArrayList<String>();


	//step 1- locate excel data file by creating the object of Fileinputstream
	FileInputStream fis = new FileInputStream("C:\\Users\\PC\\Desktop\\testdata.xlsx");

	try (//step 2 create the object of SFWorkbook to open the file
	XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
		//step 3 -access the desired sheet
		//step 3.1- fetch count of sheets available in excel file
		int countOfSheet = workbook.getNumberOfSheets();


		//step 3.2- fetch the name of sheet and compare against desired sheet name
		for(int i=0; i<countOfSheet; i++)
		{
		String Sheetname = workbook.getSheetName(i);
		if (Sheetname.equalsIgnoreCase(testSheetname))
		{
		//step 4 ---Access the sheet and iterate through rows to fetch the columns in which test case name column is found
		XSSFSheet sheet =workbook.getSheetAt(i);//here we crating the variable not object

		//step 4.1 --create iterator for rows
		Iterator<Row> rows =sheet.iterator();


		Row firstrow =rows.next(); //this will give you the 1st row A1
		//step 4.2 --- Create iterator for cells



		Iterator<Cell> cells = firstrow.cellIterator();
		int j=0;
		int tc_column=0;


		//step 4.3 --- read the cell values of row no. 1 to compare against the test case name
		while(cells.hasNext())
		{
		Cell cellvalue = cells.next();
		if(cellvalue.getStringCellValue().equalsIgnoreCase("testcasename"))
		{
		tc_column=j;
		}
		j++;
		}
		//step 5 ---fetch the data for designated test case
		while(rows.hasNext())
		{
		Row datarow =rows.next();
		if(datarow.getCell(tc_column).getStringCellValue().equalsIgnoreCase(testCaseName))
		{
		Iterator<Cell> dataCellValue =datarow.cellIterator();
		while(dataCellValue.hasNext())
		{
		Cell dataOfCell = dataCellValue.next();
		//Method 1 -- to extract cell value by using try and catch
		try
		{
		String testdata = dataOfCell.getStringCellValue();
		arrayData.add(testdata);
		}
		catch (IllegalStateException e)
		{
		int inttestData = (int) dataOfCell.getNumericCellValue();
		String stringtestData= Integer.toString(inttestData);
		arrayData.add(stringtestData);
		}
		/*Method 2-- To Extract the cell value by using if and else
		 CellType datatype = dataOfCell.getCellType();
		if (datatype.toString() == "NUMERIC")
		 {
		 int inttestData = (int) dataOfCell.getNumericCellValue();
		 String stringtestData= Integer.toString(inttestData);
		arrayData.add(stringtestData);
		}
		else if (datatype.toString() == "STRING")
		{
		 String testdata = dataOfCell.getStringCellValue();
		arrayData.add(testdata);
		}*/


		/*Method 3 -- Extract the data by converting it into String
		String testdata =dataCellValue.next().toString().replaceAll("\\.\\d+$", "");
		 System.out.println(testdata);
		 arrayData.add(testdata);*/

		/*Method 4-- Extract the data by using Dataformatter
		DataFormatter format =new DataFormatter();
		 String testdata=format.formatCellValue(dataCellValue.next());
		System.out.println(testdata);

		arrayData.add(testdata);*/
		}
		}
		}
		}
		}
	}

	return arrayData; 
	}
	}
	

