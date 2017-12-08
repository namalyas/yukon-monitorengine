package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;
import lk.yukon.servicemonitor.exception.UnsupportedServiceConfigurationManagerParameterException;
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

    private long serviceOutageStartTime;
    private long serviceOutageEndTime;


    /**
     *
     * @param service Service entity object
     * @param pollingFrequency frequency of polling action
     * @param gracePeriod waiting time to check once again if service is down
     */
    public ServiceConfigurationManager(Service service, int pollingFrequency, int gracePeriod) throws UnsupportedServiceConfigurationManagerParameterException {
        if(null==service||ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            throw new UnsupportedServiceConfigurationManagerParameterException("service :"+service+", pollingFrequency :"+pollingFrequency);
        }else{
            this.service = service;
            this.pollingFrequency = pollingFrequency;
            this.gracePeriod = gracePeriod;
            this.serviceBehaviourListenerList=new ArrayList<ServiceBehaviourListener>();
            this.lastRuningTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.lastRuningtimeWithPollingFrequency=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.lastRuningtimeWithGracePeriod=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.serviceOutageStartTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.serviceOutageEndTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
        }


    }

    /**
     *
     * @param service Service entity object
     * @param pollingFrequency frequency of polling action
     * @param gracePeriod waiting time to check once again if service is down
     * @param serviceBehaviourListenerList list holding the servicebehaviourlisteners
     */
    public ServiceConfigurationManager(Service service, int pollingFrequency, int gracePeriod, List<ServiceBehaviourListener> serviceBehaviourListenerList) throws UnsupportedServiceConfigurationManagerParameterException{
        if(null==service||ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            throw new UnsupportedServiceConfigurationManagerParameterException("service :"+service+", pollingFrequency :"+pollingFrequency);
        }else {
            this.service = service;
            this.pollingFrequency = pollingFrequency;
            this.gracePeriod = gracePeriod;
            this.serviceBehaviourListenerList = serviceBehaviourListenerList;
            this.lastRuningTime = ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.lastRuningtimeWithPollingFrequency = ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.lastRuningtimeWithGracePeriod = ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.serviceOutageStartTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
            this.serviceOutageEndTime=ApplicationConstant.CONSTANT_DEFAULT_ZERO;
        }
    }


    public Service getService() {
        return service;
    }

    public void setService(Service service) throws UnsupportedServiceConfigurationManagerParameterException {
        if(null==service){
            throw new UnsupportedServiceConfigurationManagerParameterException("service :"+service);
        }else {
            this.service = service;
        }
    }

    public int getPollingFrequency() {
        return pollingFrequency;
    }

    public void setPollingFrequency(int pollingFrequency) throws UnsupportedServiceConfigurationManagerParameterException{
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            throw new UnsupportedServiceConfigurationManagerParameterException("pollingFrequency :"+pollingFrequency);
        }else {
            this.pollingFrequency = pollingFrequency;
        }
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


    public long getServiceOutageStartTime() {
        return serviceOutageStartTime;
    }

    public void setServiceOutageStartTime(long serviceOutageStartTime) throws UnsupportedServiceConfigurationManagerParameterException {
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO>=serviceOutageStartTime){
            throw new UnsupportedServiceConfigurationManagerParameterException("serviceOutageStartTime :"+serviceOutageStartTime);
        }else{
            this.serviceOutageStartTime = serviceOutageStartTime;
        }

    }

    public long getServiceOutageEndTime() {
        return serviceOutageEndTime;
    }

    public void setServiceOutageEndTime(long serviceOutageEndTime) throws UnsupportedServiceConfigurationManagerParameterException{
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO>=serviceOutageEndTime || getServiceOutageStartTime()>= serviceOutageEndTime){
            throw new UnsupportedServiceConfigurationManagerParameterException("serviceOutageStartTime :"+getServiceOutageStartTime() +"serviceOutageEndTime :"+serviceOutageEndTime);
        }else {
            this.serviceOutageEndTime = serviceOutageEndTime;
        }
    }

    /**
     *
     * @param currentTimestamp current time stamp
     * @return true if serviceOutageStartTime < currentTimestamp < serviceOutageEndTime
     */
    public boolean isServiceInOutage(long currentTimestamp) {
        if(getServiceOutageStartTime() > ApplicationConstant.CONSTANT_DEFAULT_ZERO && getServiceOutageEndTime() > ApplicationConstant.CONSTANT_DEFAULT_ZERO) {
            return currentTimestamp >= getServiceOutageStartTime() && currentTimestamp <= getServiceOutageEndTime();
        }
        return false;
    }


    /**
     * @param currentTimeStamp Current time
     * @return next service checker running time
     */
    public long getNextRunningTime(long currentTimeStamp){
        if(isServiceInOutage(currentTimeStamp)){
            return getServiceOutageEndTime();
        }else if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==getGracePeriod() || (getPollingFrequency()<=getGracePeriod())||(getPollingFrequency()>getGracePeriod() && (getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency()))){
            return getLastRuningtimeWithPollingFrequency()+getPollingFrequency()* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR;
        }else{
            return getLastRuningtimeWithPollingFrequency()+getGracePeriod()* ApplicationConstant.CONSTANT_MILI_SEC_TO_SEC_MULTIFICATION_FACTOR;
        }

    }

    /**
     * This method is for updating the last runtime according to the next runtime generation logic.
     * @param currentTimeStamp current time
     */
    public void updateLastRunningtime(long currentTimeStamp){
        if(isServiceInOutage(currentTimeStamp)){
            setLastRuningtimeWithPollingFrequency(getServiceOutageEndTime());
            setLastRuningTime(getLastRuningtimeWithPollingFrequency());
        }else if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==getGracePeriod()|| (getPollingFrequency()<=getGracePeriod())||(getPollingFrequency()>getGracePeriod() && (getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency()))){
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
    protected void markServiceRunning(){
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
    protected void markServiceNotRunning(){
        ServiceRunningStatus servicePreviousRunningStatus=service.getServiceRunningStatus();
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==getGracePeriod()||(getLastRuningtimeWithGracePeriod() >= getLastRuningtimeWithPollingFrequency())) {
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
                ", serviceOutageStartTime=" + serviceOutageStartTime +
                ", serviceOutageEndTime=" + serviceOutageEndTime +
                '}';
    }
}
