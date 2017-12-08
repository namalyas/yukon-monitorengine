package lk.yukon.servicemonitor.service;

import junit.framework.TestCase;
import lk.yukon.servicemonitor.model.Service;

import java.util.Date;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceConfigurationManagerTest is used for TDD approach to design and implement some methods in ServiceConfigurationManager class
 * This version used for TDD based implementation of getNextRunningTime() method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class ServiceConfigurationManagerTest extends TestCase {

    /**
     * boundary conditions for getNextRunningTime() method
     *
     * 1)gracePeriod ==0 then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * 2)pollingFrequency==gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * 3)pollingFrequency<gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * 4)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod==lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * 5)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod>lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * 6)pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod<lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+gracePeriod
     *
     * exceptional cases
     * 1) pollingFrequency==0 this covers when ServiceConfigurationManager object creation
     *
     *
     *
     *
     */

    private ServiceConfigurationManager serviceConfigurationManager;

    public void setUp() throws Exception {
        super.setUp();
    }



    public void tearDown() throws Exception {
    }

    /**
     * case1 : gracePeriod ==0 then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase1() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),1,0);
        serviceConfigurationManager.setLastRuningTime(0);
        assertEquals(1000,serviceConfigurationManager.getNextRunningTime());
    }

    /**
     * case2 : pollingFrequency==gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase2() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),2,2);
        serviceConfigurationManager.setLastRuningTime(0);
        assertEquals(2000,serviceConfigurationManager.getNextRunningTime());
    }


    /**
     * case3 : pollingFrequency<gracePeriod then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase3() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),2,3);
        serviceConfigurationManager.setLastRuningTime(0);
        assertEquals(2000,serviceConfigurationManager.getNextRunningTime());
    }



    /**
     * case4 : pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod==lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase4() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setLastRuningTime(0);
        serviceConfigurationManager.setLastRuningtimeWithPollingFrequency(0);
        serviceConfigurationManager.setLastRuningtimeWithGracePeriod(0);
        assertEquals(6000,serviceConfigurationManager.getNextRunningTime());
    }



    /**
     * case5 : pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod>lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+pollingFrequency
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase5() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setLastRuningTime(3000);
        serviceConfigurationManager.setLastRuningtimeWithPollingFrequency(0);
        serviceConfigurationManager.setLastRuningtimeWithGracePeriod(3000);
        assertEquals(6000,serviceConfigurationManager.getNextRunningTime());
    }



    /**
     * case6 : pollingFrequency>gracePeriod && lastRuningtimeWithGracePeriod<lastRuningtimeWithPollingFrequency then return lastRuningtimeWithPollingFrequency+gracePeriod
     * @throws Exception if AssertionError occurs
     */
    public void testGetNextRunningTimeWithCase6() throws Exception {
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setLastRuningTime(6000);
        serviceConfigurationManager.setLastRuningtimeWithPollingFrequency(6000);
        serviceConfigurationManager.setLastRuningtimeWithGracePeriod(3000);
        assertEquals(9000,serviceConfigurationManager.getNextRunningTime());
    }


    /**
     *
     * boundary conditions for isServiceInOutage(currentTimestamp) method
     *
     * 1) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp < serviceOutageStartTime then return false
     * 2) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp > serviceOutageEndTime then return false
     * 3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return true
     *
     *
     * exceptional cases - those cases are allready covers in set methods
     *
     * 1) serviceOutageStartTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
     * 2) serviceOutageEndTime <= 0 then exception throws UnsupportedServiceConfigurationManagerParameterException
     * 3) serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime > serviceOutageEndTime then exception throws UnsupportedServiceConfigurationManagerParameterException
     *
     *
     * /


    /**
     * case1 : serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp < serviceOutageStartTime then return false
     * @throws Exception if AssertionError occurs
     */
    public void testIsServiceInOutageWithCase1() throws Exception {
        long cuttrntTimeStamp=System.currentTimeMillis();
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setServiceOutageStartTime(cuttrntTimeStamp-1000);
        serviceConfigurationManager.setServiceOutageEndTime(cuttrntTimeStamp+1000);
        assertEquals(false,serviceConfigurationManager.isServiceInOutage(cuttrntTimeStamp-2000));
    }



    /**
     * case2 : serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and currentTimestamp > serviceOutageEndTime then return false
     * @throws Exception if AssertionError occurs
     */
    public void testIsServiceInOutageWithCase2() throws Exception {
        long cuttrntTimeStamp=System.currentTimeMillis();
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setServiceOutageStartTime(cuttrntTimeStamp-1000);
        serviceConfigurationManager.setServiceOutageEndTime(cuttrntTimeStamp+1000);
        assertEquals(false,serviceConfigurationManager.isServiceInOutage(cuttrntTimeStamp+2000));
    }



    /**
     * case3 : serviceOutageStartTime > 0 and  serviceOutageEndTime > 0 and serviceOutageStartTime < serviceOutageEndTime and serviceOutageStartTime < currentTimestamp < serviceOutageEndTime  then return true
     * @throws Exception if AssertionError occurs
     */
    public void testIsServiceInOutageWithCase3() throws Exception {
        long cuttrntTimeStamp=System.currentTimeMillis();
        serviceConfigurationManager=new ServiceConfigurationManager(new Service("Google","www.google.com",80),6,3);
        serviceConfigurationManager.setServiceOutageStartTime(cuttrntTimeStamp-1000);
        serviceConfigurationManager.setServiceOutageEndTime(cuttrntTimeStamp+1000);
        assertEquals(true,serviceConfigurationManager.isServiceInOutage(cuttrntTimeStamp));
    }


}