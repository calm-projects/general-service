package com.calm.java.nio;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

@Slf4j
public class T01_ByteBuffer {

    /**
     * ByteBuffer 字节缓冲区，extends Buffer
     * 创建缓冲区：ByteBuffer.allocate(10)
     * ● capacity = 10：缓冲区的容量是 10。
     * ● position = 0：初始位置是 0。
     * ● limit = 10：初始限制是 10，即允许操作整个缓冲区。
     * ● mark 未定义：没有设置标记。
     * ● array offset = 0：如果有支持数组，则偏移量是 0。
     */
    @Test
    public void simple() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        // ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
        // ByteBuffer byteBuffer = ByteBuffer.wrap("0123456789".getBytes())
        // capacity:20,position:0,limit:20,mark:java.nio.HeapByteBuffer[pos=0 lim=20 cap=20],arrayOffset:0
        log.info("capacity:{},position:{},limit:{},mark:{},arrayOffset:{}",
                byteBuffer.capacity(), byteBuffer.position(),
                byteBuffer.limit(), byteBuffer.mark(), byteBuffer.arrayOffset());
        byte[] bytes = "0123456789".getBytes();
        // bytes length:10
        log.info("bytes length:{}", bytes.length);
        for (byte aByte : bytes) {
            byteBuffer.put(aByte);
        }

        // capacity:20,position:10,limit:20,mark:java.nio.HeapByteBuffer[pos=10 lim=20 cap=20],arrayOffset:0
        log.info("capacity:{},position:{},limit:{},mark:{},arrayOffset:{}",
                byteBuffer.capacity(), byteBuffer.position(),
                byteBuffer.limit(), byteBuffer.mark(), byteBuffer.arrayOffset());

        byteBuffer.flip();

        // capacity:20,position:0,limit:10,mark:java.nio.HeapByteBuffer[pos=0 lim=10 cap=20],arrayOffset:0
        log.info("capacity:{},position:{},limit:{},mark:{},arrayOffset:{}",
                byteBuffer.capacity(), byteBuffer.position(),
                byteBuffer.limit(), byteBuffer.mark(), byteBuffer.arrayOffset());
        while (byteBuffer.hasRemaining()) {
            byte b = byteBuffer.get();
            log.info("{}", b);
        }
    }

    /**
     * compact 压缩此缓冲区 源码详解
     * compact() 方法通常在使用 ByteBuffer 进行读取和写入操作时应用，特别是在你读取了部分数据但还没有完全处理完毕的情况下。
     * 它用于准备缓冲区，以便在保留未处理数据的同时，能够继续写入新数据。
     */
    @Test
    public void compact() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(20);
        byte[] bytes = "0123456789".getBytes();
        for (byte aByte : bytes) {
            byteBuffer.put(aByte);
        }
        byteBuffer.flip();
        for (int i = 0; i < 2; i++) {
            byte b = byteBuffer.get();
            // 48 49
            log.info("{}", b);
        }
        // capacity:20,position:2,limit:10,mark:java.nio.DirectByteBuffer[pos=5 lim=10 cap=20]
        log.info("capacity:{},position:{},limit:{},mark:{}", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit(), byteBuffer.mark());
        byteBuffer.compact();
        // capacity:20,position:8,limit:20,mark:java.nio.DirectByteBuffer[pos=5 lim=20 cap=20]
        log.info("capacity:{},position:{},limit:{},mark:{}", byteBuffer.capacity(),
                byteBuffer.position(), byteBuffer.limit(), byteBuffer.mark());
        log.info("compact");
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()){
            byte b = byteBuffer.get();
            // 50 51 52 53 54 55 56 57
            log.info("{}", b);
        }
        /*
            int pos = position();
            int lim = limit();
            assert (pos <= lim);
            lim 总共元素，pos已读区的元素， rem未读区的元素个数，例如 1234567，我的pos是2，rem就是5
            int rem = (pos <= lim ? lim - pos : 0);
            byte[] hb； 数组copy，从已读区位置开始，将rem个元素copy到数组的0位置
            例如 1234567，pos是2，rem是5，那么就从3开始copy到0，得到 3456767, 但是这里的 pos=5，如果使用flip limit = 5，
            所以不会读区到重复数据67
            System.arraycopy(hb, ix(pos), hb, ix(0), rem);
            position(rem);
            limit(capacity());
            discardMark();
            return this;
        */
    }
}
