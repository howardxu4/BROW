README
======
Oct 19, 2015 

The BMC Java API class:
------------------------
* JavaAPI.java  -- Wrapper of BMC Java API ARServerUser class use singleton pattern.
* ARForm.java   -- Contains Form class and common Form operation methods. 
* ARField.java  -- Contains Field class and common Field operation methods.
* AREntry.java  -- Contains Entry class and common Entry operation methods. 
* ARQuery.java  -- Provides general query methods and common usage query methods. 
* TestJavaAPI.java -- JavaAPI test program for each module and web app JSP.
* RunJavaAPI.java -- JavaAPI Utility for basic operations on Forms and Entries 
* config.properties -- configuration data for Java API
* README.md -- this file
* arapi90_build001.jar -- BMC AR API jar
* log4j-1.2.14.jar -- log4j jar

Usage:
-----
* javac -cp "arapi90_build001.jar;." -d classes *.java
* cd classes
* java TestJavaAPI {TESTBIT} [FormName] [QueryString]
* java RunJavaAPI {TASKNUM} {param1} [param2]
* config.properties: modify the value according to the environment

Example:
------
$ java TestJavaAPI
Usage: java TestJavaAPI {TESTBIT} [FormName] [QueryString]
TESTBIT:
     1  - JAVAAPI
     2  - ARFORM
     4  - ARFIELD
     8  - ARENTRY
    16  - ARENTRY
    31  - TESTALL
    32  - SHOWCONST
    64  - GETDATA
   128  - GETFORM
   256  - GETINFO
   512  - GETQIDS
  1024  - GETENTRY
  
$ java RunJavaAPI
Usage: java RunJavaAPI {TASKNUM}  {param1}  [param2]
  TASKNUM:
    1   - export a form to file     {formName}
    2   - import a form from file   {fileName}
    3   - show a form information   {formName}
    4   - show field[s] information {formName}  [fiedID]
    5   - remove a form on server   {formName}
    6   - show form entries info    {formNane}  [qString]
    7   - save entries to file      {formName}  [qString]
    8   - verify entries in file    {fileName}
    9   - operate entries from file {fileName}
  