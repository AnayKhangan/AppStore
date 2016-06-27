package com.java.app.store.service;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.java.app.store.dao.CategoryDao;

@WebServlet("/GetCategories")
public class GetCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDao categoryDao = new CategoryDao();
		
		Map<String,String> categories = new HashMap<String,String>();
		try 
    	{
    		categories = categoryDao.getCategories();
			
			System.out.println(categories);
			String json = new Gson().toJson(categories);
			System.out.println(json);
			response.setContentType("application/json");
            response.getWriter().write(json);
		
    	}catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
		
	}

}
