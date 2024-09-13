package com.calm.java.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;


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
    public void init() {
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
    }
}
