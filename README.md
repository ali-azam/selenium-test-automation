# selenium-test-automation
Test Automation using Selenium and Java

# Test Environment Setup:
•	Java Development Kit (jdk 9)
Download and Install Jdk 9.0.4 for macOS

•	Eclipse
Download and Install Eclipse IDE for Java Developers
https://www.eclipse.org/downloads/

Source preparation:
•	Clone the source and keep it to eclipse workspace
•	Launch eclipse and import the selenium-test-automation project [using General ->Existing Projects into Workspace]
•	Run the project

Configuration:
Before run it is necessary to configure the script. To configure we can consider two types of file as below – 
•	setting.conf [necessary information to execute testcases(TC)
•	testng.xml [we can set which TC and suite will run through this xml]
* In demo source I’ve set default value. No need to set the below configuration Info right now.

Configuration Overview:
config/setting.conf is a property file. This file is containing configuration related information likewise variable=value. Through this file user can easily pass necessary information to the system. System will initialize itself according to this file and execute automation accordingly.
•	browser=chrome	[All Test Case will execute through the browser mentioned here]
•	url= https://www.google.com 	[Target server url for automation]
•	creatorUser=qa_auto2@serverauto.com	[User name for creator]
•	creatorPass=QaAut023	[Password for creator]
•	mailfrom=seqaauto@gmail.com	[From where execution report will mail]
•	mailpass=Sen@se2121	[Password for above from mail]
•	mailto=ali.azam@sebpo.com	[Where execution report will send]
•	waitTime=6	[This value will set depend on internet speed, set lower value for higher internet speed]
•	appletDelete=yes	[System is creating new applet for every test (for yes – created applet will delete | for no – created applet will not delete)]
•	fileDownloadDir=/Users/apple/Downloads/	[File download directory for file download test]
•	testngxml=testng.xml	[Mention the name of xml where TC will mentioned]
