package com.java.app.store.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtility {
	
	public Connection getConnection() throws Exception{
		
		Connection connection = null;
		try {
    		Class.forName("org.postgresql.Driver");
    		//connection = DriverManager.getConnection("jdbc:postgresql://10.73.80.21:5432/mydb","postgres", "root123");
    		connection = DriverManager.getConnection("jdbc:postgresql://myappstorerds.cz7secwzfzy1.ap-southeast-1.rds.amazonaws.com:5432/mytestdb","postgres", "devops123");
    		System.out.println("Opened database successfully");
    	}
		catch (Exception exception ) {
    		System.err.println(exception.getClass().getName()+": "+ exception.getMessage() );
    		throw exception;
    	}
		return connection;		
	}
}
