import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

public class textFileCRUD {
	private static final String wageFilename = "D:\\SRE\\Nov2017\\wagetext.txt";
	private static final String pfFileName = "D:\\SRE\\Nov2017\\esitext.txt";
	
	
	
	public List <String> compareList(List<String> pList,List<String> cList ){
		
		List <String> resultList = new ArrayList<String>();
		
		int pCount=pList.size();
		int cCount=cList.size();
		int remCount = 0;
		
		
		System.out.println("PCount Size:"+pCount+" CCount Size:"+cCount);
		for(int i=0; i<pList.size(); i++){
			
			String key = pList.get(i);
			
			if(cList.contains(key)){
				
			}else{
				++remCount;
				System.out.println("Not in cList:"+key);
			}
			
		}
		System.out.println("PCount Size:"+pCount+" CCount Size:"+cCount+" remCount Size:"+remCount);
		
		
		return resultList;
	}
	
	
	
	public  List <String> readText(String fileName){
		BufferedReader br = null;
		FileReader fr = null;
		List <String> dataList = new ArrayList<String>();
		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;
			
			

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				dataList.add(sCurrentLine);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return dataList;
	}

	public  Map <String,String> readTextMap(String fileName){
		BufferedReader br = null;
		FileReader fr = null;
		Map textContentMap = new HashMap<String,String>();
		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;
			
			

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				String[] xtcontent = sCurrentLine.split("=");
				String key1 = xtcontent[0];
				String value1 = xtcontent[1];
				if(null != value1 && value1.length()>0){
					textContentMap.put(key1, value1);	
				}
				
				
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		return textContentMap;
	}	
	

	
//public List <String> compareMap(Map <String,String> pMap,Map <String,String> cMap ){
//		
//		List <String> resultList = new ArrayList<String>();
//		
//		int pCount=pMap.size();
//		int cCount=pMap.size();
//		int remCount = 0;
//		
//		
//		System.out.println("PCount Size:"+pCount+" CCount Size:"+cCount);
//		for(int i=0; i<pMap.size(); i++){
//			
//			String key = pMap.get(i);
//			
//			if(cList.contains(key)){
//				
//			}else{
//				++remCount;
//				System.out.println("Not in cList:"+key);
//			}
//			
//		}
//		System.out.println("PCount Size:"+pCount+" CCount Size:"+cCount+" remCount Size:"+remCount);
//		
//		
//		return resultList;
//	}
		
	
	
	
	public static void main(String[] args) {

		
		textFileCRUD tx = new textFileCRUD();
		List <String> wageList = tx.readText(wageFilename);
		List <String> pfList = tx.readText(pfFileName);
		
		tx.compareList(wageList, pfList);
		//tx.compareList(pfList, wageList);

	}

}
