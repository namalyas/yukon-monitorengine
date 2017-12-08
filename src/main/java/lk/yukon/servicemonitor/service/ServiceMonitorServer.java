package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceMonitorServer is act as Server for checking all services status
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class ServiceMonitorServer extends Thread {

    private static final Logger LOGGER= Logger.getLogger(ServiceMonitorServer.class);

    private boolean isServerRun;
    private ExecutorService executorThreadPool;
    private Map<String,ServiceConfigurationManager> serviceConfigurationManagerMap;
    private long lastRunningTime;

    public ServiceMonitorServer(){
        LOGGER.info(String.format("ServiceMonitorServer() object creation"));
        this.isServerRun=true;
        this.executorThreadPool= Executors.newCachedThreadPool();
        this.serviceConfigurationManagerMap=new HashMap<String, ServiceConfigurationManager>();
        this.lastRunningTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;

    }


    /**
     *
     * @param serviceConfigurationManager Individual service configuration detials contains object
     * @return true or false depending on the service check running eligability
     */
    private boolean isServiceEligibleForCheck(ServiceConfigurationManager serviceConfigurationManager,long currentTimeStamp){
        LOGGER.info(String.format("Calling method - isServiceEligibleForCheck(%s,%s)",serviceConfigurationManager,currentTimeStamp));
        return ((ApplicationConstant.CONSTANT_DEFAULT_ZERO==serviceConfigurationManager.getLastRuningTime())||
                (serviceConfigurationManager.getNextRunningTime(currentTimeStamp)<=lastRunningTime));
    }

    @Override
    public void run() {

        LOGGER.info(String.format("Calling method - run()"));
        while (isServerRun){
            try {
                Thread.sleep(ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            long currentTime=System.currentTimeMillis();
            LOGGER.info(String.format("currentTime : %s",currentTime));
            for(ServiceConfigurationManager serviceConfigurationManager: getServiceConfigurationManagerMap().values()){
                if (isServiceEligibleForCheck(serviceConfigurationManager,currentTime)) {
                    serviceConfigurationManager.updateLastRunningtime(currentTime);
                    executorThreadPool.submit(new ServiceMonitorAgent(serviceConfigurationManager));
                }

            }

            lastRunningTime=currentTime;

        }
    }


    public boolean isServerRun() {
        LOGGER.info(String.format("Calling method - isServerRun()"));
        return isServerRun;
    }

    public void setServerRun(boolean serverRun) {
        LOGGER.info(String.format("Calling method - setServerRun(%s)",serverRun));
        isServerRun = serverRun;
    }

    public ExecutorService getExecutorThreadPool() {
        LOGGER.info(String.format("Calling method - getExecutorThreadPool()"));
        return executorThreadPool;
    }

    public void setExecutorThreadPool(ExecutorService executorThreadPool) {
        LOGGER.info(String.format("Calling method - setExecutorThreadPool(%s)",executorThreadPool));
        this.executorThreadPool = executorThreadPool;
    }



    public long getLastRunningTime() {
        LOGGER.info(String.format("Calling method - getLastRunningTime()"));
        return lastRunningTime;
    }

    public void setLastRunningTime(long lastRunningTime) {
        LOGGER.info(String.format("Calling method - setLastRunningTime(%s)",lastRunningTime));
        this.lastRunningTime = lastRunningTime;
    }

    public void stopServer(){
        LOGGER.info(String.format("Calling method - stopServer()"));
        isServerRun=false;
    }

    public ServiceConfigurationManager addServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
        LOGGER.info(String.format("Calling method - addServiceConfigurationManager(%s)",serviceConfigurationManager));
       return serviceConfigurationManagerMap.put(serviceConfigurationManager.getService().getServiceUID(),serviceConfigurationManager);
    }

    public ServiceConfigurationManager removeServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
        LOGGER.info(String.format("Calling method - removeServiceConfigurationManager(%s)",serviceConfigurationManager));
        return serviceConfigurationManagerMap.remove(serviceConfigurationManager.getService().getServiceUID());
    }

    public Map<String, ServiceConfigurationManager> getServiceConfigurationManagerMap() {
        LOGGER.info(String.format("Calling method - getServiceConfigurationManagerMap()"));
        return serviceConfigurationManagerMap;
    }

    public void setServiceConfigurationManagerMap(Map<String, ServiceConfigurationManager> serviceConfigurationManagerMap) {
        LOGGER.info(String.format("Calling method - setServiceConfigurationManagerMap(%s)",serviceConfigurationManagerMap));
        this.serviceConfigurationManagerMap = serviceConfigurationManagerMap;
    }

    @Override
    public String toString() {
        return "ServiceMonitorServer{" +
                "isServerRun=" + isServerRun +
                ", executorThreadPool=" + executorThreadPool +
                ", serviceConfigurationManagerMap=" + serviceConfigurationManagerMap +
                ", lastRunningTime=" + lastRunningTime +
                '}';
    }
}
