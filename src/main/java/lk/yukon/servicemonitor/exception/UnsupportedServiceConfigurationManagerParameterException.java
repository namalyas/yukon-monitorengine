package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedServiceConfigurationManagerParameterException throws when unsupported ServiceConfigurationManager parameter  used for creating ServiceConfigurationManager object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedServiceConfigurationManagerParameterException extends UnsupportedParameterException{

    private StringBuffer exceptionMessage;

    public UnsupportedServiceConfigurationManagerParameterException(){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE);

    }

    public UnsupportedServiceConfigurationManagerParameterException(String message){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_CONFIGURATION_MANAGER_PARAMETER_MESSAGE);
        this.exceptionMessage.append(message);


    }

    public void setMessage(String message) {
        this.exceptionMessage.append(message);
    }

    @Override
    public String getMessage() {
        return this.exceptionMessage.toString();
    }

    @Override
    public String toString() {
        return "UnsupportedServiceConfigurationManagerParameterException{" +
                "exceptionMessage=" + exceptionMessage +
                '}';
    }
}
