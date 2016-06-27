package com.java.app.store.service;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.java.app.store.dao.SoftwareDao;
import com.java.app.store.util.Constant;
import com.java.app.store.util.EmailUtility;
import com.java.app.store.util.AWSService;;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
 
    private String email_id_from_form = null;
    private String mail_return = null;
    private String uploadPath = null;
    private File uploadDir = null;
    SoftwareDao softwareDao = new SoftwareDao();
    Date date = new Date();
    Constant conf = new Constant();
    String gsid = null;
    AWSService aws = new AWSService();
    
    @SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException 
    {
    	
    	HttpSession session=request.getSession(false);  
        if(session!=null)
        {  
        	gsid=(String)session.getAttribute("gsid");
        }
        else
        {
        	response.sendRedirect("/ApplicationStore/index.html");
        }
    
        if (!ServletFileUpload.isMultipartContent(request)) 
        {
            PrintWriter writer = response.getWriter();
            writer.println("Request does not contain upload data");
            writer.flush();
            return;
        }
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(conf.min_memory_size);
        factory.setRepository(new File(System.getProperty(conf.temp_directory)));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(conf.max_file_size);
        upload.setSizeMax(conf.max_request_size);
        
        //uploadPath = getServletContext().getRealPath("")+ File.separator + conf.root_directory;
        Properties props = null;
		try {
			props = conf.getProperties();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/*uploadPath = props.getProperty("root.directory");
		System.out.println("####"+uploadPath);
        uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) 
            uploadDir.mkdir();*/
        
            List<?> formItems = null;
			try 
			{
				formItems = upload.parseRequest(request);
			} catch (FileUploadException e1) 
			{
				request.setAttribute("message", conf.error_message+"" + e1.getMessage());
				PrintWriter writer = response.getWriter();
	            writer.println("FileUploadException");
	            writer.flush();
	            return;
				//getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
			}
            Iterator<?> iter = formItems.iterator();
            
            while (iter.hasNext()) 
            {
                FileItem item = (FileItem) iter.next();
               
                if (item.isFormField())
                {
                	conf.fieldname = item.getFieldName();
                    conf.fieldvalue = item.getString();
                    if (conf.fieldname.equals("email_id")) 
                    	email_id_from_form = conf.fieldvalue;
                    else if(conf.fieldname.equals("name_of_the_software"))
                    	conf.software_name_from_form = conf.fieldvalue;
                    else if(conf.fieldname.equals("version_of_the_software"))
                    	conf.software_version_from_form = conf.fieldvalue;
                    else if(conf.fieldname.equals("uploaded_by"))
                    	conf.uploaded_by = conf.fieldvalue;
                    else if(conf.fieldname.equals("description"))
                    	conf.description = conf.fieldvalue;
                    else if(conf.fieldname.equals("category_list"))
                    {	conf.software_category_from_form = conf.fieldvalue;
                    	System.out.println(conf.software_category_from_form);
                    }
                    	
                }
                else 
                { 
                	/*uploadPath = uploadPath + File.separator + conf.software_category_from_form;
                	uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) 
                        uploadDir.mkdir();*/
                   
                    conf.fileName = conf.software_name_from_form+"-"+conf.software_version_from_form;
                    /*String file = new File(item.getName()).getName();
                    conf.filePath = file;
                    
                    
                    File storeFile = new File(conf.filePath);*/
                    
                    try 
                    {
                    	if(!aws.doesObjectExists(item.getName()))
                    	{
                    		//item.write(storeFile);
                    		aws.uploadOnS3(item);
    						conf.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
    						softwareDao.addSoftware(conf.fileName, gsid, conf.description, "approved", conf.date, "windows", item.getName(), conf.software_category_from_form,conf.software_version_from_form);
    						EmailUtility email = new EmailUtility();
    			            mail_return = email.sendEmail(email_id_from_form,conf);
    			            if(mail_return.equals(email_id_from_form))
    			            {
    			            	request.setAttribute("message", conf.success_message+" "+conf.success_mail+" To: "+mail_return);
    			            }
    			            response.setContentType("application/json");
                            response.getWriter().write("{\"response\":\"File uploaded successfully on S3.\"}");
    			            	
                    	}
                    	else
                    	{
                    		response.setContentType("application/json");
                            response.getWriter().write("{\"response\":\"File already exists on S3.\"}");
                    	}
						
					} catch (Exception e) 
                    {
						request.setAttribute("message", conf.error_message+"" + e.getMessage());
						PrintWriter writer = response.getWriter();
			            writer.println("AWS Exception");
			            writer.flush();
			            return;
						//getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
					}
                }
            
            }
            
    }




	
	}
    
    