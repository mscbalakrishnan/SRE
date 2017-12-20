package util;

public class StringUtilCustom {
	
	
	public static String NullToEmptyString(String inputString) {
		String outputString = "";

		if (null != inputString) {
			if(inputString.length() == 0){
				return outputString;
			}
			return inputString;
		} else {
			return outputString;
		}

	}
	
	public static int StringToInteger(String inputString) {
		int outputNumber = 0;

		if (null != inputString && inputString.length() > 0) {
			outputNumber = Integer.parseInt(inputString);
		} 
		
		return outputNumber;
	}
	
	public static double StringToDouble(String inputString) {
		double outputNumber = 0;

		if (null != inputString && inputString.length() > 0) {
			outputNumber = Double.parseDouble(inputString);
		} 
		
		return outputNumber;
	}	

	public static void main (String ar[]){
		System.out.println("hello");
		String s1=null;
		String s2="";
		String s3="EWQE";
		
		String out = NullToEmptyString(s3);
		
		System.out.println("out string:"+out);
	}	
	
}
