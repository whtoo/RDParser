package com.blitz.tutorial.chapter2;

import com.blitz.tutorial.chapter6.TokenEnum;
import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

import static com.blitz.tutorial.chapter6.TokenEnum.*;

public class ListLexer extends Lexer {
    public static String[] tokenNames =
            {"n/a","<EOF>","NAME","COMMA","LBRACK","RBRACK"};

    public ListLexer(String input) {
        super(input);
    }

    @Override
    //TODO: add map of tokenNames
    public String getTokenName(TokenEnum tokenType) {
        return "";
    }

    @Override
    public Token nextToken() {
        int startPos = this.p;
        while(c != (char)-1){
            switch (c) {
                case ' ': case '\t': case '\n': case '\r': WS();continue;
                case ',':consume();return new Token(COMMA,",",startPos,p);
                case '[':consume();return new Token(LBRACK,"[",startPos,p);
                case ']':consume();return new Token(RBRACK,"]",startPos,p);
                default:
                    /**
                     * TODO 这里有两个引申问题：
                     * 1. 如何实现 NAME: [a-zA-Z]([0-9]|[a-zA-z])*
                     * 2. 如何实现 NAME 与 Keywords 做区分
                     *      Etc: NAME := ifexp
                     *           Keyword := if
                     */
                    if(isLETTER()) return NAME(startPos);
                    throw new Error("invalid character: "+c);
            }
        }

        return new Token(EOF,"<EOF>");
    }


    protected Token NAME(int pos) {
        StringBuilder buf = new StringBuilder();
        /**
         * 这里已经实现的是 NAME: [a-zA-Z]+
         */
        do { buf.append(c);consume();} while (isLETTER());
        Token nameToken = new Token(NAME,buf.toString());
        nameToken.setSrcPos(pos,p);
        return nameToken;
    }

    protected Boolean isLETTER() { return c >= 'a' && c <= 'z' || c>= 'A' && c<='Z';}
}
