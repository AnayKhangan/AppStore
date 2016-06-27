package com.java.app.store.util;
	
	import java.io.File;
	import java.io.IOException;

	import javax.swing.JOptionPane;

import org.apache.commons.fileupload.FileItem;

import com.amazonaws.AmazonClientException;
	import com.amazonaws.AmazonServiceException;
	import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
	import com.amazonaws.regions.Region;
	import com.amazonaws.regions.Regions;
	import com.amazonaws.services.s3.AmazonS3;
	import com.amazonaws.services.s3.AmazonS3Client;
	import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.Download;
import com.amazonaws.services.s3.transfer.TransferManager;
	import com.amazonaws.services.s3.transfer.Upload;
	import com.amazonaws.util.StringUtils;

	public class AWSService {
		
		//private static AWSCredentials credentials = null;
	    private static TransferManager tranferManager;
	    private static String bucketName;
	    static AmazonS3 s3 = null;
	    
	    private Upload upload;
	    private Download download;
	    
	    public AWSService(){
	    	
	    	/*try {
	            credentials = new ProfileCredentialsProvider("default").getCredentials();
	        } catch (Exception e) {
	            throw new AmazonClientException(
	                    "Cannot load the credentials from the credential profiles file. " +
	                    "Please make sure that your credentials file is at the correct " +
	                    "location (C:\\Users\\GS-0975\\.aws\\credentials), and is in valid format.",
	                    e);
	        }

	        s3 = new AmazonS3Client(credentials);*/
	    	s3 = new AmazonS3Client(new InstanceProfileCredentialsProvider());
	        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	        s3.setRegion(usWest2);
	        tranferManager = new TransferManager(s3);
	    	
	        bucketName = "application-store-version-3";
	        
	        try {
	            if (tranferManager.getAmazonS3Client().doesBucketExist(bucketName) == false) {
	            	tranferManager.getAmazonS3Client().createBucket(bucketName);
	            }
	        } catch (AmazonClientException ace) {
	            System.out.println("Unable to create a new Amazon S3 bucket: " + ace.getMessage());
	        }
	    }
	    
		
		public void createBucket(){
				
		}
		
		public void uploadOnS3(FileItem fileToUpload){
			System.out.println("In upload..");
			bucketName = "application-store-version-3";
	    try {
			PutObjectRequest request = new PutObjectRequest(
			    bucketName, fileToUpload.getName(), fileToUpload.getInputStream(), null);
			System.out.println("Starting upload..");
	    	System.out.println(fileToUpload.getName());
	        upload = tranferManager.upload(request);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    	
	    	//s3.putObject(bucketName, fileToUpload.getName(), fileToUpload., arg3);
	        try {
				upload.waitForCompletion();
			} catch (AmazonServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AmazonClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("Finishing upload..");
	        
	    }
		
		public boolean doesObjectExists(String objectName){
			
	        bucketName = "application-store-version-3";
			
			if(s3.doesObjectExist(bucketName, objectName)){
	    		return true; 
	    	}
			
			return false;
			
		}
		
		public File downloadFromS3(String key){
			
			bucketName = "application-store-version-3";
			File downloadedFile = new File(key);
			//S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
			download = tranferManager.download(new GetObjectRequest(bucketName, key), downloadedFile);
			try {
				download.waitForCompletion();
			} catch (AmazonServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AmazonClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return downloadedFile;
			
		}

	}
	
