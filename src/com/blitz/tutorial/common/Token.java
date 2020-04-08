package com.blitz.tutorial.common;

public class Token {
    public int type;
    public String rawText;

    public Token(int type,String rawText){
        this.type = type;
        this.rawText = rawText;
    }

    public String text() {
        return this.rawText;
    }

    @Override
    public String toString() {

        return "Token{" +
                "type=" + type +
                ", rawText='" + rawText + '\'' +
                '}';
    }
}