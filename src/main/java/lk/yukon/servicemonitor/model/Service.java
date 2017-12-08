package lk.yukon.servicemonitor.model;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.enums.ServiceProtocolType;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;
import lk.yukon.servicemonitor.exception.UnsupportedServiceParameterException;

/**
 * license: BSD - see LICENSE for details
 *
 * Service represent the real world service entity.
 * serviceUID is generated host+"_"+port
 * default description is generated host+":"+port
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class Service {
    private String serviceUID;
    private String host;
    private String description;
    private int port;
    private ServiceRunningStatus serviceRunningStatus;
    private ServiceProtocolType serviceProtocolType;


    /**
     * @param host host of the service
     * @param port port of the service
     */
    public Service(String host, int port) throws UnsupportedServiceParameterException {
        if(null==serviceUID || null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            throw new UnsupportedServiceParameterException("serviceUID :"+serviceUID+", host :"+host+", port:"+port);
        }else{
            setServiceUID(host+ ApplicationConstant.CONSTANT_UNDERSCORE+port);
            this.host = host;
            this.port = port;
            setDescription(host+ApplicationConstant.CONSTANT_COLON+port);
            this.serviceRunningStatus=ServiceRunningStatus.SERVICE_RUNNING_STATUS_UNDEFINED;
            this.serviceProtocolType=ServiceProtocolType.SERVICE_PROTOCOL_TYPE_TCP;
        }


    }

    /**
     * @param host host of the service
     * @param port port of the service
     * @param description description of the service
     */
    public Service(String host, int port,String description) throws UnsupportedServiceParameterException {
        if(null==serviceUID || null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            throw new UnsupportedServiceParameterException("serviceUID :"+serviceUID+", host :"+host+", port:"+port);
        }else{
            setServiceUID(host+ ApplicationConstant.CONSTANT_UNDERSCORE+port);
            this.host = host;
            this.port = port;
            this.description=description;
            this.serviceRunningStatus=ServiceRunningStatus.SERVICE_RUNNING_STATUS_UNDEFINED;
            this.serviceProtocolType=ServiceProtocolType.SERVICE_PROTOCOL_TYPE_TCP;
        }


    }


    /**
     * @param host host of the service
     * @param port port of the service
     * @param serviceRunningStatus service running status
     */
    public Service(String host, int port, ServiceRunningStatus serviceRunningStatus)  throws UnsupportedServiceParameterException {
        if(null==serviceUID || null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            throw new UnsupportedServiceParameterException("serviceUID :"+serviceUID+", host :"+host+", port:"+port);
        }else {
            setServiceUID(host+ ApplicationConstant.CONSTANT_UNDERSCORE+port);
            this.host = host;
            this.port = port;
            setDescription(host+ApplicationConstant.CONSTANT_COLON+port);
            this.serviceRunningStatus = serviceRunningStatus;
            this.serviceProtocolType = ServiceProtocolType.SERVICE_PROTOCOL_TYPE_TCP;
        }
    }

    /**
     * @param host host of the service
     * @param port port of the service
     * @param serviceRunningStatus service running status
     * @param serviceProtocolType service communicating protocol
     */
    public Service(String host, int port, ServiceRunningStatus serviceRunningStatus, ServiceProtocolType serviceProtocolType)  throws UnsupportedServiceParameterException {
        if(null==serviceUID || null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            throw new UnsupportedServiceParameterException("serviceUID :"+serviceUID+", host :"+host+", port:"+port);
        }else {
            setServiceUID(host+ ApplicationConstant.CONSTANT_UNDERSCORE+port);
            this.host = host;
            this.port = port;
            setDescription(host+ApplicationConstant.CONSTANT_COLON+port);
            this.serviceRunningStatus = serviceRunningStatus;
            this.serviceProtocolType = serviceProtocolType;
        }
    }

    public String getServiceUID() {
        return serviceUID;
    }

    private void setServiceUID(String serviceUID) throws UnsupportedServiceParameterException {

        if(null==serviceUID){
            throw new UnsupportedServiceParameterException("serviceUID :"+serviceUID);
        }else{
            this.serviceUID = serviceUID;
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host)throws UnsupportedServiceParameterException  {
        if(null==host){
            throw new UnsupportedServiceParameterException("host :"+host);
        }else {
            this.host = host;
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port)throws UnsupportedServiceParameterException {
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            throw new UnsupportedServiceParameterException("port :"+port);
        }else {
            this.port = port;
        }
    }

    public ServiceRunningStatus getServiceRunningStatus() {
        return serviceRunningStatus;
    }

    public void setServiceRunningStatus(ServiceRunningStatus serviceRunningStatus) {
        this.serviceRunningStatus = serviceRunningStatus;
    }

    public ServiceProtocolType getServiceProtocolType() {
        return serviceProtocolType;
    }

    public void setServiceProtocolType(ServiceProtocolType serviceProtocolType) {
        this.serviceProtocolType = serviceProtocolType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceUID='" + serviceUID + '\'' +
                ", host='" + host + '\'' +
                ", description='" + description + '\'' +
                ", port=" + port +
                ", serviceRunningStatus=" + serviceRunningStatus +
                ", serviceProtocolType=" + serviceProtocolType +
                '}';
    }
}
