package com.blitz.tutorial.common;

import com.blitz.tutorial.chapter6.TokenEnum;

public class Token {
    public TokenEnum type;
    public String rawText;
    protected SourcePosition srcPos;
    public Boolean isIdentity() {
        return type == TokenEnum.IDENTIFIER;
    }

    public Boolean isNumber() {
        return  type == TokenEnum.DOUBLE || type == TokenEnum.FLOAT || type == TokenEnum.INT;
    }

    public Boolean isString() {
        return  type == TokenEnum.STRING;
    }

    public Token(TokenEnum type,String rawText){
        this.type = type;
        this.rawText = rawText;
        this.srcPos = new SourcePosition();
    }

    public Token(TokenEnum type,String rawText,int pos ,int offset){
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