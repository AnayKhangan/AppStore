package com.java.app.store.service;

import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.java.app.store.dao.UserDao;

public class UserDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Map<String, String> db = new HashMap<String, String>();
	
	public void init(ServletConfig config) throws ServletException 
	{
		System.out.println("Loaded with server..");
		UserDao userDao = new UserDao();
	   
	    try {
	       
	         
	         db = userDao.getUsers();
	         
	         config.getServletContext().setAttribute("user_db", db);
	         
	       } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	       }
	    System.out.println("Hello"+db);
	    System.out.println("Loaded database..");
}
	
}
