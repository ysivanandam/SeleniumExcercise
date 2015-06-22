Readme:
======

Concepts covered in the Project:
===============================
1. Maven Project
2. Page Object Model oriented scripting
3. Log4j html logs
4. JUnit Driven Execution
5. Capture Screenshot
6. Reading JSON Data 
7. Read/Write from/to INI files


Tested in Browsers (in Windows 7 Professional - Service Pack 1) -------- Jdk 1.7.0_75:
===============================================================
1. Internet Explorer 11.0.9600.17843
2. Firefox 31.0
3. Chrome 43.0.2357.124 m

Steps to follow to execute the tests:
======================================
1. Set the browser as ie or firefox or chrome in <projectroot>/Config.ini

2. Place the drivers in <projectroot>/browserdrivers folder
	For better usage, name the IEDriver as IEDriverServer.exe
	For better usage, name the ChromeDriver as chromedriver.exe
	In Windows, there is no need for Firefox driver to be placed in <projectroot>/browserdrivers folder. Please do the necessary configurations when executing in Mac OS.

3. Log4j setup
	log4j.properties location - <projectroot>/src/main/resources
	Set the path of the log file in log4j.appender.HTML.File
	Default log file location: <projectroot>/logs/ folder

4. JUnit Test Classes Location: com.siva.excercise.testscript.ju.test

5. Run JUnit Test com.siva.excercise.testscript.ju.test.PlaceOrder
	Test1: Submit Order
	Test2: Remove Order and Check for message to user

6. Run JUnit Test com.siva.excercise.testscript.ju.test.UpdateAccount
	Test1: Login->Update FirstName and BillingCountry->Logout
	Test2: Login->Verify FirstName and BillingCountry->Logout

7. Run JUnit Test com.siva.excercise.testscript.ju.test.JSONTest
	Test1: Get the ID of HYATT AUSTIN station
	Test2: Get the address information of HYATT AUSTIN

Successful Log Files based on my execution:
==========================================
1. excercise_chrome_PlaceOrder.html
2. excercise_firefox_PlaceOrder.html
3. excercise_ie_PlaceOrder.html
4. excercise_chrome_UpdateAccount.html
5. excercise_firefox_UpdateAccount.html
6. excercise_ie_UpdateAccount.html
7. excercise_json.html

Note: 
1. All the log files are in the <projectroot>/logs folder for your reference.
2. Please clean/delete the html log files before execution as the results will be appended to the file.


Factors not considered for this project:
=======================================
1. Proxy configuration
2. Grid execution
3. Data from external source like excel, csv, database, xml, etc.,
4. Multi-threaded execution
5. Code Documentation

Issues Faced:
============
1. Sometimes, I see that the application slows down and the browser is in loading status preventing the driver to move forward. This may be due to my system's performance and I did not try in a different machine. I have written the script to stop the test when an unanticipated failure occurs.
