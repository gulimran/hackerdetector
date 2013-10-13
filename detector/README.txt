Hacker detection service
========================

A service provided to allow customers to sign in using their username and password. This is an automated system to help identify attempts to hack the system and compromise accounts. Activity log files are recorded and this service processes these logs to identify suspicious activity. 

This is a Java program implementing the HackerDetector interface which defines a single method 'parseLine'. The method takes one line of the log file at a time and return the IP address if any suspicious activity is identified or null if the activity appears to be normal. 

The parseline method is called each time a new log line is produced, log lines are passed in chronological order.

The log lines will be in the following format:

ip,date,action,username

IP look like 90.217.8.168
Date is in the epoch format like 1336129471
Action is one of the following: SIGNIN_SUCCESS or SIGNIN_FAILURE
Username is a String like John.Smith

A log line will therefore look like this:

90.217.8.168,133612947,SIGNIN_SUCCESS,John.Smith

The first detection method is to identify a single IP address that has attempted a failed login 5 or more times within a 5 minute period. On detection the service returns the suspicious IP.

The test harness can generate around 100,000 failed signing a day so memory consumption is managed.

Junit is used as testing framework and Mockito is used as mocking framework. No other framework (inversion of control, data stores, caches, etc.) are used.

