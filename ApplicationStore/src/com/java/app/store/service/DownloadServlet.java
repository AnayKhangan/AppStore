package com.java.app.store.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.app.store.util.AWSService;
import com.java.app.store.util.Constant;
 
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        String file = request.getParameter("file");
         Constant conf = new Constant();
         AWSService aws = new AWSService();
        
        //String relativePath = getServletContext().getRealPath("");
        Properties props = null;
		try {
			props = conf.getProperties();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//String relativePath = props.getProperty("root.directory");
        //relativePath = relativePath + File.separator + "gslab" + File.separator + "databases" + File.separator + "Bootcamp_schedule_July_2015.pdf";
        //relativePath = relativePath + file;
        //System.out.println("relativePath = " + file);
        
        File downloadFile = null;
        
        
        if(aws.doesObjectExists(file))
        {
        	downloadFile = aws.downloadFromS3(file);
        	if (downloadFile.exists()){
        	FileInputStream inStream = new FileInputStream(downloadFile);
        	ServletContext context = getServletContext();
            
            
            String mimeType = context.getMimeType(file);
            if (mimeType == null) {        
                
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);
             
            
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());
             
            
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=%s", downloadFile.getName());
            response.setHeader(headerKey, headerValue);
             
            
            OutputStream outStream = response.getOutputStream();
             
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
             
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
             
            inStream.close();
            outStream.close();   
            System.out.println("File Downloaded.");
        }
        }
        else
        {
        	System.out.println("File Downloaded.");
        }
        
        
        
    }
}