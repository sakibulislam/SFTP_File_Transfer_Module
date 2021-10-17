package sftpTransfer;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.*;  
import java.io.*; 

public class SftpTransfer {

	public static void main(String[] args) {


		JSch jsch = new JSch();
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
//		20210601
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		
        LocalDateTime now = LocalDateTime.now();
        
        System.out.println("Current Date: " + dtf.format(now));

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        
		try (InputStream input = new FileInputStream("app.properties")){
			
			Properties prop = new Properties();
			
			// load a properties file
            prop.load(input);
            
            // get the property value and print it out
            System.out.println("logDir:" + prop.getProperty("logDirectory"));
            System.out.println("user:" + prop.getProperty("user"));
            System.out.println("pass:" + prop.getProperty("password"));
            System.out.println("destIP:" + prop.getProperty("destinationIp"));
            System.out.println("destDir:" + prop.getProperty("destDirectory"));
            System.out.println("sourceDir:" + prop.getProperty("sourceDirectory"));
            System.out.println("fileNamePrefix:" + prop.getProperty("fileNamePrefix"));
            System.out.println("fileNameSuffix:" + prop.getProperty("fileNameSuffix"));
            
			/******* Log directory *******/
			
			fh = new FileHandler(prop.getProperty("logDirectory") + dtf.format(now) + ".log");
			
			
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
			/******* SFTP channel connection *******/
            
            session = jsch.getSession(prop.getProperty("user"), prop.getProperty("destinationIp")); 
            session.setPassword(prop.getProperty("password"));                     
            
			System.out.println("Session created....");
			
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			System.out.println("shell channel connected.....");
			channelSftp = (ChannelSftp)channel;
			
			/******* file transfer *******/
			
			/******* destination directory *******/
			
			String destDir = prop.getProperty("destDirectory"); 
			
			System.out.println("dest dir:" + destDir);
			
			channelSftp.cd(destDir);
			
			/******* source directory **********/
			
			File rootFolder = new File(prop.getProperty("sourceDirectory")); 
			
			System.out.println("source dir:" + rootFolder);

			
			//file formatting pattern logic
			for (File file: rootFolder.listFiles()) {
				if (file.getName().contains(prop.getProperty("fileNamePrefix")+ dtf.format(now) + prop.getProperty("fileNameSuffix"))) {
					System.out.println(file.getName() + " file transferring....");
					FileInputStream fis = new FileInputStream(file);
					channelSftp.put(fis, file.getName());
					System.out.println(file.getName() + " file transferred successfully to remote server.");
					logger.info(file.getName() + " file transferred successfully to remote server.");
					fis.close();
					System.out.println("FileInputStream closed...");
				}
			}
			
			for (Handler h : logger.getHandlers()) {
				h.close();
				System.out.println("log filehandler closed...");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exception occured: " + e.getMessage());
		} finally {
			channelSftp.disconnect();
			System.out.println("channelSftp disconnected....");
			session.disconnect();
			System.out.println("session disconnected....");
			channel.disconnect();
			System.out.println("channel disconnected....");
			channelSftp.exit();
			System.out.println("channel exited....");
		}

	}

}
