package com.java.app.store.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.java.app.store.entity.Software;
import com.java.app.store.util.DatabaseUtility;



public class SoftwareDao {
	
	Connection c = null;
    Statement stmt = null;
    DatabaseUtility dbUtil = new DatabaseUtility();

	public Map<String,String> getByCategoryName(String categoryName){
	
		Software software = new Software();
			
		try 
    	{
    		c = dbUtil.getConnection();
    		stmt = c.createStatement();
    	}catch ( Exception e ) {
    			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	try {
    		ResultSet rs = stmt.executeQuery( "SELECT name,link_to_update FROM softwares where category_name='"+categoryName+"';" );
			while ( rs.next() ) {
				software.softwares.put(rs.getString("name"), rs.getString("link_to_update"));
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
		
		return software.softwares;
		
	}
	
	public void addSoftware(String name,String uploaded_by,String description,String status,String upload_date,String supported_os,String link,String category_name,String version){
	
		try 
    	{
    		c = dbUtil.getConnection();
    		stmt = c.createStatement();
    	}catch ( Exception e ) {
    			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	String sql = "INSERT INTO softwares("+
            "name, uploaded_by, description, status,upload_date, supported_os, link_to_update, category_name, version)"+
            "VALUES ('"+name+"','"+uploaded_by+"','"+description+"','"+status+"','"+upload_date+"','"+supported_os+"','"+link+"','"+category_name+"','"+version+"');";
    		//System.out.println(sql);
          try {
			stmt.executeUpdate(sql);
			System.out.println("Software entry done in database.");
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
	
	}
	//changeStatus()
}
