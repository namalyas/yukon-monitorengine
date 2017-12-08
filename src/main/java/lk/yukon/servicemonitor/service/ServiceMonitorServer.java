package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
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

    private boolean isServerRun;
    private ExecutorService executorThreadPool;
    private Map<String,ServiceConfigurationManager> serviceConfigurationManagerMap;
    private long lastRunningTime;

    public ServiceMonitorServer(){
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
        return ((serviceConfigurationManager.getLastRuningTime()==0)||
                (serviceConfigurationManager.getNextRunningTime(currentTimeStamp)<=lastRunningTime));
    }

    @Override
    public void run() {

        while (isServerRun){
            try {
                Thread.sleep(ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long currentTime=System.currentTimeMillis();
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
        return isServerRun;
    }

    public void setServerRun(boolean serverRun) {
        isServerRun = serverRun;
    }

    public ExecutorService getExecutorThreadPool() {
        return executorThreadPool;
    }

    public void setExecutorThreadPool(ExecutorService executorThreadPool) {
        this.executorThreadPool = executorThreadPool;
    }



    public long getLastRunningTime() {
        return lastRunningTime;
    }

    public void setLastRunningTime(long lastRunningTime) {
        this.lastRunningTime = lastRunningTime;
    }

    public void stopServer(){
        isServerRun=false;
    }

    public ServiceConfigurationManager addServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
       return serviceConfigurationManagerMap.put(serviceConfigurationManager.getService().getServiceUID(),serviceConfigurationManager);
    }

    public ServiceConfigurationManager removeServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
        return serviceConfigurationManagerMap.remove(serviceConfigurationManager.getService().getServiceUID());
    }

    public Map<String, ServiceConfigurationManager> getServiceConfigurationManagerMap() {
        return serviceConfigurationManagerMap;
    }

    public void setServiceConfigurationManagerMap(Map<String, ServiceConfigurationManager> serviceConfigurationManagerMap) {
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
