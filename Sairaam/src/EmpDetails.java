

import java.util.Set;

public class EmpDetails {
	
	public String empEsi = null;
	public String empUAN = null;
	public String empCompany = null;
	
	public String plantName = null;
	
	public String totalDays = null;
	public String getEmpUAN() {
		return empUAN;
	}

	public void setEmpUAN(String empUAN) {
		this.empUAN = empUAN;
	}

	public String empName=null;
    public String days=null;
    public String salary = null;

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getEmpEsi() {
		return empEsi;
	}

	public void setEmpEsi(String empEsi) {
		this.empEsi = empEsi;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public static Set<EmpDetails> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmpCompany() {
		return empCompany;
	}

	public void setEmpCompany(String empCompany) {
		this.empCompany = empCompany;
	}

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(String totalDays) {
		this.totalDays = totalDays;
	}

}
