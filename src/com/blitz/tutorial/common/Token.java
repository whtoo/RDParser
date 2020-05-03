package com.blitz.tutorial.common;

public class Token {
    public int type;
    public String rawText;
    protected SourcePosition srcPos;

    public Token(int type,String rawText){
        this.type = type;
        this.rawText = rawText;
        this.srcPos = new SourcePosition();
    }

    public Token(int type,String rawText,int pos ,int offset){
        this(type,rawText);
        this.setSrcPos(pos,offset);
    }
    public void setSrcPos(int pos,int offset){
        this.srcPos.startPos = pos;
        this.srcPos.offset = offset;
    }

    public int getStartPos() {
        return this.srcPos.startPos;
    }

    public int getOffset() {
        return this.srcPos.offset;
    }

    public String text() {
        return this.rawText;
    }

    @Override
    public String toString() {

        return "Token{" +
                "type=" + type +
                ", rawText='" + rawText + '\'' +
                ", from'" + getStartPos() + '\'' +
                ", to'" + getOffset() + '\'' +
                '}';
    }
}