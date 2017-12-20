 
import org.apache.poi.ss.usermodel.CellValue; 
import org.apache.poi.ss.usermodel.FormulaEvaluator; 
import org.apache.poi.xssf.usermodel.XSSFCell; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import java.io.FileInputStream; 
import java.util.ArrayList; 
 
/**
 * Implementation of {ExcelReader} for .xlsx files 
 */ 
 
public class XlsXReader  { 
 
    XSSFWorkbook workbook; 
 
    String[] sheetNames; 
 
    final FormulaEvaluator evaluator ; 
 
    /**
     * Conversion of arbitrary value types to string 
     * Also reads formula cells 
     * @param cell the cell to read from 
     * @return the value as string 
     */ 
    public  String readCellValueAsString(XSSFCell cell) { 
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
 
    
    
    
    
    private void init(){ 
        ArrayList<String> names = new ArrayList<>(); 
        int numSheets = workbook.getNumberOfSheets(); 
        for ( int i = 0 ; i < numSheets; i++ ){ 
            names.add(  workbook.getSheetName(i) ); 
        } 
        sheetNames = new String[names.size()]; 
        sheetNames = names.toArray( sheetNames ); 
    } 
 
    /**
     * Creates an instance of the ExcelReaderXLSX 
     * 
     * @param fileName the excel xsl file to read from 
     */ 
    public XlsXReader(String fileName) { 
        
    	
    	try { 
            workbook = new XSSFWorkbook(new FileInputStream(fileName)); 
            evaluator = workbook.getCreationHelper().createFormulaEvaluator(); 
            init(); 
 
        } catch (Exception e) { 
            throw new Error(e); 
        } 
    } 
 
    
    	   
 
 
    public String[] sheets() { 
        return sheetNames ; 
    } 
 
     
    public String value(String sheet, int row, int column) { 
        XSSFCell cell = workbook.getSheet(sheet).getRow(row).getCell(column); 
        return readCellValueAsString(cell); 
    } 
 
   
    public int rowCount(String sheet) { 
        return workbook.getSheet(sheet).getLastRowNum() + 1 ; 
    } 
 
    
    public int columnCount(String sheet) { 
        return workbook.getSheet(sheet).getRow(0).getLastCellNum() ; 
    } 
}