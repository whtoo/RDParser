package com.blitz.tutorial.chapter2;

import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Token;

import static com.blitz.tutorial.chapter6.TokenEnum.EOF;

public class Test {
    public static void main(String[] args) {
        ListLexer lexer = new ListLexer(args[0]);
        Token t = lexer.nextToken();
        while (t.type != EOF){
            System.out.println(t);
            t = lexer.nextToken();
        }
        System.out.println(t);
    }
}
