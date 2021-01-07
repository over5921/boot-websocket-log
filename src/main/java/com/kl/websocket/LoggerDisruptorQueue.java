package com.kl.websocket;

import com.kl.websocket.disruptor.*;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by kl on 2018/8/24.
 * Content :Disruptor 环形队列
 */
@Component
public class LoggerDisruptorQueue {

    private Executor executor = Executors.newCachedThreadPool();

    // The factory for the event
    private LoggerEventFactory factory = new LoggerEventFactory();

    private FileLoggerEventFactory fileLoggerEventFactory = new FileLoggerEventFactory();

    // Specify the size of the ring buffer, must be power of 2.
    private int bufferSize = 2 * 1024;

    // Construct the Disruptor
    private Disruptor<LoggerEvent> disruptor = new Disruptor<>(factory, bufferSize, executor);;

    private Disruptor<FileLoggerEvent> fileLoggerEventDisruptor = new Disruptor<>(fileLoggerEventFactory, bufferSize, executor);;

    private static  RingBuffer<LoggerEvent> ringBuffer;

    private static  RingBuffer<FileLoggerEvent> fileLoggerEventRingBuffer;

    @Autowired
    LoggerDisruptorQueue(LoggerEventHandler eventHandler,FileLoggerEventHandler fileLoggerEventHandler) {
        disruptor.handleEventsWith(eventHandler);
        fileLoggerEventDisruptor.handleEventsWith(fileLoggerEventHandler);
        this.ringBuffer = disruptor.getRingBuffer();
        this.fileLoggerEventRingBuffer = fileLoggerEventDisruptor.getRingBuffer();
        disruptor.start();
        fileLoggerEventDisruptor.start();
    }

    public static void publishEvent(LoggerMessage log) {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            LoggerEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setLog(log);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public static void publishEvent(String log) {
        if(fileLoggerEventRingBuffer == null) return;
        long sequence = fileLoggerEventRingBuffer.next();  // Grab the next sequence
        try {
            FileLoggerEvent event = fileLoggerEventRingBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.setLog(log);  // Fill with data
        } finally {
            fileLoggerEventRingBuffer.publish(sequence);
        }
    }

}
