package lk.yukon.servicemonitor.service;

import junit.framework.TestCase;
import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.listener.ServiceBehaviourListener;
import lk.yukon.servicemonitor.model.Service;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by namal on 12/8/17.
 */
public class ServiceMonitorServerTest extends TestCase {
    private static final Logger LOGGER= Logger.getLogger(ServiceMonitorServerTest.class);
    /**
     *
     * Planned unit tests
     *
     * 1) run with  working server connection which will die in 5 sec
     * 2) run with  not working server connection wait 5 sec and then run 5 sec and then run
     * 3) attach multiple listeners for same connection -
     * 4) multiple server connections with single listeners
     */

    private ServiceMonitorServer serviceMonitorServer;

    /**
     * Inside this setUp() method I created 2 server socket connections
     *  1)Run 5 sec and die
     *  2)wait 5 sec and run 5 sec and then die
     * By using those connections i'm going to writing the above unit tests
     *
     * @throws Exception if Exception occurs
     *
     */

    public void setUp() throws Exception {
        super.setUp();
        LOGGER.info(String.format("------------------- unit start -------------------"));
        Thread serverSocketThreadWithLive5Sec=new Thread(new Runnable() {
            public void run() {
                LOGGER.info(String.format("Running server socket host : %s, port : %s","localhost",1500));
                LOGGER.info(String.format("This server socket listen for 5 sec and then shutdowns"));
                try {
                    ServerSocket serverSocket=new ServerSocket(1500);
                    Thread.sleep(5*ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread serverSocketThreadWithLiveAfter5SecAndDeadIn5Sec=new Thread(new Runnable() {
            public void run() {
                LOGGER.info(String.format("Running server socket host : %s, port : %s","localhost",1600));
                LOGGER.info(String.format("This server socket wait 5 sec and listen for 5 sec and then shutdowns"));
                try {
                    Thread.sleep(5*ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
                    ServerSocket serverSocket=new ServerSocket(1600);
                    Thread.sleep(5*ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        serverSocketThreadWithLive5Sec.start();
        serverSocketThreadWithLiveAfter5SecAndDeadIn5Sec.start();


        serviceMonitorServer=new ServiceMonitorServer();


    }

    public void tearDown() throws Exception {
        serviceMonitorServer.stopServer();
        Thread.sleep(10* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
        LOGGER.info(String.format("------------------- unit end -------------------"));

    }

    /**
     * case1 : run with  working server connection which will die in 5 sec
     * @throws Exception if AssertionError occurs
     */
    public void testWithAllreadyWorkingServerConnection() throws Exception{
        LOGGER.info(String.format("Calling method - testWithAllreadyWorkingServerConnection()"));
        ServiceConfigurationManager serviceConfigurationManager=new ServiceConfigurationManager(new Service("localhost",1500),1,0);
        ServiceBehaviourListener serviceBehaviourListener=new ServiceBehaviourListener() {
            public void serviceUp(Service service) {
                LOGGER.info(String.format("======== Service is up and running : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }

            public void serviceDown(Service service) {
                LOGGER.info(String.format("======== Service is down : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }
        };


        serviceConfigurationManager.addServiceBehaviourListener(serviceBehaviourListener);

        serviceMonitorServer.addServiceConfigurationManager(serviceConfigurationManager);
        serviceMonitorServer.start();
        Thread.sleep(10* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
    }



    /**
     * case2 : run with  not working server connection wait 5 sec and then run 5 sec and then run
     * @throws Exception if AssertionError occurs
     */
    public void testWithAllreadyNotWorkingServerConnection() throws Exception{
        LOGGER.info(String.format("Calling method - testWithAllreadyNotWorkingServerConnection()"));
        ServiceConfigurationManager serviceConfigurationManager=new ServiceConfigurationManager(new Service("localhost",1600),1,0);
        ServiceBehaviourListener serviceBehaviourListener=new ServiceBehaviourListener() {
            public void serviceUp(Service service) {
                LOGGER.info(String.format("======== Service is up and running : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }

            public void serviceDown(Service service) {
                LOGGER.info(String.format("======== Service is down : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }
        };


        serviceConfigurationManager.addServiceBehaviourListener(serviceBehaviourListener);

        serviceMonitorServer.addServiceConfigurationManager(serviceConfigurationManager);
        serviceMonitorServer.start();
        Thread.sleep(10* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
    }



    /**
     * case3 : attach multiple listeners for same connection
     * @throws Exception if AssertionError occurs
     */
    public void testWithMultimpleListeners() throws Exception{
        LOGGER.info(String.format("Calling method - testWithMultimpleListeners()"));
        ServiceConfigurationManager serviceConfigurationManager=new ServiceConfigurationManager(new Service("localhost",1600),1,0);
        ServiceBehaviourListener serviceBehaviourListenerA=new ServiceBehaviourListener() {
            public void serviceUp(Service service) {
                LOGGER.info(String.format("======== Listener A - Service is up and running : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }

            public void serviceDown(Service service) {
                LOGGER.info(String.format("======== Listener A - Service is down : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }
        };

        ServiceBehaviourListener serviceBehaviourListenerB=new ServiceBehaviourListener() {
            public void serviceUp(Service service) {
                LOGGER.info(String.format("======== Listener B - Service is up and running : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }

            public void serviceDown(Service service) {
                LOGGER.info(String.format("======== Listener B - Service is down : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }
        };

        serviceConfigurationManager.addServiceBehaviourListener(serviceBehaviourListenerA);
        serviceConfigurationManager.addServiceBehaviourListener(serviceBehaviourListenerB);

        serviceMonitorServer.addServiceConfigurationManager(serviceConfigurationManager);
        serviceMonitorServer.start();
        Thread.sleep(10* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
    }


    /**
     * case4 : multiple server connections with single listeners
     * @throws Exception if AssertionError occurs
     */
    public void testWithMultipleServerConnectinonWithSingleListener() throws Exception{
        LOGGER.info(String.format("Calling method - testWithMultipleServerConnectinonWithSingleListener()"));
        ServiceConfigurationManager serviceConfigurationManagerA=new ServiceConfigurationManager(new Service("localhost",1500),1,0);
        ServiceConfigurationManager serviceConfigurationManagerB=new ServiceConfigurationManager(new Service("localhost",1600),1,0);
        ServiceBehaviourListener serviceBehaviourListener=new ServiceBehaviourListener() {
            public void serviceUp(Service service) {
                LOGGER.info(String.format("======== Service is up and running : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }

            public void serviceDown(Service service) {
                LOGGER.info(String.format("======== Service is down : host : %s , port :%s ======== ",service.getHost(),service.getPort()));
            }
        };


        serviceConfigurationManagerA.addServiceBehaviourListener(serviceBehaviourListener);
        serviceConfigurationManagerB.addServiceBehaviourListener(serviceBehaviourListener);

        serviceMonitorServer.addServiceConfigurationManager(serviceConfigurationManagerA);
        serviceMonitorServer.addServiceConfigurationManager(serviceConfigurationManagerB);
        serviceMonitorServer.start();
        Thread.sleep(10* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
    }

}