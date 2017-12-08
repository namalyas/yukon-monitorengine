package lk.yukon.servicemonitor.service;

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

    private ServiceConfigurationManager serviceConfigurationManager;

    public ServiceMonitorAgent(ServiceConfigurationManager serviceConfigurationManager){
        this.serviceConfigurationManager=serviceConfigurationManager;
    }

    public void run() {
        try{
            Socket socket=new Socket(serviceConfigurationManager.getService().getHost(),serviceConfigurationManager.getService().getPort());
            if(socket.isConnected()){
                serviceConfigurationManager.markServiceRunning();
            }else{
                serviceConfigurationManager.markServiceNotRunning();
            }
            socket.close();

        }catch (IOException ex){
            serviceConfigurationManager.markServiceNotRunning();

        }catch (Exception ex){
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
