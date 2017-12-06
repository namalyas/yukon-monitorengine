package lk.yukon.servicemonitor.manager;

import junit.framework.TestCase;
import lk.yukon.servicemonitor.model.Service;

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
     *In here I'm covering below boundary conditions for getNextRunningTime() method
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







}