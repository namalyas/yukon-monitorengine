package lk.yukon.servicemonitor.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceMonitorAgent is act as Worker thread for status checking each individual service
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class ServiceMonitorAgent implements Runnable{

    private static final Logger LOGGER= Logger.getLogger(ServiceMonitorAgent.class);

    private ServiceConfigurationManager serviceConfigurationManager;

    public ServiceMonitorAgent(ServiceConfigurationManager serviceConfigurationManager){
        LOGGER.info(String.format("ServiceMonitorAgent(%s) object creation",serviceConfigurationManager));
        this.serviceConfigurationManager=serviceConfigurationManager;
    }

    public void run() {
        LOGGER.info(String.format("Calling method - run()"));
        try{
            LOGGER.info(String.format("Creating socket(%s,%s)",serviceConfigurationManager.getService().getHost(),serviceConfigurationManager.getService().getPort()));
            Socket socket=new Socket(serviceConfigurationManager.getService().getHost(),serviceConfigurationManager.getService().getPort());
            if(socket.isConnected()){
                LOGGER.info(String.format("socket connected"));
                serviceConfigurationManager.markServiceRunning();
            }else{
                LOGGER.info(String.format("socket not connected"));
                serviceConfigurationManager.markServiceNotRunning();
            }
            socket.close();

        }catch (IOException ex){
            LOGGER.error("IOException occured : ",ex);
            serviceConfigurationManager.markServiceNotRunning();

        }catch (Exception ex){
            LOGGER.error("Exception occured : ",ex);
            serviceConfigurationManager.markServiceNotRunning();

        }
    }


    @Override
    public String toString() {
        return "ServiceMonitorAgent{" +
                "serviceConfigurationManager=" + serviceConfigurationManager +
                '}';
    }
}
