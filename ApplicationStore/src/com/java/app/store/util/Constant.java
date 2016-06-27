package com.java.app.store.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constant {
	
	public static final String root_directory = "gslab";
	public static final int min_memory_size = 1024 * 1024 * 3;  
	public static final int max_file_size = 1024 * 1024 * 60; 
	public static final int max_request_size = 1024 * 1024 * 70; 
	public static final String temp_directory = "java.io.tmpdir";
	
	public static String fieldname = null;
	public static String fieldvalue = null;
	public static String fileName = null;
	public static String filePath = null;
	
	public static final String success_message = "Upload has been done successfully!";
	public static final String success_mail = "Mail sent successfully. ";
	public static final String error_mail = "Sorry. Mail sending failed.";
	public static final String error_message = "There was an error: ";
	
	public static final String username = "Champ.Anay1@gmail.com";
	public static final String password = "vsueghtlfhcgjvyq";
	
	public static final String mail_subject = "File Upload Notification";
	public static String mail_text = "Your File has been successfully uploaded.\n Thank you.";
	
	public static String software_name_from_form = null;
	public static String software_version_from_form = null;
	public static String software_category_from_form = null;
	public static String uploaded_by = null;
	public static String description = null;
	public static String date = null;
	
    
    InputStream inputStream;
   

    public Properties getProperties() throws IOException
    {
	
    	Properties prop = new Properties();
    	String propFileName = "mail.properties";
    	try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			// load a properties file
			prop.load(inputStream);
	 	} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
    }

    public String setMailText()
    {
    	mail_text = "Hi "+uploaded_by+",\n"
    			+"\tYour File has been successfully uploaded.\n"
    			+"\n\tSoftware name:"+software_name_from_form
    			+"\n\tSoftware version:"+software_version_from_form
    			+"\n\tDescription:"+description;
    	return mail_text;
    }

}
