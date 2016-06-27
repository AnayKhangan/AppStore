package com.java.app.store.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.java.app.store.entity.UserData;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String json = null;
    	LDAPAuthentication lDAPAuthentication = new LDAPAuthentication();
    	Map<String,String> userDetails = new HashMap<String,String>();
    	
	    UserData userData = new UserData();
	    JSONParser parser = new JSONParser();
	    
	    String gsid = request.getParameter("gsid");
	    String password = request.getParameter("password");
	    
	    if(lDAPAuthentication.isUserAuthorized(gsid, password)) {
	    	
	    	HttpSession session = request.getSession();  
	        session.setAttribute("gsid",gsid); 
	        userDetails = (HashMap) this.getServletConfig().getServletContext().getAttribute("user_db");
	    	
	    	String role = userDetails.get(gsid);
	    	userData.setGsid(gsid);
	        userData.setRole(role);
	    	json = new Gson().toJson(userData);
	        
	    	response.setContentType("application/json");
	        response.getWriter().write(json);
	    }
	    else {
	    	userData.setGsid(gsid);
	        userData.setRole("none");
	        json = new Gson().toJson(userData);
	        System.out.println(json);
	        response.setContentType("application/json");
	        response.getWriter().write(json);
	    }
    }
}
