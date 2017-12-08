package lk.yukon.servicemonitor.exception;

import lk.yukon.servicemonitor.configuration.ApplicationConstant;

/**
 * license: BSD - see LICENSE for details
 *
 * UnsupportedParameterException throws when unsupported  parameter  used with creating object or calling method
 *
 * @author Namal Weheragoda
 * @version 1.0.0
 */
public class UnsupportedParameterException extends Exception {
    private StringBuffer exceptionMessage;

    public UnsupportedParameterException(){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_PARAMETER_MESSAGE);

    }

    public UnsupportedParameterException(String message){
        this.exceptionMessage=new StringBuffer();
        this.exceptionMessage.append(ApplicationConstant.CONSTANT_UNSUPPORTED_PARAMETER_MESSAGE);
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
        return "UnsupportedParameterException{" +
                "exceptionMessage=" + exceptionMessage +
                '}';
    }
}
