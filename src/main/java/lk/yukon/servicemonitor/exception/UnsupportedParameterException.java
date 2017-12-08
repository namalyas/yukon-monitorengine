package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;
import org.apache.log4j.Logger;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedParameterException throws when unsupported  parameter  used with creating object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedParameterException extends Exception {
    private static final Logger LOGGER= Logger.getLogger(UnsupportedParameterException.class);
    private StringBuffer exceptionMessage;

    public UnsupportedParameterException(){
        LOGGER.info("UnsupportedParameterException() object creation");
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_PARAMETER_MESSAGE);

    }

    public UnsupportedParameterException(String message){
        LOGGER.info(String.format("UnsupportedParameterException(%s) object creation",message));
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_PARAMETER_MESSAGE);
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
        return "UnsupportedParameterException{" +
                "exceptionMessage=" + exceptionMessage +
                '}';
    }
}
