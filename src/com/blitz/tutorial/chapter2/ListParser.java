package com.blitz.tutorial.chapter2;

import com.blitz.tutorial.common.Lexer;
import com.blitz.tutorial.common.Parser;
import static com.blitz.tutorial.chapter6.TokenEnum.*;

import java.util.List;

/**
 * list : '[' elements ']'
 * elements : element (',', element)*
 * element : NAME | list;
 */
public class ListParser extends Parser {

    public ListParser(Lexer input) {
        super(input);
    }
    /** list : '[' elements ']' */
    public void list() {
        match(LBRACK);
        elements();
        match(RBRACK);

    }
    /** elements : element (',', element)* */
    void elements() {
        element();
        while (lookahead.type == COMMA) {
            match(COMMA);
            element();
        }
    }

    void element() {
        if(lookahead.type == NAME) NAME();
        else if(lookahead.type == LBRACK) list();
        else throw  new Error("expecting name or list ; found " + lookahead);
    }

    void NAME() {
        match(NAME);
    }

    public static void main(String[] args) {
        ListLexer lexer = new ListLexer(args[0]);
        ListParser parser = new ListParser(lexer);
        parser.list();
    }
}
