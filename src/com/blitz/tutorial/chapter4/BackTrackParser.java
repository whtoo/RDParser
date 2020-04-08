package com.blitz.tutorial.chapter4;

import com.blitz.tutorial.chapter5.PreviousParseFailedException;
import com.blitz.tutorial.common.Lexer;
// stat : list EOF | assign EOF ;
// assign : list '=' list ;
// list : '[' elements ']'
// elements : element (',', element)*
// element : NAME = NAME | NAME | list;

public class BackTrackParser extends Parser {

    public BackTrackParser(Lexer input) {
        super(input);
    }

    /**
     * stat : list EOF | assign EOF
     */
    public void stat() throws Exception {
        if (speculate_stat_alt1()) {
            list();
            match(Lexer.EOF_TYPE);
        } else if (speculate_stat_alt2()) {
            assign();
            match(Lexer.EOF_TYPE);
        }
        else {
            throw new Error("expecting stat found " + LT(1));
        }
    }

    protected boolean speculate_stat_alt1() {
        System.out.println("attempt alternative 1");
        boolean success = true;
        mark();

        try {
            list();
            match(Lexer.EOF_TYPE);
        } catch (Exception ex) {
            System.out.println("Try with alt1 but failed");
            success = false;
        }
        release();


        return success;
    }

    protected boolean speculate_stat_alt2() {
        System.out.println("attempt alternative 2");
        boolean success = true;
        mark();

        try {
            assign();
            match(Lexer.EOF_TYPE);
        } catch (Exception ex) {
            System.out.println("Try with alt2 but failed");
            success = false;
        }
        release();


        return success;
    }

    // list : '[' elements ']'
    protected void list() throws Exception {
        System.out.println("Parse list rule at index"+index());
        match(BackTrackLexer.LBRACK);
        elements();
        match(BackTrackLexer.RBRACK);
    }

    // assign : list '=' list ;
    protected void assign() throws Exception {
        list();
        match(BackTrackLexer.EQUALS);
        list();
    }

    // elements : element (',', element)*
    protected void elements() throws Exception {
        element();
        while (LA(1) == BackTrackLexer.COMMA) {
            match(BackTrackLexer.COMMA);
            element();
        }
    }

    // element : NAME = NAME | NAME | list;
    protected void element() throws Exception {
        if(LA(1) == BackTrackLexer.NAME && LA(2) == BackTrackLexer.EQUALS) {
            match(BackTrackLexer.NAME);
            match(BackTrackLexer.EQUALS);
            match(BackTrackLexer.NAME);
        } else if(LA(1) == BackTrackLexer.NAME) {
            match(BackTrackLexer.NAME);
        } else if(LA(1) == BackTrackLexer.LBRACK) {
            list();
        } else {
            throw new Error("expecting element found "+ input.getTokenName(LA(1)));
        }
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new BackTrackLexer(args[0]);
        BackTrackParser parser = new BackTrackParser(lexer);
        parser.stat();
    }
}
