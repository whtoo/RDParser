package com.blitz.tutorial.common;


public abstract class Parser {
    protected Lexer input;
    protected Token lookahead;
    public  Parser(Lexer input) { this.input = input; consume();}
    public  void consume() { lookahead = input.nextToken();}
    public void match(int x) {
        if (lookahead.type == x) consume();
        else throw new Error("expecting " + input.getTokenName(x)+"; found "+ lookahead);
    }
}
