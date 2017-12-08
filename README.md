# yukon-monitorengine
This repo contains service monitor server

# Technical Assignment
Design and implement in (java) a service monitoring class.  This monitor will be used to monitor the status of multiple services.  A service is defined as a host/port combination.  To check if a service is up, the monitor will establish a TCP connection to the host on the specified port.  If a connection is established, the service is up, if the connection is refused, the service is not up.
The monitor will allow callers to register interest in a service, and a polling frequency. The callers will be notified when the service goes up and down.
The monitor should detect multiple callers registering interest in the same service, and should not poll any service more frequently than once a second.
At any time a service can be configured with a planned service outage; however, not all services need to specify an outage. The service outage will specify a start and end time for which no notifications for that service will be delivered.
The monitor should allow callers to define a grace time.  If a service is not responding, the monitor will wait for the grace time to expire before notifying any clients.  If the service goes back on line during this grace time, no notification will be sent.  If the grace time is less than the polling frequency, the monitor should schedule extra checks of the service.
The code should include a set of unit tests, and you are advised to think about implementing good coding practices, design patterns, OOP concepts, and other high-level elements to show your skills as a developer.

# What Was Done
1) Design / Implement set of class according the assignment requirement by following the good OO/OAD concepts.
2) Write couple of unit tests to demonstrate the capabilities of the service monitor server.

# How To Run
1) Since this is a java based maven project you need to install Java and maven correctly
2) Then clone the project from github
3) Then navigate to project folder and execute maven clean install
NOTE: Please check the availability of ServerSocket ports used in ServiceMonitorServerTest class. (currently used 1500 and 1600)

# Project Description
Server capabilities are demonstrate in ServiceMonitorServerTest class. please refer the Planned tests currently executed.
1) Run with  working server connection which will die in 5 sec
2) Run with  not working server connection wait 5 sec and then run 5 sec and then run
3) Attach multiple listeners for same connection
4) Multiple server connections with single listeners
          
If you want you can test with multiple unit tests other than this you can write your own. (To easy refering purposes Logs ralted only this testcase are set to display in the console. other logs store in log files . refer- log4j.properties)
           
Apart from thaServiceConfigurationManagerTest class contains TDD approach unit tetst wich are used to build some complex methods ralted to calculate server next running time and serverice outage time. boundary conditions are as follows.    
Boundary conditions for getNextRunningTime(currentTimeStamp) method not considering the service outage time 

1) gracePeriod ==0 then return lastRuningtimeWithPollingFrequency+pollingFrequency
2) pollingFrequency==gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
3) pollingFrequency<gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
4) pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod==lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
5) pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod>lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
6)  pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod<lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+gracePeriod
           
Boundary conditions for getNextRunningTime(currentTimeStamp) method considering the service outage time          
7) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return serviceOutageEndTime
           
Exceptional cases
1) pollingFrequency==0 this covers when ServiceConfigurationManager object creation        
           
--------------------------------------------------------------------------------------------------------------
Boundary conditions for isServiceInOutage(currentTimestamp) method
1) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp < serviceOutageStartTime then return false</li> 
2) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp > serviceOutageEndTime then return false</li> 
3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return true</li>      
           
Exceptional cases - those cases are already covers in set methods</li>
1) serviceOutageStartTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
2) serviceOutageEndTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime > serviceOutageEndTime then exception throws UnsupportedServiceConfigurationManagerParameterException
           

# What Was Done - Extra
1) ServiceBehaviourAdaptor
2) TDD approach to build complex logics in simplify way
3) Exception hirachi for finer exceptions
4) Eliminate duplicate service addtion by introducing service UUID
5) Adding extra description field for service description
6) Adding extra prototype field for service communication prototype


# Future Enhancements
<ul>Various service protocol types supporting</ul>
<ul>Add more outage times to single service</ul>
<ul>Add service checking priorities</ul>
<ul>Communication port ranage limitation parameters</ul>


