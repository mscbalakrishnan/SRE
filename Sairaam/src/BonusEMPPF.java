import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import util.DBUtil;
import util.StringUtilCustom;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BonusEMPPF {

	public static String decPF2016 = "D:/SRE/DiwaliPFBonus2017/PFDEC2016.xls";
	public static String janPF2017 = "D:/SRE/DiwaliPFBonus2017/PFJAN2017.xls";
	public static String febPF2017 = "D:/SRE/DiwaliPFBonus2017/PFFEB2017.xls";
	public static String marPF2017 = "D:/SRE/DiwaliPFBonus2017/PFMAR2017.xls";
	public static String aprPF2017 = "D:/SRE/DiwaliPFBonus2017/PFAPR2017.xls";
	public static String mayPF2017 = "D:/SRE/DiwaliPFBonus2017/PFMAY2017.xls";
	public static String junPF2017 = "D:/SRE/DiwaliPFBonus2017/PFJUN2017.xls";
	public static String julPF2017 = "D:/SRE/DiwaliPFBonus2017/PFJUL2017.xls";
	public static String augPF2017 = "D:/SRE/DiwaliPFBonus2017/PFAUG2017.xls";
	
	public static String septPF2017 = "D:/SRE/DiwaliPFBonus2017/PARENTSEPT2017.xls";
	
	public static double totalMandaysCount = 0;
	
	
	
	
	public static double decCount = 0;
	public static double janCount = 0;
	public static double febCount = 0;
	public static double marCount = 0;
	public static double aprCount = 0;
	public static double mayCount = 0;
	public static double juneCount = 0;
	public static double julyCount = 0;
	public static double augCount = 0;
	public static double septCount = 0;
	
	
	public static Map <String,EmpDetails> readXLSFile() throws IOException {
		System.out.println("readXLSFile 1");
		InputStream ExcelFileToRead = new FileInputStream(septPF2017);
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		HSSFSheet sheet = wb.getSheetAt(3);
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(wb);
		HSSFRow row;
		HSSFCell cell;
		
		
		
		List <EmpDetails>empDetailsList = new ArrayList<EmpDetails>();
		Map <String,EmpDetails>empDetailsListMap = new LinkedHashMap<String,EmpDetails>();
		
		Iterator rows = sheet.rowIterator();
		EmpDetails empDetails = null;
		int minRow = 0;
		int maxRow = 241;
		int counter = 0;
		int mapCounter = 0;
		while (rows.hasNext()) {
			
			empDetails = new EmpDetails();
			row = (HSSFRow) rows.next();
			//row = (XSSFRow) rows.next();
			
			if (row.getRowNum() >= minRow && row.getRowNum() <= maxRow) {
				++counter;
				
				Iterator cells = row.cellIterator();
				
				if((row.getCell(2).getCellType()== HSSFCell.CELL_TYPE_STRING)){
					String numValueString = row.getCell(2).getStringCellValue().trim();
					//System.out.println("UAN STR:"+numValueString);
					empDetails.setEmpUAN(numValueString);
					
				}else if(row.getCell(2).getCellType()== HSSFCell.CELL_TYPE_NUMERIC){
					double numValue = new Double(row.getCell(2).getNumericCellValue());
					
					//System.out.println("UAN Double:"+String.valueOf((long) numValue));
					empDetails.setEmpUAN(String.valueOf((long) numValue));
				}else{
					System.out.println("NULL VALUEEEEEEEEEEEEEEEEEEEEEEE"+row.getRowNum());
				}
				
			    String empName = row.getCell(1).getStringCellValue().trim();
			    //System.out.println("empName:"+empName);
				empDetails.setEmpName(empName);
				
				double numDaysValue = new Double(row.getCell(5).getNumericCellValue());
				//System.out.println("numDaysValue:"+numDaysValue);
			
				empDetails.setDays(String.valueOf((double) numDaysValue));	
				//System.out.println(empDetails.getDays());
				
				String plantName = row.getCell(6).getStringCellValue().trim();
				//System.out.println("String:"+plantName);
				empDetails.setPlantName(plantName);				
				
				
				
				
				
				//System.out.println("Plant Name:"+plantName);
				
				
					
				
				}
			
			//empDetailsList.add(empDetails);
			if(null != empDetails.getEmpUAN()){
				++mapCounter;
				String empUAN = empDetails.getEmpUAN().trim();
				
				if(empUAN != null && empUAN.length() > 0){
					//System.out.println(row.getRowNum()+"  :empUAN empUAN:"+empUAN);
					if(empDetailsListMap.containsKey(empUAN)){
						System.out.println((row.getRowNum()+" :Duplicate empUAN:"+empUAN));
					}else{
						empDetailsListMap.put(empUAN, empDetails);
					}
					
				}else if(empUAN != null && empUAN.length() == 0){
					System.out.println((row.getRowNum()+" :Zero Length empUAN:"+empUAN));
				}
					
				
			}
			
				

			}
		
		System.out.println("mapCounter:"+mapCounter);
		 System.out.println("Map size..:"+empDetailsListMap.size());
		 int mapIterCounter = 0;
		 double totalManDays = 0;
		 for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
			{
			 ++mapIterCounter;
			//System.out.println("mapIterCounter:"+mapIterCounter);
			   // System.out.println("Uan KEY..:"+entry.getKey() + "/" + ((EmpDetails)entry.getValue()).getEmpName() + "/" + ((EmpDetails)entry.getValue()).getSalary() +"........INTO...........:" +((EmpDetails)entry.getValue()).getDays());
			    //System.out.println(entry.getKey()+"="+((EmpDetails)entry.getValue()).getDays());
			 System.out.println(entry.getKey());
			 EmpDetails empDetailsTemp = entry.getValue();
			 double days = Double.parseDouble(empDetailsTemp.getDays());
			 totalManDays = totalManDays + days;
			}	
		 System.out.println("mapIterCounter:"+mapIterCounter);
		 System.out.println("totalManDays:"+totalManDays);
		
		return empDetailsListMap;
		}

		
public int addEmpPF(EmpDetails empDetails) {
		
		System.out.println("addEmpPF called...:");
		int result = -1; 
		Connection con = null;
		Statement st = null;
		
		try {

			String empId = StringUtilCustom.NullToEmptyString(empDetails.getEmpUAN());
			String empName = StringUtilCustom.NullToEmptyString(empDetails.getEmpName());
			String empPlant = StringUtilCustom.NullToEmptyString(empDetails.getPlantName());
			String empUAN = StringUtilCustom.NullToEmptyString(empDetails.getEmpUAN());
			String year = "2017";

			
			
			
			con = new DBUtil().connect();

			st = con.createStatement();
			
			

			String sqlQuery = "INSERT INTO SREEMP_MASTER (emp_id, emp_name, emppf_no, empplant_name, year)"
					+ " VALUES ('" + empId + "','"
					+ empName + "','"
					+ empUAN + "','"
					+ empPlant + "','"
					+ year +"')";

			
			System.out.println(sqlQuery);

			result = st.executeUpdate(sqlQuery);

			System.out.println(result);

		} catch (Exception e) {
			System.out.println(e);
		}finally{
			try {
				st.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return result;
	
	
	}  	


public boolean updEmpManDays(EmpDetails empDetails, String mon) {
	System.out.println("updEmpManDays called");
	boolean isUpdatedStatus = false; 
	Connection con = null;
	Statement st = null;

	try {
		
		
		String empName = StringUtilCustom.NullToEmptyString(empDetails.getEmpName());
		String empPlant = StringUtilCustom.NullToEmptyString(empDetails.getPlantName());
		String empUAN = StringUtilCustom.NullToEmptyString(empDetails.getEmpUAN());
		String manDays = StringUtilCustom.NullToEmptyString(empDetails.getDays());
		String year = "2017";
	
		con = new DBUtil().connect();

		st = con.createStatement();
		

		String sqlQuery = "UPDATE sreemp_master SET "
				+ mon + " ='" +manDays
				+"'  where emppf_no='"+empUAN+"'";
				

		
		
		System.out.println(sqlQuery);

		boolean isUpdated = st.execute(sqlQuery);
		if(!isUpdated){
			int updateCount = st.getUpdateCount();
			if(updateCount > 0){
				isUpdatedStatus = true;
			}
			
		}
		

		System.out.println("Records Updated Successfully............:"+st.getUpdateCount());

	} catch (Exception e) {
		System.out.println(e);
	}finally{
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	return isUpdatedStatus;

}	


public boolean updTotalManDays(EmpDetails empDetails) {
	System.out.println("updTotalManDays called");
	boolean isUpdatedStatus = false; 
	Connection con = null;
	Statement st = null;

	try {
			String empUAN = empDetails.getEmpUAN();
			String totalDays = empDetails.getTotalDays();
	
		con = new DBUtil().connect();

		st = con.createStatement();
		

		String sqlQuery = "UPDATE sreemp_master SET total_days = '"+ totalDays +"' where emppf_no='"+empUAN+"'";
		System.out.println(sqlQuery);

		boolean isUpdated = st.execute(sqlQuery);
		if(!isUpdated){
			int updateCount = st.getUpdateCount();
			if(updateCount > 0){
				isUpdatedStatus = true;
			}
			
		}
		

		//System.out.println("Records Updated Successfully............:"+st.getUpdateCount());

	} catch (Exception e) {
		System.out.println(e);
	}finally{
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	return isUpdatedStatus;

}	


public List<String> getUANList() {
	 
	
	System.out.println("getDoctorNames called....");
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	
	List<String> uanList = new ArrayList<String>();

	
	try {

		con = new DBUtil().connect();

		st = con.createStatement();


		String sqlQuery = "SELECT emppf_no FROM SREEMP_MASTER";
		
		

		System.out.println(sqlQuery);

		rs = st.executeQuery(sqlQuery);
		
		
		while(rs.next()){
			
			String uan = rs.getString("emppf_no");
			uanList.add(uan);
			
		}
		
		System.out.println("UAN List Size:"+uanList.size());

	} catch (Exception e) {
		e.printStackTrace();
		//System.out.println(e);
	}finally{
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	return uanList;

}	
	
public List<EmpDetails> getEmpDetailsList() {
	 
	System.out.println("getEmpDetails called....");
	
	Connection con = null;
	Statement st = null;
	ResultSet rs = null;
	EmpDetails empDetails = null;
	List<EmpDetails> empDetailsList = new ArrayList<EmpDetails>();
	double localTotalDayCount = 0;
	try {

		con = new DBUtil().connect();

		st = con.createStatement();

		String sqlQuery = "select emppf_no, emp_name, emp_dec, emp_jan, emp_feb, emp_mar, emp_apr, emp_may, emp_june, emp_july, emp_aug, emp_sept from sreemp_master";

		System.out.println(sqlQuery);

		rs = st.executeQuery(sqlQuery);
		
		while(rs.next()){
			
			empDetails = new EmpDetails();
			String emppf_no = rs.getString("emppf_no");
			String emp_name = rs.getString("emp_name");
			String emp_dec = StringUtilCustom.NullToEmptyString(rs.getString("emp_dec"));
			String emp_jan = StringUtilCustom.NullToEmptyString(rs.getString("emp_jan"));
			String emp_feb = StringUtilCustom.NullToEmptyString(rs.getString("emp_feb"));
			String emp_mar = StringUtilCustom.NullToEmptyString(rs.getString("emp_mar"));
			String emp_apr = StringUtilCustom.NullToEmptyString(rs.getString("emp_apr"));
			String emp_may = StringUtilCustom.NullToEmptyString(rs.getString("emp_may"));
			String emp_june = StringUtilCustom.NullToEmptyString(rs.getString("emp_june"));
			String emp_july = StringUtilCustom.NullToEmptyString(rs.getString("emp_july"));
			String emp_aug = StringUtilCustom.NullToEmptyString(rs.getString("emp_aug"));
			String emp_sept = StringUtilCustom.NullToEmptyString(rs.getString("emp_sept"));
			
			double decDays = StringUtilCustom.StringToDouble(emp_dec);
			double janDays = StringUtilCustom.StringToDouble(emp_jan);
			double febDays = StringUtilCustom.StringToDouble(emp_feb);
			double marDays = StringUtilCustom.StringToDouble(emp_mar);
			double aprDays = StringUtilCustom.StringToDouble(emp_apr);
			double mayDays = StringUtilCustom.StringToDouble(emp_may);
			double juneDays = StringUtilCustom.StringToDouble(emp_june);
			double julyDays = StringUtilCustom.StringToDouble(emp_july);
			double augDays = StringUtilCustom.StringToDouble(emp_aug);
			double septDays = StringUtilCustom.StringToDouble(emp_sept);
			
			double totalDays = decDays + janDays + febDays + marDays + aprDays + mayDays + juneDays + julyDays + augDays + septDays;
			
			decCount = decCount + decDays;
			janCount = janCount + janDays;
			febCount = febCount + febDays;
			marCount = marCount + marDays;
			aprCount = aprCount + aprDays;
			mayCount = mayCount + mayDays;
			juneCount = juneCount + juneDays;
			julyCount = julyCount + julyDays;
			augCount = augCount + augDays;
			septCount = septCount + septDays;
			
			localTotalDayCount = decCount + janCount + febCount + marCount + aprCount + mayCount + juneCount + julyCount + augCount + septCount;
					
				
			
			
			totalMandaysCount = totalMandaysCount + totalDays;
			
			String totalDaysString  = String.valueOf(totalDays);
			
			empDetails.setEmpUAN(emppf_no);
			empDetails.setEmpName(emp_name);
			empDetails.setTotalDays(totalDaysString);
			
			empDetailsList.add(empDetails);
		}
		
		System.out.println("decCount...:"+decCount);
		System.out.println("janCount...:"+janCount);
		System.out.println("febCount...:"+febCount);
		System.out.println("marCount...:"+marCount);
		System.out.println("aprCount...:"+aprCount);
		System.out.println("mayCount...:"+mayCount);
		System.out.println("juneCount...:"+juneCount);
		System.out.println("julyCount...:"+julyCount);
		System.out.println("augCount...:"+augCount);
		System.out.println("septCount...:"+septCount);
		
		
		
		
		System.out.println("totalMandaysCount...:"+totalMandaysCount);
		System.out.println("localTotalDayCount...:"+localTotalDayCount);

	} catch (Exception e) {
		e.printStackTrace();
		//System.out.println(e);
	}finally{
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	return empDetailsList;

}	



	public void addRecord(Map <String,EmpDetails>empDetailsListMap) {
		
		
		List<String> uanList = getUANList();
		int updCount = 0;
		int insCount = 0;
		int totCount = 0;
		
		for (Map.Entry<String,EmpDetails> entry : empDetailsListMap.entrySet())
		{
			EmpDetails empDetails = entry.getValue();
			String uanNO = empDetails.getEmpUAN();
			String mon = "emp_sept";
			if(uanList.contains(uanNO)){
				//update
				++updCount;
				updEmpManDays(empDetails,mon);
				
				System.out.println("UPDATED:########################################:"+uanNO);
			}else{
				++insCount;
				System.out.println("INSERTED:*********************************************************************:"+uanNO);
				addEmpPF(empDetails);
			}
			
			++totCount;
			
		}		
		
		System.out.println("totCount:"+totCount+" insCount:"+insCount+" updCount:"+updCount);
		
	}
	

	public void updateRecord() {
			
		List<EmpDetails> empDetailsList = new BonusEMPPF().getEmpDetailsList();
		
		for (EmpDetails empDetails : empDetailsList) {
			new BonusEMPPF().updTotalManDays(empDetails);
		}		
			
		
	}	
	
	
	public static void main(String[] args) throws Exception{

		//readXLSFile();
		//new BonusEMPPF().addRecord(readXLSFile());
		new BonusEMPPF().updateRecord();
		
		
		

	}

}
