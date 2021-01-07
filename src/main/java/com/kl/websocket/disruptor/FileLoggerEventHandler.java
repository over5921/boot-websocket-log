package com.kl.websocket.disruptor;

import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by kl on 2018/8/24.
 * Content :文件日志事件处理器
 */
@Component
public class FileLoggerEventHandler implements EventHandler<FileLoggerEvent> {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void onEvent(FileLoggerEvent fileLoggerEvent, long l, boolean b) {
        messagingTemplate.convertAndSend("/topic/pullFileLogger",fileLoggerEvent.getLog());
    }
}
