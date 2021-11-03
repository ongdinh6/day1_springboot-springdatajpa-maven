package com.tma.demo.utils;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/* This class is used for debug */
public class LogUtil {
    private DateTimeUtil dateTimeUtil;
    private String errorMessage;
    private List<Logger> loggers;

    private void setDateTimeUtil(DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private LogUtil(){
        this.loggers = new ArrayList<>();
    }

    private static class MyLogger{
        private static final LogUtil LOG_UTIL = new LogUtil();

        public static LogUtil getLogUtil() {
            return LOG_UTIL;
        }
    }

    public static LogUtil getInstance() {
        return MyLogger.getLogUtil();
    }

    public void setLogUtil(String errorMessage, Logger logger){
        setDateTimeUtil(new DateTimeUtil());
        setErrorMessage(errorMessage);
        loggers.add(logger);
    }





}
