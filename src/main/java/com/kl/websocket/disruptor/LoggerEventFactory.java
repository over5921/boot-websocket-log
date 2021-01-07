package com.kl.websocket.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by kl on 2018/8/24.
 * Content :进程日志事件工厂类
 */
public class LoggerEventFactory implements EventFactory<LoggerEvent> {
    @Override
    public LoggerEvent newInstance() {
        return new LoggerEvent();
    }
}
