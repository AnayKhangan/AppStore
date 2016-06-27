package com.java.app.store.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.java.app.store.util.DatabaseUtility;


public class UserDao {
	
	Connection c = null;
    Statement stmt = null;
    DatabaseUtility dbUtil = new DatabaseUtility();
	
	Map<String,String> users = new HashMap<String,String>();
	
	public Map<String,String> getUsers(){
		
		try 
    	{
    		c = dbUtil.getConnection();
    		stmt = c.createStatement();
    	}catch ( Exception e ) {
    			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	try {
    	ResultSet rs = stmt.executeQuery( "SELECT * FROM user_authentication;" );
			while ( rs.next() ) {
				users.put(rs.getString("gsid"), rs.getString("role"));
	         }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return users;
		
		
	}

}
