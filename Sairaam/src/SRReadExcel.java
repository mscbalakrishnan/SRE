import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SRReadExcel {
 
	
	
	
	
	public static void readXLSFile() throws IOException
	{
		System.out.println("readXLSFile 1");
		InputStream ExcelFileToRead = new FileInputStream("D:/SRE/Sept2017/PARENTSEPT2017.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			
			//if(rows. == 2){
				row=(HSSFRow) rows.next();
				Iterator cells = row.cellIterator();
				
				while (cells.hasNext())
				{
					cell=(HSSFCell) cells.next();
			
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
					{
						System.out.print(cell.getStringCellValue()+" ");
					}
					else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
					{
						System.out.print(cell.getNumericCellValue()+" ");
					}
					else
					{
						//U Can Handel Boolean, Formula, Errors
					}
				}
				System.out.println();
				
			}
		}
	

	
	public static void writeXLSFile() throws IOException {
		
		String excelFileName = "C:/Test.xls";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			HSSFRow row = sheet.createRow(r);
	
			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				HSSFCell cell = row.createCell(c);
				
				cell.setCellValue("Cell "+r+" "+c);
			}
		}
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);
		
		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void readXLSXFile() throws IOException
	{
		System.out.println("readXLSXFile 1");
		InputStream ExcelFileToRead = new FileInputStream("D:/SRE/empdetails.xlsx");
		XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);
		
		XSSFWorkbook test = new XSSFWorkbook(); 
		final FormulaEvaluator evaluator;
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row; 
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();
		

		while (rows.hasNext())
		{
			row=(XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			if(row.getRowNum() == 2){
				
				System.out.println("ROW NUMBER ONEEEEEEEEEEEEE");
			
			while (cells.hasNext())
			{
				
					cell=(XSSFCell) cells.next();
					
					
					System.out.println("Cell Type: ..:"+cell.getCellType() + "\n");
					
					//String cellText = readCellValueAsString(cell);
					String cellText = readCellValueAsString1(cell,evaluator);
					
					System.out.print("Row Number :"+row.getRowNum() + " :--------------Column Index --------: "+ cell.getColumnIndex() + " Column Value -------------:" + cellText + " " +"\n");
					
					
					
					/*if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
					{
						System.out.print("Row Number :"+row.getRowNum() + " :--------------Column Index --------: "+ cell.getColumnIndex() + " Column Value -------------:" + cell.getStringCellValue() + " " +"\n");
						
					}
					else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
					{
						System.out.print("Row Number :"+row.getRowNum() + " :--------------Column Index --------: "+ cell.getColumnIndex() + " Column Value -------------:" + cell.getNumericCellValue() + " " + "\n");
					}
					
					else
					{
						//U Can Handel Boolean, Formula, Errors
					}*/

					
				
			}
			}
			System.out.println();
		}
	
	}
	
	public static void writeXLSXFile() throws IOException {
		
		System.out.println("writeXLSXFile called.............");
		
		String excelFileName = "D:/Test1.xlsx";//name of excel file

		String sheetName = "Sheet1";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName) ;

		//iterating r number of rows
		for (int r=0;r < 5; r++ )
		{
			XSSFRow row = sheet.createRow(r);

			//iterating c number of columns
			for (int c=0;c < 5; c++ )
			{
				XSSFCell cell = row.createCell(c);
	
				cell.setCellValue("Cell "+r+" "+c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}

	
    public  static  String readCellValueAsString(XSSFCell cell) { 
        
        switch (cell.getCellType()) { 
            case XSSFCell.CELL_TYPE_BLANK: 
                return ""; 
            case XSSFCell.CELL_TYPE_BOOLEAN: 
                return Boolean.toString(cell.getBooleanCellValue()); 
            case XSSFCell.CELL_TYPE_STRING: 
                return cell.getStringCellValue(); 
            case XSSFCell.CELL_TYPE_NUMERIC: 
                Double d = new Double(cell.getNumericCellValue()); 
                long l = d.longValue(); 
                Double compD = (double) l; 
                if (compD.equals(d)) { 
                    return Long.toString(l); 
                } else { 
                    return Double.toString(d); 
                } 
        } 
        return null; 
    } 	
	

    
    public  static String readCellValueAsString1(XSSFCell cell, FormulaEvaluator evaluator) { 
        CellValue cellValue = evaluator.evaluate(cell); 
        switch (cellValue.getCellType()) { 
            case XSSFCell.CELL_TYPE_BLANK: 
                return ""; 
            case XSSFCell.CELL_TYPE_BOOLEAN: 
                return Boolean.toString(cell.getBooleanCellValue()); 
            case XSSFCell.CELL_TYPE_STRING: 
                return cell.getStringCellValue(); 
            case XSSFCell.CELL_TYPE_NUMERIC: 
                Double d = new Double(cell.getNumericCellValue()); 
                long l = d.longValue(); 
                Double compD = (double) l; 
                if (compD.equals(d)) { 
                    return Long.toString(l); 
                } else { 
                    return Double.toString(d); 
                } 
        } 
        return null; 
    } 
 
	
	public static void main(String[] args) throws IOException {
		System.out.println("main 1");
		//writeXLSFile();
		readXLSFile();
		
		//writeXLSXFile();
		//readXLSXFile();

	}
	
	
	
	
	
	
}
