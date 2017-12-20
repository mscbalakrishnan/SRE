
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SRReadExcel2 {

	public static Map <String,EmpDetails> readXLSFile() throws IOException {
		System.out.println("readXLSFile 1");
		InputStream ExcelFileToRead = new FileInputStream("D:/SRE/Nov2017/PARENTNOV2017.xlsx");
		
		
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFRow row;
		XSSFCell cell;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		XSSFSheet sheet = wb.getSheetAt(0);
		
		/*HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFRow row; 
		HSSFCell cell;
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
		HSSFSheet sheet=wb.getSheetAt(0);*/
		
		List <EmpDetails>empDetailsList = new ArrayList<EmpDetails>();
		Map <String,EmpDetails>empDetailsListMap = new LinkedHashMap<String,EmpDetails>();
		Iterator rows = sheet.rowIterator();
		EmpDetails empDetails = null;
		int minRow = 15;
		int maxRow = 54;
		while (rows.hasNext()) {
			empDetails = new EmpDetails();
			//row = (HSSFRow) rows.next();
			row = (XSSFRow) rows.next();
			
			if (row.getRowNum() >= minRow && row.getRowNum() <= maxRow) {
				Iterator cells = row.cellIterator();
				
				
				if((row.getCell(2).getCellType()== HSSFCell.CELL_TYPE_STRING)){
					empDetails.setEmpEsi(row.getCell(2).getStringCellValue().trim());
				}else if(row.getCell(2).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
					double numValue = new Double(row.getCell(2).getNumericCellValue());
					empDetails.setEmpEsi(String.valueOf((long) numValue));
				}
				
				
				empDetails.setEmpName(row.getCell(3).getStringCellValue().trim());
				
				double numDaysValue = new Double(row.getCell(5).getNumericCellValue());
				System.out.println("Days Value: "+numDaysValue);
				empDetails.setDays(String.valueOf((double) numDaysValue));	
				
				double cellDoubleValueSal = evaluator.evaluateInCell(row.getCell(18)).getNumericCellValue();
				
				System.out.println("DDDD ROUND: "+Math.round(cellDoubleValueSal));
				
				
				empDetails.setSalary(String.valueOf(Math.round(cellDoubleValueSal)).toString());
				
				
					
				
				}
			
			//empDetailsList.add(empDetails);
			if(null != empDetails.getEmpEsi()){
				empDetailsListMap.put(empDetails.getEmpEsi(), empDetails);	
			}
			
				

			}
		
		
		 System.out.println("Map size..:"+empDetailsListMap.size());
		 
		 for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
			{
			    System.out.println("ESI KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
			}		
		 
				
		//System.out.println(empDetailsList);
		return empDetailsListMap;
		}

	

	public static void writeXLSFile() throws IOException {

		String excelFileName = "D:/SRE/Sept2017/PFSEPT2017.xls";// name of excel file

		String sheetName = "Sheet1";// name of sheet

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		
		//List<EmpDetails> empDetailsdata = readXLSFile();

		// iterating r number of rows
		for (int r = 0; r < 5; r++) {
			HSSFRow row = sheet.createRow(r);

			// iterating c number of columns
			for (int c = 0; c < 5; c++) {
				HSSFCell cell = row.createCell(c);

				cell.setCellValue("Cell Murali " + r + " " + c);
			}
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
	}
	
	public static void writeExistingXLSFile() throws IOException {

		String excelFileName = "D:/SRE/Nov2017/PFNOV2017.xls";// name of excel file
		System.out.println("writeExistingXLSFile called");
		Map <String,EmpDetails>empDetailsListMap = readXLSFile();
		
		for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
		{
		    System.out.println("ESI KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
		}		

		InputStream ExcelFileToRead = new FileInputStream(excelFileName);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		

		HSSFSheet sheet = wb.getSheetAt(2);
		
		HSSFRow row;
		HSSFCell cell;
		
		int rowMin = 4; int rowMax = 46;
		int counter=0;
		for(int i=rowMin; i<=rowMax; i++){
			
			cell = sheet.getRow(i).getCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			String cellValueStr = null;
			
			
			if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				//System.out.print(cell.getStringCellValue() + " ");
				
				cellValueStr = cell.getStringCellValue().trim();
				System.out.println(i+"...ESI Value .......:"+cellValueStr);
				
			}	
			
			if(null != cellValueStr && cellValueStr.length() > 0){

				EmpDetails empDetails = getEmpDataObject(cellValueStr, empDetailsListMap);
				if(null != empDetails){
					counter++;System.out.println("Count ESI found...:"+counter);
					//System.out.println("ESI Value From Parent Found.......:"+empDetails.getEmpEsi());
					//System.out.println("ESI Value From Parent Found.......:"+empDetails.getEmpName());
					
					cell = sheet.getRow(i).getCell(4);
					System.out.println("Cell Type cell 4...:"+cell.getCellType());
					if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
						cell.setCellValue(empDetails.getDays());
					}
					cell = sheet.getRow(i).getCell(5);
					System.out.println("Cell Type...:"+cell.getCellType());
		
					if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
						cell.setCellValue(empDetails.getSalary());
					}
					
					
					
				}			
				
			}
			
		}
		
		for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
		{
		    System.out.println("ESI KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
		}	
		
		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		// write this workbook to an Outputstream.
		wb.write(fileOut);
		fileOut.flush();
		fileOut.close();
		ExcelFileToRead.close();
	}	

	public static EmpDetails getEmpDataObject(String esiKey, Map <String,EmpDetails> empDetailsListMap) throws IOException {

		EmpDetails empDetailsObj = new EmpDetails();
		System.out.println("BEFORE SIZE:"+empDetailsListMap.size());	
		try{
			
			if(null != empDetailsListMap && null != esiKey && empDetailsListMap.size() > 0){
				
				empDetailsObj = empDetailsListMap.get(esiKey);
				
				if(empDetailsListMap.containsKey(esiKey)){
					empDetailsListMap.remove(esiKey);
				}
				
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("After SIZE:"+empDetailsListMap.size());	
		
		return empDetailsObj;
	}
	
	
	
	
	
	
	public static void readXLSXFile() throws IOException {
		System.out.println("readXLSXFile 1");
		InputStream ExcelFileToRead = new FileInputStream("D:/SRE/Sept2017/PARENTSEPT2017.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

		XSSFWorkbook test = new XSSFWorkbook();

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext()) {
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext()) {
				cell = (XSSFCell) cells.next();

				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + " ");
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					System.out.print(cell.getNumericCellValue() + " ");
				} else {
					// U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}

	}

	
	public static void main(String[] args) throws IOException {
		System.out.println("main 1");
		//readXLSFile();
		//writeXLSFile();
		

		//writeXLSXFile();
		//readXLSXFile();
		
		writeExistingXLSFile();


	}

}
