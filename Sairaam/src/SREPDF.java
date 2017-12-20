
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

public class SREPDF {

	public static Map <String,EmpDetails> readXLSFile() throws IOException {
		System.out.println("readXLSFile 1");
		InputStream ExcelFileToRead = new FileInputStream("D:/SRE/Nov2017/PARENTNOV2017.xlsx");
		/*HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet sheet = wb.getSheetAt(2);
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
		HSSFRow row;
		HSSFCell cell;*/
		
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		XSSFRow row;
		XSSFCell cell;
		XSSFFormulaEvaluator evaluator = new XSSFFormulaEvaluator(wb);
		XSSFSheet sheet = wb.getSheetAt(0);
		
		List <EmpDetails>empDetailsList = new ArrayList<EmpDetails>();
		Map <String,EmpDetails>empDetailsListMap = new LinkedHashMap<String,EmpDetails>();
		
		Iterator rows = sheet.rowIterator();
		EmpDetails empDetails = null;
		int minRow = 15;
		int maxRow = 91;
		while (rows.hasNext()) {
			empDetails = new EmpDetails();
			//row = (HSSFRow) rows.next();
			row = (XSSFRow) rows.next();
			
			if (row.getRowNum() >= minRow && row.getRowNum() <= maxRow) {
				//if (row.getRowNum()==24){
				Iterator cells = row.cellIterator();
				
				if((row.getCell(1).getCellType()== HSSFCell.CELL_TYPE_STRING)){
					empDetails.setEmpUAN(row.getCell(1).getStringCellValue().trim());
				}else if(row.getCell(1).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
					double numValue = new Double(row.getCell(1).getNumericCellValue());
					empDetails.setEmpUAN(String.valueOf((long) numValue));
				}
				
				
			    empDetails.setEmpName(row.getCell(3).getStringCellValue().trim());
				
				double numDaysValue = new Double(row.getCell(5).getNumericCellValue());
				
				empDetails.setDays(String.valueOf((double) numDaysValue));	
				//System.out.println(empDetails.getDays());
				System.out.println("Salary:"+evaluator.evaluateInCell(row.getCell(18)).toString());
				double cellDoubleValueSal = evaluator.evaluateInCell(row.getCell(18)).getNumericCellValue();
				
				System.out.println("DDDD ROUND: "+Math.round(cellDoubleValueSal));
				
				
				empDetails.setSalary(String.valueOf(Math.round(cellDoubleValueSal)).toString());
				
				
					
				
				}
			
			empDetailsList.add(empDetails);
			if(null != empDetails.getEmpUAN()){
				empDetailsListMap.put(empDetails.getEmpUAN(), empDetails);	
			}
			
				

			}
		
		
		 System.out.println("Map size..:"+empDetailsListMap.size());
		 
		 for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
			{
			   // System.out.println("Uan KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
			    System.out.println(entry.getKey()+"="+((EmpDetails)entry.getValue()).getDays());
			}	
				
		
		return empDetailsListMap;
		}

	

	
	
	public static void writeExistingXLSFile() throws IOException {

		String excelFileName = "D:/SRE/Nov2017/PFNOV2017.xls";// name of excel file
		Map <String,EmpDetails>empDetailsListMap = readXLSFile();
		
//		for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
//		{
//		    System.out.println("Uan KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
//		}		

		InputStream ExcelFileToRead = new FileInputStream(excelFileName);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		

		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFRow row;
		HSSFCell cell;
		
		int rowMin = 5; int rowMax = 218;
		int counter=0;
		for(int i=rowMin; i<=rowMax; i++){
			
			System.out.println("Row number:"+sheet.getRow(i).getRowNum());
			cell = sheet.getRow(i).getCell(3);
			if(null != cell ){
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				String cellValueStr = null;
				
				
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + " ");
					
					cellValueStr = cell.getStringCellValue().trim();
					System.out.println("UAN Value .......:"+cellValueStr);
					
				}	
				
				if(null != cellValueStr && cellValueStr.length() > 0){

					EmpDetails empDetails = getEmpDataObject(cellValueStr, empDetailsListMap);
					if(null != empDetails){
						counter++;System.out.println("Count UAN found...:"+counter);
						//System.out.println("ESI Value From Parent Found.......:"+empDetails.getEmpEsi());
						//System.out.println("ESI Value From Parent Found.......:"+empDetails.getEmpName());
						
						cell = sheet.getRow(i).getCell(4);
						System.out.println("Cell Type cell 4...:"+cell.getCellType());
						if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
							cell.setCellValue(empDetails.getDays());
						}
						cell = sheet.getRow(i).getCell(6);
						System.out.println("Cell Type...:"+cell.getCellType());
			
						if(cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
							cell.setCellValue(empDetails.getSalary());
							System.out.println("Cell days...:"+cell.getStringCellValue());
						}
						
						
						
					}			
					
				}				
			}
			
			
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
				
		try{
			
			if(null != empDetailsListMap && null != esiKey && empDetailsListMap.size() > 0){
				
				empDetailsObj = empDetailsListMap.get(esiKey);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return empDetailsObj;
	}
	
	
	
	
	
	



	public static void main(String[] args) throws IOException {
		System.out.println("main 1");
		 //writeXLSFile();
		//readXLSFile();

		//writeXLSXFile();
		// readXLSXFile();
		
		writeExistingXLSFile();
 

	}

}
