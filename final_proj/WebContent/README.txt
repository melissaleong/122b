HOW TO COMPILE OUR Project 3 ON AWS
=========================================

1) Export Project 3 as a WAR file from Eclipse
2) Start AWS instance and go to "https://AWS_IP:8443" to access manager app
3) Deploy WAR file onto AWS
4) In the terminal cd to "opt/tomcat/webapps/" and cd to whatever the project was called
5) Inside the project folder, there should be all the html and jsp files as well as two directories: "WEB-INF" and "META-INF"
	- WEB-INF will contain all the java source code of the project as well as a "lib" directory, which contains the mySQL connector and servlet-api jars
6) In order to compile/recompile the project, cd to the WEB-INF/classes directory and enter in the following command:

	javac -classpath "../lib/servlet-api.jar:../lib/mysql-connector-java-5.14.0-bin.jar:" [LIST OF ALL THE FILES YOU WANT TO COMPILE]

7) on the manager app, redeploy the WAR file to get the changes

Tomcat Username and password
============================
User Name: isinger
Password: pi3zza

IP of AWS instance
==================
52.11.120.68


PROJECT 3 XML PARSING COMPILE GUIDE
===================================
- on AWS
	& cd /opt/tomcat/webapps/[whatever the war is called]/WEB-INF/classes/xml_stuff
- in the directory there should be a runnable jar called "proj3xml_FINAL.jar" as well as the 3 xml files from the stanford movies directory
	- in order to run the jar enter the following command

		$ java -jar proj3xml_FINAL.jar

	- the parsing should begin (should take around 20-30 seconds)
	- the parser will only work on AWS since the connection settings (username, password) are set to the moviedb created on AWS 
- go into the mysql database to check to see if data has been added
		
		$ mysql -u root -p
			(password: root1234)
		mysql> use moviedb
			- then enter in a query


- the source code for the parsers should all be in /opt/tomcat/webapps/[whatever the war is called]/WEB-INF/classes
	- they are titled
		ActorParser.java
		CastParser.Java
		movieParser.java
		Proj3_main.java

PERFORMANCE TUNING
==================
1) Hash maps:
	- in order to check if the database already contains a certain movie/genre/actor, we stored the current moviedb data into a hash map
	- this is much faster than constantly running mySQL queries for every single movie/actor/genre because hash maps are O(1) insert
2) CSV file loading:
	- for loading the information into the database, we wrote all the proper data into a csv file
	- from there we did a "LOAD DATA LOCAL INFILE" into the database.
		- this loaded all the data in one go
		- this is much faster than creating a query for each individual insertion
			- instead of "n" insertions ("n" being the number of entries we need to add), we only need to do 1.


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