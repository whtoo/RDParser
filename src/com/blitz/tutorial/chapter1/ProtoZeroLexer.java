package com.blitz.tutorial.chapter1;

/**
 * Without lexer and token,
 * the codes will show how to make an analysis of statement.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *    Etc : return x + 1;
 */
public class ProtoZeroLexer {
    String mInput;
    List<String> tokens;
    int pos;

    public ProtoZeroLexer(String input) {
        mInput = input;
        pos = 0;
    }

    /**
     *  1. match "return".
     *  2. match expression
     *  3. match ";"
     * @return The list of expected words.
     * @throws Exception Any unexpected case.
     */
    public List<String> parse() throws Exception{
        tokens = new ArrayList<>(4);
        match("return");
        skipWS();
        expr();
        skipWS();
        match(";");
        return tokens;
    }

    void match(String text) throws Exception {
        boolean flag = false;
        int offset = text.length();
        flag = mInput.substring(pos,pos+offset).equalsIgnoreCase(text);
        if(flag) {
            tokens.add(text);
            consume(offset);
        } else {
            throw new Exception("Not matched on pos,offset :"+ pos +","+ offset +"when parser expected " + text);
        }
    }

    void consume(int offset) {
        pos += offset;
        if(pos == mInput.length()-1){
            // Reach EOF
            System.out.println("EOF end!");
        }
    }

    void skipWS() {
        char cur = mInput.charAt(pos);
        if(cur == '\n' || cur == '\t' || cur == ' '){
            consume(1);
        }
    }

    void expr() throws Exception{
        match("x");
        skipWS();
        match("+");
        skipWS();
        match("1");
    }
}
