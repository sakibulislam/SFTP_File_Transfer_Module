# SFTP File Transfer Module

# Key Features

- [x] Transfer any files (.csv/.txt/.pdf/.xls etc.) with any size from one server to another easily (Prerequisite is node to node connectivity)

  

- [x] Deploy easily in any  server by using the java jar

  

- [x] Dynamic application by using properties parameter

  

- [x] SFTP file transferring in scheduled time automatically with no hassle by add this jar in cronjob

# Set up and run the project

  

1. Clone the project

2. Open the project in any IDE (Eclipse/IntelliJ IDEA)

3. Add the external library jsch-0.1.55 from 'lib' project directory

4. Select JAVA - jdk1.8 as compiler

5. Update app.properties file based on your requirements

6. Update the file naming formatting logic from SftpTransfer.java based on the source file name.
> Existing codebase file naming format: [Select Prefix File Name From Properties File] + current yyyyMMdd + [Select Suffix File Name From Properties File] `e.g if filename = Sample_20211014.txt here fileNamePrefix= Sample_; current yyyyMMdd= 20211014; fileNameSuffix=.txt)`

7. Export the project as Runnable JAR file.

8. Deploy the Java JAR in Linux server simply putting the JAR and app.properties file

9. Run the Java JAR file command: java -jar your_jar_name.jar

10. Automate by scheduling the application adding this JAR in cronjob

  
  

# Configurations (app.properties)

  

logDirectory=*******

  

sourceDirectory=*******

  

destDirectory=*******

  

fileNamePrefix=*******

  

fileNameSuffix=*******

  

user=*******

  

destinationIp=*******

  

password=*******

    

# External Libraries

- jsch-0.1.55

  



  


