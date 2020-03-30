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
public class ProtoZeroParser {
    String mInput;
    List<String> tokens;
    int pos;

    public  ProtoZeroParser(String input) {
        mInput = input;
        pos = 0;
    }

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
            moveForward(offset);
        } else {
            throw new Exception("Not matched on pos,offset :"+ pos +","+ offset +"when parser expected " + text);
        }
    }

    void moveForward(int offset) {
        pos += offset;
        if(pos == mInput.length()-1){
            // Reach EOF
            System.out.println("EOF end!");
        }
    }

    void skipWS() {
        char cur = mInput.charAt(pos);
        if(cur == '\n' || cur == '\t' || cur == ' '){
            moveForward(1);
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
