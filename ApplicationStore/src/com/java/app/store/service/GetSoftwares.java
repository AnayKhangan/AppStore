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
import com.java.app.store.dao.SoftwareDao;

@WebServlet("/GetSoftwares")
public class GetSoftwares extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String,String> softwares = new HashMap<String,String>();
		String category = request.getParameter("category");
		SoftwareDao softwareDao = new SoftwareDao();
		try 
    	{
    		softwares = softwareDao.getByCategoryName(category);
			
			System.out.println(softwares);
			String json = new Gson().toJson(softwares);
			System.out.println(json);
			response.setContentType("application/json");
            response.getWriter().write(json);
		
    	}catch ( Exception e ) {
    		System.err.println( e.getClass().getName()+": "+ e.getMessage() );
    	}
		
	}

}
