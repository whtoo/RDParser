package com.blitz.tutorial.chapter4;

import com.blitz.tutorial.chapter3.LLKLexer;
import com.blitz.tutorial.chapter6.TokenEnum;

public class BackTrackLexer extends LLKLexer {
    public BackTrackLexer(String input) {
        super(input);
    }

    @Override
    public String getTokenName(TokenEnum tokenType) {
        switch (tokenType) {
            case LBRACK:
                return "LBRACK";
            case RBRACK:
                return "RBRACK";
            case EQUALS:
                return "EQUALS";
            case NAME:
                return "NAME";
            case COMMA:
                return "COMMA";
            default:
                return "EOF";
        }
    }
}
