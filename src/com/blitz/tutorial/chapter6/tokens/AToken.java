package com.blitz.tutorial.chapter6.tokens;

import com.blitz.tutorial.chapter6.TokenEnum;

import javax.print.DocFlavor;

public abstract class AToken {
    String image;
    TokenEnum type;
    Position pos;

    public void setPos(int starPos, int offset) {
        this.setPos(starPos, offset,0);
    }

    static class Position {
        String fileName;
        int col;
        int colOffset;
        int row;

        public Position(String fileName,int col,int colOffset,int row){
            this.fileName = fileName;
            this.col = col;
            this.colOffset = colOffset;
            this.row = row;
        }
        public Position(){
           this(null,-1,-1,-1);
        }
        public void setSrcPos(int start,int end){
            this.col = start;
            this.colOffset = end;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "start=" + col +
                    ", offset=" + colOffset +
                    '}';
        }
    }

    public AToken(String image,TokenEnum tokenEnum) {
        this.image = image;
        this.type = tokenEnum;
        this.pos = new Position();
    }

    public void setPos(int col, int offset, int line) {
        this.pos.setSrcPos(col,offset);
        this.pos.row = line;
    }

    public abstract Boolean test();


    protected String typeName() {
        switch (this.type) {
            case NAME:
                return "NAME";
            case LBRACK:
                return "LBRACK";
            case COMMA:
                return "COMMA";
            case EOF:
                return "EOF";
            case EQUALS:
                return "EQUALS";
            case RBRACK:
                return "RBRACK";
            case STRING:
                return "STRING";
            case IDENTIFIER:
                return "IDENTIFIER";
            case INT:
                return "INT";
            case FLOAT:
                return "FLOAT";
            case DOUBLE:
                return "DOUBLE";
            case OPERATOR:
                return "OPERATOR";
            case CHARACTER:
                return "CHARACTER";
            default:
                return "UNKNOW";
        }
    }
    @Override
    public String toString() {
        return "AToken{" +
                "image='" + image + '\'' +
                ", type=" + typeName() +
                ", pos=" + pos +
                '}';
    }
}
