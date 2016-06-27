package com.java.app.store.util;

import java.util.logging.Logger;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ActiveDirectory {
	// Logger
	private static final Logger LOG = Logger.getLogger(ActiveDirectory.class.getName());

    private DirContext dirContext;
    private SearchControls searchCtls;
	private String[] returnAttributes = { "sAMAccountName","givenName", "cn", "mail" };
    private String baseFilter = "(&((&(objectCategory=Person)(objectClass=User)))";

    
    public ActiveDirectory(InitialDirContext dirContext) {
        
        //default domain base for search
    	this.dirContext = dirContext;
        //initializing search controls
        searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        searchCtls.setReturningAttributes(returnAttributes);
    }
   
    public NamingEnumeration<SearchResult> searchUser(String searchValue, String searchBy, String searchBase) {
    	String filter = getFilter(searchValue, searchBy);    	
    	String base = searchBase;
		try {
			return this.dirContext.search(base, filter, this.searchCtls);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage()); 
		}
		return null;
    }

    public void closeLdapConnection(){
        try {
            if(dirContext != null)
                dirContext.close();
        }
        catch (NamingException e) {
        	LOG.severe(e.getMessage());            
        }
    }
    
   
    private String getFilter(String searchValue, String searchBy) {
    	String filter = this.baseFilter;    	
    	if(searchBy.equals("email")) {
    		filter += "(mail=" + searchValue + "))";
    	} else if(searchBy.equals("username")) {
    		filter += "(samaccountname=" + searchValue + "))";
    	}
		return filter;
	}
    
}
