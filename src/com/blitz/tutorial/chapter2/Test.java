package com.blitz.tutorial.chapter2;

import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

public class Test {
    public static void main(String[] args) {
        ListLexer lexer = new ListLexer(args[0]);
        Token t = lexer.nextToken();
        while (t.type != Lexer.EOF_TYPE){
            System.out.println(t);
            t = lexer.nextToken();
        }
        System.out.println(t);
    }
}
