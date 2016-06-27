package com.java.app.store.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.java.app.store.entity.Category;
import com.java.app.store.util.DatabaseUtility;

public class CategoryDao {
	
	Connection c = null;
    Statement stmt = null;
    DatabaseUtility dbUtil = new DatabaseUtility();

	public Map<String,String> getCategories(){
		
		Category category = new Category();
		
		try 
    	{
    		c = dbUtil.getConnection();
    		stmt = c.createStatement();
    	}catch ( Exception e ) {
    			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	try {
    	ResultSet rs = stmt.executeQuery( "SELECT name,link_to_traverse FROM software_category;" );
			while ( rs.next() ) {
				category.categories.put(rs.getString("name"), rs.getString("link_to_traverse"));
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
		return category.categories;
	}
	
	public void addCategory(String name,String created_by,String link,String description,String myDate,String myDate2){
	
	try 
    	{
    		c = dbUtil.getConnection();
    		stmt = c.createStatement();
    	}catch ( Exception e ) {
    			System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
    	String sql = "INSERT INTO software_category("+
                "name, created_by, link_to_traverse, description,creation_date, last_modified)"+
                "VALUES ('"+name+"','"+created_by+"','"+link+"','"+description+"','"+myDate+"','"+myDate2+"');";
          try {
			stmt.executeUpdate(sql);
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
	
	
}
