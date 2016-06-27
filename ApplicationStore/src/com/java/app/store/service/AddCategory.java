package com.java.app.store.service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.app.store.dao.CategoryDao;

@WebServlet("/AddCategory")
public class AddCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		Date date = new Date();
		String myDate =	new SimpleDateFormat("yyyy-MM-dd").format(date);
		CategoryDao categoryDao = new CategoryDao();
		String gsid = null;
		HttpSession session=request.getSession(false);  
        if(session!=null)
        {  
        	gsid=(String)session.getAttribute("gsid");
        }
        else
        {
        	response.sendRedirect("/V2/index.html");
        }
        
        categoryDao.addCategory(category, gsid, "\\", description, myDate, myDate);
        
	}

}
