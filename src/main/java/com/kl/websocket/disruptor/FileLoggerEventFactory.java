package com.kl.websocket.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by kl on 2018/8/24.
 * Content :文件日志事件工厂类
 */
public class FileLoggerEventFactory implements EventFactory<FileLoggerEvent> {
    @Override
    public FileLoggerEvent newInstance() {
        return new FileLoggerEvent();
    }
}
