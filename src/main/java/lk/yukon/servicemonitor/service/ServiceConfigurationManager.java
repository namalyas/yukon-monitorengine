package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;
import lk.yukon.servicemonitor.listener.ServiceBehaviourListener;
import lk.yukon.servicemonitor.model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * license: BSD - see LICENSE for details
 *
 * ServiceConfigurationManager is holding the configuration details related to service status checking and some utility methods
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class ServiceConfigurationManager {

    private Service service;
    private int pollingFrequency;
    private int gracePeriod;
    private List<ServiceBehaviourListener> serviceBehaviourListenerList;

    private long lastRuningTime;
    private long lastRuningtimeWithPollingFrequency;
    private long lastRuningtimeWithGracePeriod;


    /**
     *
     * @param service Service entity object
     * @param pollingFrequency frequency of polling action
     * @param gracePeriod waiting time to check once again if service is down
     */
    public ServiceConfigurationManager(Service service, int pollingFrequency, int gracePeriod) {
        this.service = service;
        this.pollingFrequency = pollingFrequency;
        this.gracePeriod = gracePeriod;
        this.serviceBehaviourListenerList=new ArrayList<ServiceBehaviourListener>();
        this.lastRuningTime=0;
        this.lastRuningtimeWithPollingFrequency=0;
        this.lastRuningtimeWithGracePeriod=0;
    }

    /**
     *
     * @param service Service entity object
     * @param pollingFrequency frequency of polling action
     * @param gracePeriod waiting time to check once again if service is down
     * @param serviceBehaviourListenerList list holding the servicebehaviourlisteners
     */
    public ServiceConfigurationManager(Service service, int pollingFrequency, int gracePeriod, List<ServiceBehaviourListener> serviceBehaviourListenerList) {
        this.service = service;
        this.pollingFrequency = pollingFrequency;
        this.gracePeriod = gracePeriod;
        this.serviceBehaviourListenerList = serviceBehaviourListenerList;
        this.lastRuningTime=0;
        this.lastRuningtimeWithPollingFrequency=0;
        this.lastRuningtimeWithGracePeriod=0;
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public int getPollingFrequency() {
        return pollingFrequency;
    }

    public void setPollingFrequency(int pollingFrequency) {
        this.pollingFrequency = pollingFrequency;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public List<ServiceBehaviourListener> getServiceBehaviourListenerList() {
        return serviceBehaviourListenerList;
    }

    public void setServiceBehaviourListenerList(List<ServiceBehaviourListener> serviceBehaviourListenerList) {
        this.serviceBehaviourListenerList = serviceBehaviourListenerList;
    }

    public long getLastRuningTime() {
        return lastRuningTime;
    }

    public void setLastRuningTime(long lastRuningTime) {
        this.lastRuningTime = lastRuningTime;
    }

    public long getLastRuningtimeWithPollingFrequency() {
        return lastRuningtimeWithPollingFrequency;
    }

    public void setLastRuningtimeWithPollingFrequency(long lastRuningtimeWithPollingFrequency) {
        this.lastRuningtimeWithPollingFrequency = lastRuningtimeWithPollingFrequency;
    }

    public long getLastRuningtimeWithGracePeriod() {
        return lastRuningtimeWithGracePeriod;
    }

    public void setLastRuningtimeWithGracePeriod(long lastRuningtimeWithGracePeriod) {
        this.lastRuningtimeWithGracePeriod = lastRuningtimeWithGracePeriod;
    }




    /**
     *
     * @return next service checker running time
     */
    public long getNextRunningTime(){
        if(getGracePeriod()==0 || (getPollingFrequency()<=getGracePeriod())||(getPollingFrequency()>getGracePeriod() && (getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency()))){
            return getLastRuningtimeWithPollingFrequency()+getPollingFrequency()* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR;
        }else{
            return getLastRuningtimeWithPollingFrequency()+getGracePeriod()* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR;
        }

    }

    /**
     * This method is for updating the last runtime according to the next runtime generation logic.
     */
    public void updateLastRuningtime(){
        if(0==getGracePeriod()|| (getPollingFrequency()<=getGracePeriod())||(getPollingFrequency()>getGracePeriod() && (getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency()))){
            setLastRuningtimeWithPollingFrequency(getLastRuningtimeWithPollingFrequency() + getPollingFrequency() * ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
            setLastRuningTime(getLastRuningtimeWithPollingFrequency());
        }else{
            setLastRuningtimeWithGracePeriod(getLastRuningtimeWithPollingFrequency() + getGracePeriod() * ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR);
            setLastRuningTime(getLastRuningtimeWithGracePeriod());
        }
    }


    /**
     *
     * @param serviceBehaviourListener Implementaion of serviceBehaviourListener object to add serviceBehaviourListenerList
     */
    public void addServiceBehaviourListener(ServiceBehaviourListener serviceBehaviourListener){
        serviceBehaviourListenerList.add(serviceBehaviourListener);
    }

    /**
     *
     * @param serviceBehaviourListener serviceBehaviourListener object for removing from serviceBehaviourListenerList
     */
    public void removeServiceBehaviourListener(ServiceBehaviourListener serviceBehaviourListener){
        serviceBehaviourListenerList.remove(serviceBehaviourListener);
    }


    /**
     * This method is for watching service status change (service running) and notify to listeners
     */
    public void markServiceRunning(){
        ServiceRunningStatus servicePreviousRunningStatus=service.getServiceRunningStatus();
        if(!servicePreviousRunningStatus.equals(ServiceRunningStatus.SERVICE_RUNNING_STATUS_RUNNING)){
            service.setServiceRunningStatus(ServiceRunningStatus.SERVICE_RUNNING_STATUS_RUNNING);
            for(ServiceBehaviourListener serviceBehaviourListener:serviceBehaviourListenerList){
                serviceBehaviourListener.serviceUp(service);
            }
        }
    }

    /**
     * This method is for watching service status change (service not running) and notify to listeners
     */
    public void markServiceNotRunning(){
        ServiceRunningStatus servicePreviousRunningStatus=service.getServiceRunningStatus();
        if(0==getGracePeriod()||(getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency())) {
            service.setServiceRunningStatus(ServiceRunningStatus.SERVICE_RUNNING_STATUS_NOT_RUNNING);
            if (!servicePreviousRunningStatus.equals(ServiceRunningStatus.SERVICE_RUNNING_STATUS_NOT_RUNNING)) {
                for (ServiceBehaviourListener serviceBehaviourListener : serviceBehaviourListenerList) {
                    serviceBehaviourListener.serviceDown(service);
                }
            }
        }

    }


    @Override
    public String toString() {
        return "ServiceConfigurationManager{" +
                "service=" + service +
                ", pollingFrequency=" + pollingFrequency +
                ", gracePeriod=" + gracePeriod +
                ", serviceBehaviourListenerList=" + serviceBehaviourListenerList +
                ", lastRuningTime=" + lastRuningTime +
                ", lastRuningtimeWithPollingFrequency=" + lastRuningtimeWithPollingFrequency +
                ", lastRuningtimeWithGracePeriod=" + lastRuningtimeWithGracePeriod +
                '}';
    }
}
