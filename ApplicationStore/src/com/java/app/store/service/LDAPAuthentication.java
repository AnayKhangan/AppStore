package com.java.app.store.service;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;
import javax.servlet.annotation.WebServlet;

import com.java.app.store.util.ActiveDirectory;

@WebServlet("/LDAPAuthentication")
public class LDAPAuthentication {
	
	public boolean isUserAuthorized(String username, String password){
		
		InitialDirContext context = null;
		
		Properties props = new Properties();
	    props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    //props.put(Context.PROVIDER_URL, "ldap://dc1.gslab.com:389");
	    props.put(Context.PROVIDER_URL, "ldap://ec2-52-77-167-180.ap-southeast-1.compute.amazonaws.com:389");
	    //props.put(Context.SECURITY_PRINCIPAL, username+"@gslab.com");
	    props.put(Context.SECURITY_PRINCIPAL, "cn="+username+",ou=users,dc=testgslab,dc=com");
	    props.put(Context.SECURITY_CREDENTIALS, password);//dn user password
	    System.out.println("Searching");
	    
			try {
				context = new InitialDirContext(props);
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Authentication Failed for username.");
				return false;
			}
			System.out.println("Authentication Successful.");
			ActiveDirectory activeDirectory = new ActiveDirectory(context);
	        
	        //Searching
	        NamingEnumeration<SearchResult> result = activeDirectory.searchUser(username,"username", "DC=testgslab,DC=com");
	    try {
	        if(result.hasMore()) {
				SearchResult rs= (SearchResult)result.next();
				Attributes attrs = rs.getAttributes();
				String temp = attrs.get("samaccountname").toString();
				System.out.println("Username	: " + temp.substring(temp.indexOf(":")+1));
				//temp = attrs.get("givenname").toString();*/
				System.out.println("Name         : " + temp.substring(temp.indexOf(":")+1));
				temp = attrs.get("mail").toString();
				System.out.println("Email ID	: " + temp.substring(temp.indexOf(":")+1));
				temp = attrs.get("cn").toString();
				System.out.println("Display Name : " + temp.substring(temp.indexOf(":")+1) + "\n\n"); 
			} else  {
				System.out.println("No search result found!");
			}

			//Closing LDAP Connection
	        activeDirectory.closeLdapConnection();
			return true;
		} 
	    catch (NamingException exception) {
	    	exception.printStackTrace();
			System.out.println("Authentication Failed for attributes.");
			return false;
		}
	    

	}
}
