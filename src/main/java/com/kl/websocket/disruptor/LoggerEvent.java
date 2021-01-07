package com.kl.websocket.disruptor;

import com.kl.websocket.LoggerMessage;

/**
 * Created by kl on 2018/8/24.
 * Content :进程日志事件内容载体
 */
public class LoggerEvent {

    private LoggerMessage log;

    public LoggerMessage getLog() {
        return log;
    }

    public void setLog(LoggerMessage log) {
        this.log = log;
    }
}
