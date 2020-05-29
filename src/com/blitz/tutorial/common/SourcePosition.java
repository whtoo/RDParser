package com.blitz.tutorial.common;

public class SourcePosition {
    /**
     * 开始位置
     */
    int startPos;
    /**
     * 相对开始位置的偏移
     */
    int offset;
    /**
     * 行号
     */
    int lineNo;

    public SourcePosition(){
        this.startPos = 0;
        this.offset = -1;
    }

    public SourcePosition(int pos,int offset) {
        this.startPos =pos;
        this.offset = offset;
    }
}
