package com.lin.spi.exception;

/**
 * @author jianglinzou
 * @date 2019/7/5 上午11:23
 */
public class SPIRuntimeException extends RuntimeException {


    public SPIRuntimeException() {
    }

    public SPIRuntimeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public SPIRuntimeException(String msg) {
        super(msg);
    }

    public SPIRuntimeException(Throwable throwable) {
        super(throwable);
    }

}
