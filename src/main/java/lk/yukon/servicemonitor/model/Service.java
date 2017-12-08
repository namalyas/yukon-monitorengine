package lk.yukon.servicemonitor.model;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import lk.yukon.servicemonitor.enums.ServiceProtocolType;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;
import lk.yukon.servicemonitor.exception.UnsupportedServiceParameterException;
import org.apache.log4j.Logger;

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
    private static final Logger LOGGER= Logger.getLogger(Service.class);

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
        LOGGER.info(String.format("Service(%s,%s) object creation",host,port));
        if(null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            LOGGER.error(String.format("Exception occurs in Service(host,port) %s host :%s, port:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,host,port));
            throw new UnsupportedServiceParameterException(String.format("host :%s, port :%s",host,port));
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
        LOGGER.info(String.format("Service(%s,%s,%s) object creation",host,port,description));
        if(null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            LOGGER.error(String.format("Exception occurs in Service(host,port,description) %s host :%s, port:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,host,port));
            throw new UnsupportedServiceParameterException(String.format("host :%s, port :%s",host,port));
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
        LOGGER.info(String.format("Service(%s,%s,%s) object creation",host,port,serviceRunningStatus));
        if(null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            LOGGER.error(String.format("Exception occurs in Service(host,port,serviceRunningStatus) %s host :%s, port:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,host,port));
            throw new UnsupportedServiceParameterException(String.format("host :%s, port :%s",host,port));
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
        LOGGER.info(String.format("Service(%s,%s,%s,%s) object creation",host,port,serviceRunningStatus,serviceProtocolType));
        if(null==host || ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            LOGGER.error(String.format("Exception occurs in Service(host,port,serviceRunningStatus,serviceProtocolType) %s host :%s, port:%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,host,port));
            throw new UnsupportedServiceParameterException(String.format("host :%s, port :%s",host,port));
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
        LOGGER.info(String.format("Calling method - getServiceUID()"));
        return serviceUID;
    }

    /**
     * this method is private because serviceUID is auto generated
     *
     * @param serviceUID auto generated serviceUID = host+"_"+port
     * @throws UnsupportedServiceParameterException
     */
    private void setServiceUID(String serviceUID) throws UnsupportedServiceParameterException {
        LOGGER.info(String.format("Calling method - setServiceUID(%s)",serviceUID));
        if(null==serviceUID){
            LOGGER.error(String.format("Exception occurs in calling method setServiceUID(serviceUID) %s serviceUID :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,serviceUID));
            throw new UnsupportedServiceParameterException(String.format("serviceUID :%s",serviceUID));
        }else{
            this.serviceUID = serviceUID;
        }

    }

    public String getHost() {
        LOGGER.info(String.format("Calling method - getHost()"));
        return host;
    }

    public void setHost(String host)throws UnsupportedServiceParameterException  {
        LOGGER.info(String.format("Calling method - setHost(%s)",host));
        if(null==host){
            LOGGER.error(String.format("Exception occurs in calling method setHost(host) %s host :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,host));
            throw new UnsupportedServiceParameterException(String.format("host :%s",host));
        }else {
            this.host = host;
        }
    }

    public int getPort() {
        LOGGER.info(String.format("Calling method - getPort()"));
        return port;
    }

    public void setPort(int port)throws UnsupportedServiceParameterException {
        LOGGER.info(String.format("Calling method - setPort(%s)",port));
        if(ApplicationConstant.CONSTANT_DEFAULT_ZERO==port){
            LOGGER.error(String.format("Exception occurs in calling method setPort(port) %s port :%s",ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE,port));
            throw new UnsupportedServiceParameterException(String.format("port :%s",port));
        }else {
            this.port = port;
        }
    }

    public ServiceRunningStatus getServiceRunningStatus() {
        LOGGER.info(String.format("Calling method - getServiceRunningStatus()"));
        return serviceRunningStatus;
    }

    public void setServiceRunningStatus(ServiceRunningStatus serviceRunningStatus) {
        LOGGER.info(String.format("Calling method - setServiceRunningStatus(%s)",serviceRunningStatus));
        this.serviceRunningStatus = serviceRunningStatus;
    }

    public ServiceProtocolType getServiceProtocolType() {
        LOGGER.info(String.format("Calling method - getServiceProtocolType()"));
        return serviceProtocolType;
    }

    public void setServiceProtocolType(ServiceProtocolType serviceProtocolType) {
        LOGGER.info(String.format("Calling method - setServiceProtocolType(%s)",serviceProtocolType));
        this.serviceProtocolType = serviceProtocolType;
    }

    public String getDescription() {
        LOGGER.info(String.format("Calling method - getDescription()"));
        return description;
    }

    public void setDescription(String description) {
        LOGGER.info(String.format("Calling method - setDescription(%s)",description));
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
