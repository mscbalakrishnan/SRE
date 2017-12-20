package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;




public class DBUtil {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sre";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String MAX_POOL = "250";

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public void testDB() {
    	System.out.println("getDoctorDetailsById called");
    	
    	
    	Connection con = null;
    	Statement st = null;
    	ResultSet rs = null;

    	try {

    	
    		con = new DBUtil().connect();

    		st = con.createStatement();

    		String sqlQuery = "SELECT doct_id, doct_name, email, password, address, phone, dept_id from DOCTOR_MASTER ";

    		System.out.println(sqlQuery);

    		rs = st.executeQuery(sqlQuery);
    		
    		while(rs.next()){
    			
    			String docId = (String)rs.getString("doct_id");
    			String docName = (String)rs.getString("doct_name");
    			String email = (String)rs.getString("email");
    			String password = (String)rs.getString("password");
    			String address = (String)rs.getString("address");
    			String phone = (String)rs.getString("phone");
    			String deptId = (String)rs.getString("dept_id");
    			
    			System.out.println("docId..:"+docId);
    			
    		}

    		

    	} catch (Exception e) {
    		System.out.println(e);
    	}finally{
    		try {
    			rs.close();
    			st.close();
    			con.close();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}

    }	    
    
    
    

    
    public static void main (String ar[]){
    	
    	DBUtil dbUtil =  new DBUtil();
    	
    	dbUtil.testDB();
    	
    	//dbUtil.connect();
    	dbUtil.disconnect();
    	
    }
}

