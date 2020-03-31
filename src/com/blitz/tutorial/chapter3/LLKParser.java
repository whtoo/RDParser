package com.blitz.tutorial.chapter3;

import com.blitz.tutorial.chapter2.ListLexer;
import com.blitz.tutorial.chapter2.ListParser;
import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Parser;
import com.blitz.tutorial.common.Token;

/**
 *
 */
public class LLKParser  {
    protected LLKLexer input;
    protected Token[] lookahead;

    protected int k;
    protected int p = 0;

    public  LLKParser(LLKLexer input,int k) {
        this.input = input;
        this.k = k;
        this.lookahead = new Token[k];
        for (int i = 1;i <=k;i++) consume();

    }

    public Token LT(int i) {return lookahead[(p+i-1)%k];}
    public int LA(int i) {return LT(i).type;}

    public  void consume() {
        lookahead[p] = input.nextToken();
        p = (p+1) % k;
    }

    public void match(int x) {
        if (LA(1) == x) {
            System.out.println(lookahead[p]);
            consume();
        }
        else throw new Error("expecting " + input.getTokenName(x)+"; found "+ LT(1));
    }

    /** list : '[' elements ']' */
    public void list() {
        match(ListLexer.LBRACK);
        elements();
        match(ListLexer.RBRACK);

    }
    /** elements : element (',', element)* */
    void elements() {
        element();
        while (LA(1) == ListLexer.COMMA) {
            match(ListLexer.COMMA);
            element();
        }
    }

    void NAME() {
        match(ListLexer.NAME);
    }

    void element() {
        if(LA(1) == LLKLexer.NAME && LA(2) == LLKLexer.EQUALS) {
            match(LLKLexer.NAME);
            match(LLKLexer.EQUALS);
            match(LLKLexer.NAME);
        }
        else if(LA(1) == LLKLexer.NAME) match(LLKLexer.NAME);
        else if(LA(1) == LLKLexer.LBRACK) list();

    }

    public static void main(String[] args) {
        LLKLexer lexer = new LLKLexer(args[0]);
        LLKParser parser = new LLKParser(lexer,2);
        parser.list();
    }
}
