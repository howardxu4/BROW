README
======
December 9, 2015

The BMC ARSystem JavaAPI utility:
------------------------
This is the web application for verify JavaAPI utility using JSP
0. Name your appication (e.g. BROW)
1. Create a directory under {Apache-Tomcat-installed dir}/webapp name as BROW
2. Copy all files under the BROW directory
3. Using URL localhost:8080/BROW/discover.html on browser to check all forms info
4. Using URL localhost:8080/BROW/changeserver.jsp on browser to switch ARServer 
5. Using URL localhost:8080/BROW/Demo on browser for checking servlet (future using)
6. Under classes directory, Command line: java user/RunJavaAPI to run JavaAPI utility
7. Under classes directory, Command line: java user/TestJavaAPI to run test JavaAPI modules


Contains:
--------

WEB-INF ---- classes ---- com ---- mytest ---- ServletDemo.class
        |            |
        |            ---- user ---- JavaAPI.class
        |            |         |
        |            |         ---- UserData.class
        |            |         |
        |            |         ---- ARForm.class
        |            |         |
        |            |         ---- ARField.class
        |            |         |
        |            |         ---- AREntry.class
        |            |         |
        |            |         ---- ARQuery.class
        |            |         |
        |            |         ---- TestJavaAPI.class
        |            |         |
        |            |         ---- RunJavaAPI.class
        |            |
        |            ---- config.properties
        | 
        ---- lib ---- arapi90_build001.jar
        |        |
        |        ---- log4j-1.2.14.jar
        |
	---- src ---- com ---- mytest ---- ServletDemo.java
	|	 |
	|	 ---- user ---- JavaAPI.java
	|		   |
	|		   ---- UserData.java
	|	  	   |
	|	 	   ---- ARForm.java
	|	  	   |
	|	 	   ---- ARField.java
	|	  	   |
	|	 	   ---- AREntry.java
	|	  	   |
	|	 	   ---- ARQuery.java
	|	  	   |
	|	 	   ---- TestJavaAPI.java
	|	  	   |
	|	 	   ---- RunJavaAPI.java
	|	  	   |
	|	 	   ---- README.md
	|
	---- web.xml
	

discover.html
getdata.jsp
getform.jsp
explore.jsp
getinfo.jp
getqids.jsp
getentry.jsp
examine.jsp
getfid.jsp
getfield.jsp
getafes.jsp
getafe.jsp
changeserver.jsp
saveserver.jsp
MyJson.js
MyBase.js
README.txt

Configuration:
-----
* Modify the config.properties file to setup default login information
* Modify the MyBase.js to your application base url
* Using localhost:8080/BROW/changeserver.jsp to dynamic change current login

Note:
----
There may have class not found problem, to resolve that using set CLASSPATH to BMC jar files
