package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;

import java.util.ArrayList;
import java.util.List;
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
    private List<ServiceConfigurationManager> serviceConfigurationManagerLists;
    private long lastRunningTime;

    public ServiceMonitorServer(){
        this.isServerRun=true;
        this.executorThreadPool= Executors.newCachedThreadPool();
        this.serviceConfigurationManagerLists=new ArrayList<ServiceConfigurationManager>();
        this.lastRunningTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;

    }


    /**
     *
     * @param serviceConfigurationManager Individual service configuration detials contains object
     * @return true or false depending on the service check running eligability
     */
    private boolean isServiceEligibleForCheck(ServiceConfigurationManager serviceConfigurationManager){
        return ((serviceConfigurationManager.getLastRuningTime()==0)||
                (serviceConfigurationManager.getNextRunningTime()<=lastRunningTime));
    }

    @Override
    public void run() {

        while (isServerRun){
            long currentTime=System.currentTimeMillis();
            try {
                Thread.sleep(ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for(ServiceConfigurationManager serviceConfigurationManager:serviceConfigurationManagerLists){
                if (isServiceEligibleForCheck(serviceConfigurationManager)) {
                    serviceConfigurationManager.updateLastRuningtime();
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

    public List<ServiceConfigurationManager> getServiceConfigurationManagerLists() {
        return serviceConfigurationManagerLists;
    }

    public void setServiceConfigurationManagerLists(List<ServiceConfigurationManager> serviceConfigurationManagerLists) {
        this.serviceConfigurationManagerLists = serviceConfigurationManagerLists;
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

    public void addServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
        serviceConfigurationManagerLists.add(serviceConfigurationManager);
    }

    public void removeServiceConfigurationManager(ServiceConfigurationManager serviceConfigurationManager){
        serviceConfigurationManagerLists.remove(serviceConfigurationManager);
    }


    @Override
    public String toString() {
        return "ServiceMonitorServer{" +
                "isServerRun=" + isServerRun +
                ", executorThreadPool=" + executorThreadPool +
                ", serviceConfigurationManagerLists=" + serviceConfigurationManagerLists +
                ", lastRunningTime=" + lastRunningTime +
                '}';
    }
}
