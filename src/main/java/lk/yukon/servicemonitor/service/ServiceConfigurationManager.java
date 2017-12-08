package lk.yukon.servicemonitor.service;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;
import lk.yukon.servicemonitor.exception.UnsupportedServiceConfigurationManagerParameterException;
import lk.yukon.servicemonitor.listener.ServiceBehaviourListener;
import lk.yukon.servicemonitor.model.Service;
import org.apache.log4j.Logger;

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

    private static final Logger LOGGER= Logger.getLogger(ServiceConfigurationManager.class);

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
        LOGGER.info(String.format("ServiceConfigurationManager(%s,%s,%s) object creation",service,pollingFrequency,gracePeriod));
        if(null==service||ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            LOGGER.error(String.format("Exception occurs in ServiceConfigurationManager(service,pollingFrequency,gracePeriod) %s service :%s, pollingFrequency:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE,service,pollingFrequency));
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("service :%s, pollingFrequency :%s",service,pollingFrequency));
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
        LOGGER.info(String.format("ServiceConfigurationManager(%s,%s,%s,%s) object creation",service,pollingFrequency,gracePeriod,serviceBehaviourListenerList));
        if(null==service||ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            LOGGER.error(String.format("Exception occurs in ServiceConfigurationManager(service,pollingFrequency,gracePeriod,serviceBehaviourListenerList) %s service :%s, pollingFrequency:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE,service,pollingFrequency));
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("service :%s, pollingFrequency :%s",service,pollingFrequency));
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
        LOGGER.info(String.format("Calling method - getService()"));
        return service;
    }

    public void setService(Service service) throws UnsupportedServiceConfigurationManagerParameterException {
        LOGGER.info(String.format("Calling method - setService(%s)",service));
        if(null==service){
            LOGGER.error(String.format("Exception occurs in calling method setService(service) %s service :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE,service));
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("service : %s",service));
        }else {
            this.service = service;
        }
    }

    public int getPollingFrequency() {
        LOGGER.info(String.format("Calling method - getPollingFrequency()"));
        return pollingFrequency;
    }

    public void setPollingFrequency(int pollingFrequency) throws UnsupportedServiceConfigurationManagerParameterException{
        LOGGER.info(String.format("Calling method - setPollingFrequency(%s)",pollingFrequency));
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==pollingFrequency){
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("pollingFrequency : %s",pollingFrequency));
        }else {
            this.pollingFrequency = pollingFrequency;
        }
    }

    public int getGracePeriod() {
        LOGGER.info(String.format("Calling method - getGracePeriod()"));
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        LOGGER.info(String.format("Calling method - setGracePeriod(%s)",gracePeriod));
        this.gracePeriod = gracePeriod;
    }

    public List<ServiceBehaviourListener> getServiceBehaviourListenerList() {
        LOGGER.info(String.format("Calling method - getServiceBehaviourListenerList()"));
        return serviceBehaviourListenerList;
    }

    public void setServiceBehaviourListenerList(List<ServiceBehaviourListener> serviceBehaviourListenerList) {
        LOGGER.info(String.format("Calling method - setServiceBehaviourListenerList(%s)",serviceBehaviourListenerList));
        this.serviceBehaviourListenerList = serviceBehaviourListenerList;
    }

    public long getLastRuningTime() {
        LOGGER.info(String.format("Calling method - getLastRuningTime()"));
        return lastRuningTime;
    }

    public void setLastRuningTime(long lastRuningTime) {
        LOGGER.info(String.format("Calling method - setLastRuningTime(%s)",lastRuningTime));
        this.lastRuningTime = lastRuningTime;
    }

    public long getLastRuningtimeWithPollingFrequency() {
        LOGGER.info(String.format("Calling method - getLastRuningtimeWithPollingFrequency()"));
        return lastRuningtimeWithPollingFrequency;
    }

    public void setLastRuningtimeWithPollingFrequency(long lastRuningtimeWithPollingFrequency) {
        LOGGER.info(String.format("Calling method - setLastRuningtimeWithPollingFrequency(%s)",lastRuningtimeWithPollingFrequency));
        this.lastRuningtimeWithPollingFrequency = lastRuningtimeWithPollingFrequency;
    }

    public long getLastRuningtimeWithGracePeriod() {
        LOGGER.info(String.format("Calling method - getLastRuningtimeWithGracePeriod()"));
        return lastRuningtimeWithGracePeriod;
    }

    public void setLastRuningtimeWithGracePeriod(long lastRuningtimeWithGracePeriod) {
        LOGGER.info(String.format("Calling method - setLastRuningtimeWithGracePeriod(%s)",lastRuningtimeWithGracePeriod));
        this.lastRuningtimeWithGracePeriod = lastRuningtimeWithGracePeriod;
    }


    public long getServiceOutageStartTime() {
        LOGGER.info(String.format("Calling method - getServiceOutageStartTime()"));
        return serviceOutageStartTime;
    }

    public void setServiceOutageStartTime(long serviceOutageStartTime) throws UnsupportedServiceConfigurationManagerParameterException {
        LOGGER.info(String.format("Calling method - setServiceOutageStartTime(%s)",serviceOutageStartTime));
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO>=serviceOutageStartTime){
            LOGGER.error(String.format("Exception occurs in calling method setServiceOutageStartTime(serviceOutageStartTime) %s serviceOutageStartTime :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE,serviceOutageStartTime));
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("serviceOutageStartTime : %s",serviceOutageStartTime));
        }else{
            this.serviceOutageStartTime = serviceOutageStartTime;
        }

    }

    public long getServiceOutageEndTime() {
        LOGGER.info(String.format("Calling method - getServiceOutageEndTime()"));
        return serviceOutageEndTime;
    }

    public void setServiceOutageEndTime(long serviceOutageEndTime) throws UnsupportedServiceConfigurationManagerParameterException{
        LOGGER.info(String.format("Calling method - setServiceOutageEndTime(%s)",serviceOutageEndTime));
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO>=serviceOutageEndTime || getServiceOutageStartTime()>= serviceOutageEndTime){
            LOGGER.error(String.format("Exception occurs in calling method setServiceOutageEndTime(serviceOutageEndTime) %s serviceOutageStartTime :%s , serviceOutageEndTime :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE,getServiceOutageStartTime(),serviceOutageEndTime));
            throw new UnsupportedServiceConfigurationManagerParameterException(String.format("serviceOutageStartTime :%s , serviceOutageEndTime :%s",getServiceOutageStartTime(),serviceOutageEndTime));
        }else {
            this.serviceOutageEndTime = serviceOutageEndTime;
        }
    }

    /**
     *
     * @param currentTimestamp current time stamp
     * @return true if serviceOutageStartTime < currentTimestamp < serviceOutageEndTime
     */
    public synchronized boolean isServiceInOutage(long currentTimestamp) {
        LOGGER.info(String.format("Calling method - isServiceInOutage(%s)",currentTimestamp));
        if(getServiceOutageStartTime() > ApplicationConstant.CONSTANT_DEFAULT_ZERO && getServiceOutageEndTime() > ApplicationConstant.CONSTANT_DEFAULT_ZERO) {
            return currentTimestamp >= getServiceOutageStartTime() && currentTimestamp <= getServiceOutageEndTime();
        }
        return false;
    }


    /**
     * @param currentTimeStamp Current time
     * @return next service checker running time
     */
    public synchronized long getNextRunningTime(long currentTimeStamp){
        LOGGER.info(String.format("Calling method - getNextRunningTime(%s)",currentTimeStamp));
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
    public synchronized void updateLastRunningtime(long currentTimeStamp){
        LOGGER.info(String.format("Calling method - updateLastRunningtime(%s)",currentTimeStamp));
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
    public synchronized void addServiceBehaviourListener(ServiceBehaviourListener serviceBehaviourListener){
        LOGGER.info(String.format("Calling method - addServiceBehaviourListener(%s)",serviceBehaviourListener));
        serviceBehaviourListenerList.add(serviceBehaviourListener);
    }

    /**
     *
     * @param serviceBehaviourListener serviceBehaviourListener object for removing from serviceBehaviourListenerList
     */
    public synchronized void removeServiceBehaviourListener(ServiceBehaviourListener serviceBehaviourListener){
        LOGGER.info(String.format("Calling method - removeServiceBehaviourListener(%s)",serviceBehaviourListener));
        serviceBehaviourListenerList.remove(serviceBehaviourListener);
    }


    /**
     * This method is for watching service status change (service running) and notify to listeners
     */
    protected synchronized void markServiceRunning(){
        LOGGER.info(String.format("Calling method - markServiceRunning()"));
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
    protected synchronized void markServiceNotRunning(){
        LOGGER.info(String.format("Calling method - markServiceNotRunning()"));
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
