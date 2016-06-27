package com.java.app.store.util;


import javax.mail.PasswordAuthentication;

public class Authentication extends javax.mail.Authenticator
{
	String username = null;
	String password = null;
	@SuppressWarnings("static-access")
	Authentication(Constant conf)
	{
		this.username = conf.username;
		this.password = conf.password;
	}
	protected PasswordAuthentication getPasswordAuthentication() 
	{  
		return new PasswordAuthentication(username,password);  
	} 

}
