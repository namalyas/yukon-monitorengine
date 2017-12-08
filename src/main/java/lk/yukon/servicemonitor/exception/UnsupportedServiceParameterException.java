package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import org.apache.log4j.Logger;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedServiceParameterException throws when unsupported service parameter  used for creating service object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedServiceParameterException extends UnsupportedParameterException {

    private static final Logger LOGGER= Logger.getLogger(UnsupportedServiceParameterException.class);
    private StringBuffer exceptionMessage;

    public UnsupportedServiceParameterException(){
        LOGGER.info("UnsupportedServiceParameterException() object creation");
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE);

    }

    public UnsupportedServiceParameterException(String message){
        LOGGER.info(String.format("UnsupportedServiceParameterException(%s) object creation",message));
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_SERVICE_PARAMETER_MESSAGE);
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
        return "UnsupportedServiceParameterException{" +
                "exceptionMessage=" + exceptionMessage +
                '}';
    }
}
