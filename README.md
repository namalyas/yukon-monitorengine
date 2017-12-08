# yukon-monitorengine
This repo contains service monitor server

# Technical Assignment
Design and implement in (java) a service monitoring class.  This monitor will be used to monitor the status of multiple services.  A service is defined as a host/port combination.  To check if a service is up, the monitor will establish a TCP connection to the host on the specified port.  If a connection is established, the service is up, if the connection is refused, the service is not up.
The monitor will allow callers to register interest in a service, and a polling frequency. The callers will be notified when the service goes up and down.
The monitor should detect multiple callers registering interest in the same service, and should not poll any service more frequently than once a second.
At any time a service can be configured with a planned service outage; however, not all services need to specify an outage. The service outage will specify a start and end time for which no notifications for that service will be delivered.
The monitor should allow callers to define a grace time.  If a service is not responding, the monitor will wait for the grace time to expire before notifying any clients.  If the service goes back on line during this grace time, no notification will be sent.  If the grace time is less than the polling frequency, the monitor should schedule extra checks of the service.
The code should include a set of unit tests, and you are advised to think about implementing good coding practices, design patterns, OOP concepts, and other high-level elements to show your skills as a developer.

# What Was Done:
1) Design / Implement set of class according the assignment requirement by following the good OO/OAD concepts.
2) Write couple of unit tests to demostrate the capabilities of the service monitor server.

# How To Run
1) Since this is a java based maven project you need to install Java and maven correctly
2) Then clone the project from github
3) Then navigate to project folder and execute maven clean install

NOTE* : Server capabilities are demostate in ServiceMonitorServerTest class. please refer the Planned tests currently executed
          <li>1) run with  working server connection which will die in 5 sec</li>
          <li>2) run with  not working server connection wait 5 sec and then run 5 sec and then run</li>
          <li>3) attach multiple listeners for same connection </li>
          <li>4) multiple server connections with single listeners</li>
          
       If you want you can test with multiple unit tets other than this. (To easy refering purposes Logs ralted only this testcase are set to display in the console. other logs store in log files . refer- log4j.properties)
          
          
      Apart from that ServiceConfigurationManagerTest class contains TDD approach unit tetst wich are used to build some complex methods ralted to calculate server next running time and serverice outage time. boundary conditions are as follows.
      
      boundary conditions for getNextRunningTime(currentTimeStamp) method not considering the service outage time
           
           1)gracePeriod ==0 then return lastRuningtimeWithPollingFrequency+pollingFrequency
           2)pollingFrequency==gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
           3)pollingFrequency<gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
           4)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod==lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
           5)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod>lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
           6)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod<lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+gracePeriod
           
           boundary conditions for getNextRunningTime(currentTimeStamp) method considering the service outage time
           
           7) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return serviceOutageEndTime
           
           exceptional cases
           1) pollingFrequency==0 this covers when ServiceConfigurationManager object creation
           
           
           
           
      boundary conditions for isServiceInOutage(currentTimestamp) method
           
           1) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp < serviceOutageStartTime then return false
           2) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp > serviceOutageEndTime then return false
           3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return true
           
           
           exceptional cases - those cases are allready covers in set methods
           
           1) serviceOutageStartTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
           2) serviceOutageEndTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
           3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime > serviceOutageEndTime then exception throws UnsupportedServiceConfigurationManagerParameterException
           


# future enhansments

<ul>service protocol types added for future enhansments</ul>
<ul>add more outage times to single serverce</ul>
<ul>add service checking priorities</ul>
<ul>its better in the future to limit port range not only port 0</ul>

# extra things did

<ul>ServiceBehaviourAdaptor</ul>
<ul>TDD approach to build complex logics in simplify way</ul>
<ul>Exception hirachi for finer exceptions</ul>
<ul>eleminate duplicate service addtion by introducing service UUID</ul>
<ul>adding extra description for service description</ul>

# task to do

