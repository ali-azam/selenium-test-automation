package com.automation.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * this class is responsible for sending the log
 * */
public class SendMail {
	
	/*
	 * this method will take email detail from settings file and will send mail
	 * testng.setOutputDirectory(
	 * AppConstant.TESTNG_REPORT_DIR+setting.getBrowser()+"TestReport/"+setting.getYear()+"/"+setting.getMonth()+"/"+setting.getReportTime()
	 * ); 
	 * //yyyy-MM-dd-hh-mm-ss
	 */
	public static void sendmail(PropertySettings setting) {

		final String userID = setting.getMailFrom(); // Sender User ID
		final String userPass = setting.getMailPassword(); // Sender Password
		final String emailTo = setting.getMailTo();
		final String errorLogFile = AppConstant.ERROR_LOG_PATH;

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userID, userPass);
					}
				});
		try {

			Message message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/html");

			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(emailTo));
			message.setSubject("Hello, this is for testing purpose only...");
			message.setText("Please do not reply,"
					+ "\n\n System generated email.");
			
			Multipart multipart = new MimeMultipart();
			DataSource source = null;
			Map<Integer, String> browsers = setting.getBrowserList();
			
			for (int i = 0;i<browsers.size(); i++) {
				if (!browsers.get(i).equals("")) {
					MimeBodyPart messageBodyPart = new MimeBodyPart();
					messageBodyPart.setHeader("Content-Type", "text/html");
					//source = new FileDataSource(AppConstant.TEST_LOG_PATH+browsers.get(i)+".log");
					source = new FileDataSource(AppConstant.ASSETS_PATH+browsers.get(i)+"TestReport/"+setting.getYear()+"/"+setting.getMonth()+"/"+setting.getReportTime()+"/emailable-report.html");
					messageBodyPart.setDataHandler(new DataHandler(source));
					//messageBodyPart.setFileName(AppConstant.TEST_LOG_PATH+browsers.get(i)+".log");
					//messageBodyPart.setFileName(AppConstant.TESTNG_REPORT_DIR+browsers.get(i)+"TestReport/"+setting.getYear()+"/"+setting.getMonth()+"/"+setting.getReportTime()+"/emailable-report.html");
					messageBodyPart.setFileName(browsers.get(i)+"TestReport/"+setting.getYear()+"/"+setting.getMonth()+"/"+setting.getReportTime()+"/emailable-report.html");
					multipart.addBodyPart(messageBodyPart);
				}
			}
			
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			
			messageBodyPart1.setText(CreateLogger.getFileData(AppConstant.TEST_CACHE_PATH)+"\n\nPlease check the attached log file.");
			multipart.addBodyPart(messageBodyPart1);

			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email successfully sent to: " + emailTo);

		} catch (MessagingException e) {
			String errorLogMsg = "Error reported on: " + new Date()
					+ "\n----------------------------------";
			errorLogMsg += "\nError message: " + e.getMessage() + "\n\n";

			CreateLogger.enterLogData(errorLogFile, errorLogMsg);
			System.out.println("Attachment not sent...!!!\n\nPlease check the '"
							+ errorLogFile + "' for details.");
		}
	}
	
	/*
	 * this method will take email detail from settings file and will send mail
	 */
	public static void sendmail(PropertySettings setting, String subject, String body) {

		final String userID = setting.getMailFrom(); // Sender User ID
		final String userPass = setting.getMailPassword(); // Sender Password
		final String emailTo = setting.getMailTo();
		final String errorLogFile = AppConstant.ERROR_LOG_PATH;

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userID, userPass);
					}
				});
		try {

			Message message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/html");

			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(emailTo));
			message.setSubject(subject);
			message.setText(body);
			
			Multipart multipart = new MimeMultipart();
			
			String folderName = "extentReport";			//Extent-report from TestRailDB
			
			//Extent-report from TestNG Automation
			/*String filePathString = folderName+"/chrome/" + setting.getExtentReportPath();
			String filePathString01 = folderName+"/safari/" + setting.getExtentReportPath();			
			String filePathString02 = folderName+"/firefox/" + setting.getExtentReportPath();*/
			String filePathString = folderName+"/" + setting.getExtentReportPath();
			
			addAttachment(multipart, filePathString);
			/*addAttachment(multipart, filePathString01);
			addAttachment(multipart, filePathString02);*/
			
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			
			ArrayList<String> summaryReportList = setting.getSummaryReportList();
			String summaryReport = "";
			for (int i = 0;i<summaryReportList.size(); i++) {
				 summaryReport += summaryReportList.get(i);
			}
			messageBodyPart1.setText(body+summaryReport+"\n\nPlease check the attached report.");
			//messageBodyPart1.setText(body+CreateLogger.getFileData(AppConstant.TEST_CACHE_PATH)+"\n\nPlease check the attached log file.");			
			multipart.addBodyPart(messageBodyPart1);

			message.setContent(multipart);
				
			Transport.send(message);
			System.out.println("Email successfully sent to: " + emailTo);
			System.out.println();
			
		} catch (MessagingException e) {
			String errorLogMsg = "Error reported on: " + new Date()
					+ "\n----------------------------------";
			errorLogMsg += "\nError message: " + e.getMessage() + "\n\n";

			CreateLogger.enterLogData(errorLogFile, errorLogMsg);
			System.out.println("Attachment not sent...!!!\n\nPlease check the '"
							+ errorLogFile + "' for details.");
		}		
	}
	
	// Attach Individual file
	private static void addAttachment(Multipart multipart, String fileName){
		DataSource source = null;
		File f = new File(AppConstant.ASSETS_PATH + fileName);
		if(f.exists() && !f.isDirectory()) {
			source = new FileDataSource(AppConstant.ASSETS_PATH + fileName);
			BodyPart messageBodyPart = new MimeBodyPart();
			try {
				messageBodyPart.setHeader("Content-Type", "text/html");
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
}