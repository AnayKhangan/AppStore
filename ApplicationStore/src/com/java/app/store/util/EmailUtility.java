package com.java.app.store.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtility {
	
	@SuppressWarnings("static-access")
	public String sendEmail(String email_id_from_form, Constant conf) {
				   
		Properties props = null;
		try {
			props = conf.getProperties();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Authentication auth = new Authentication(conf);
		Session session = Session.getDefaultInstance(props, auth);
   
		try {  
				MimeMessage message = new MimeMessage(session);  
				message.setFrom(new InternetAddress(conf.username));
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(email_id_from_form));  
				message.setSubject(conf.mail_subject);
				String mail_text = conf.setMailText();
				message.setText(mail_text);  
     
				Transport.send(message);  
  
				System.out.println(conf.success_mail+" "+email_id_from_form);
   
		} catch (MessagingException e) {
			return "There was an error: "+e.getMessage();
		}
		return email_id_from_form;  
	}

}
