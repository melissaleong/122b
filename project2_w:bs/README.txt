HOW TO COMPILE OUR FABFLIX WEBSITE ON AWS
=========================================

1) Export Fabflix project as a WAR file from Eclipse
2) Start AWS instance and go to "http://AWS_IP:8080" to access manager app
3) Deploy WAR file onto AWS
4) In the terminal cd to "opt/tomcat/webapps/" and cd to whatever the project was called
5) Inside the project folder, there should be all the html and jsp files as well as two directories: "WEB-INF" and "META-INF"
	- WEB-INF will contain all the java source code of the project as well as a "lib" directory, which contains the mySQL connector and servlet-api jars
6) In order to compile/recompile the project, cd to the WEB-INF/classes directory and enter in the following command:

	javac -classpath "../lib/servlet-api.jar:../lib/mysql-connector-java-5.14.0-bin.jar" [LIST OF ALL THE FILES YOU WANT TO COMPILE]

7) on the manager app, redeploy the WAR file to get the changes


HOW WE RAN THE ADVANCED SEARCH
=========================================

Using %pattern% in our search, our program runs a query on the database for any string that contains that specific pattern anywhere.