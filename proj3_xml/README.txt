PROJECT 3 XML PARSING COMPILE GUIDE
===================================
- on AWS
	& cd /opt/tomcat/webapps/[whatever the war is called]/WEB-INF/lib
- in the directory there should be a runnable jar called "proj3xml.jar" as well as the 3 xml files from the stanford movies directory
	- in order to run the jar enter the following command

		$ java -jar proj3xml.jar

	- the parsing should begin (should take around 20-30 seconds)
	- the parser will only work on AWS since the connection settings (username, password) are set to the moviedb created on AWS 
- go into the mysql database to check to see if data has been added
		
		$ mysql -u root -p
			(password: root1234)
		mysql> use moviedb
			- then enter in a query


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