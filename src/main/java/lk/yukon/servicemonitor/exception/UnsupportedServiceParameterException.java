package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedServiceParameterException throws when unsupported service parameter  used for creating service object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedServiceParameterException extends UnsupportedParameterException {

    private StringBuffer exceptionMessage;

    public UnsupportedServiceParameterException(){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE);

    }

    public UnsupportedServiceParameterException(String message){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE);
        this.exceptionMessage.append(message);


    }

    public void setMessage(String message) {
        this.exceptionMessage.append(message);
    }

    @Override
    public String getMessage() {
        return this.exceptionMessage.toString();
    }
}
