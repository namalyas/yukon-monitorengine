package lk.yukon.servicemonitor.model;

import lk.yukon.servicemonitor.enums.ServiceProtocolType;
import lk.yukon.servicemonitor.enums.ServiceRunningStatus;

/**
 * license: BSD - see LICENSE for details
 *
 * Service represent the real world service entity
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class Service {
    private String serviceUID;
    private String host;
    private int port;
    private ServiceRunningStatus serviceRunningStatus;
    private ServiceProtocolType serviceProtocolType;


    /**
     * @param serviceUID Unique Id representing the service
     * @param host host of the service
     * @param port port of the service
     */
    public Service(String serviceUID, String host, int port) {
        this.serviceUID = serviceUID;
        this.host = host;
        this.port = port;
        this.serviceRunningStatus=ServiceRunningStatus.SERVICE_RUNNING_STATUS_UNDEFINED;
        this.serviceProtocolType=ServiceProtocolType.SERVICE_PROTOCOL_TYPE_TCP;
    }


    /**
     * @param serviceUID Unique Id representing the service
     * @param host host of the service
     * @param port port of the service
     * @param serviceRunningStatus service running status
     */
    public Service(String serviceUID, String host, int port, ServiceRunningStatus serviceRunningStatus) {
        this.serviceUID = serviceUID;
        this.host = host;
        this.port = port;
        this.serviceRunningStatus = serviceRunningStatus;
        this.serviceProtocolType=ServiceProtocolType.SERVICE_PROTOCOL_TYPE_TCP;
    }

    /**
     * @param serviceUID Unique Id representing the service
     * @param host host of the service
     * @param port port of the service
     * @param serviceRunningStatus service running status
     * @param serviceProtocolType service communicating protocol
     */
    public Service(String serviceUID, String host, int port, ServiceRunningStatus serviceRunningStatus, ServiceProtocolType serviceProtocolType) {
        this.serviceUID = serviceUID;
        this.host = host;
        this.port = port;
        this.serviceRunningStatus = serviceRunningStatus;
        this.serviceProtocolType = serviceProtocolType;
    }

    public String getServiceUID() {
        return serviceUID;
    }

    public void setServiceUID(String serviceUID) {
        this.serviceUID = serviceUID;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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


    @Override
    public String toString() {
        return "Service{" +
                "serviceUID='" + serviceUID + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", serviceRunningStatus=" + serviceRunningStatus +
                ", serviceProtocolType=" + serviceProtocolType +
                '}';
    }



}
