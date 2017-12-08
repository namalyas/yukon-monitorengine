package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import org.apache.log4j.Logger;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedServiceConfigurationManagerParameterException throws when unsupported ServiceConfigurationManager parameter  used for creating ServiceConfigurationManager object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedServiceConfigurationManagerParameterException extends UnsupportedParameterException{

    private static final Logger LOGGER= Logger.getLogger(UnsupportedServiceConfigurationManagerParameterException.class);
    private StringBuffer exceptionMessage;

    public UnsupportedServiceConfigurationManagerParameterException(){
        LOGGER.info("UnsupportedServiceConfigurationManagerParameterException() object creation");
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE);

    }

    public UnsupportedServiceConfigurationManagerParameterException(String message){
        LOGGER.info(String.format("UnsupportedServiceConfigurationManagerParameterException(%s) object creation",message));
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE);
        this.exceptionMessage.append(message);


    }

    public void setMessage(String message) {
        LOGGER.info(String.format("Calling method - setMessage(%s)",message));
        this.exceptionMessage.append(message);
    }

    @Override
    public String getMessage() {
        LOGGER.info(String.format("Calling method - getMessage()"));
        return this.exceptionMessage.toString();
    }

    @Override
    public String toString() {
        return "UnsupportedServiceConfigurationManagerParameterException{" +
                "exceptionMessage=" + exceptionMessage +
                '}';
    }
}
