package com.blitz.tutorial.chapter4;

import com.blitz.tutorial.chapter3.LLKLexer;

public class BackTrackLexer extends LLKLexer {
    public BackTrackLexer(String input) {
        super(input);
    }

    @Override
    public String getTokenName(int tokenType) {
        switch (tokenType) {
            case 4:
                return "LBRACK";
            case 5:
                return "RBRACK";
            case -1:
                return "EOF";
            case 6:
                return "EQUALS";
            case 2:
                return "NAME";
            case 3:
                return "COMMA";
            default:
                return "None";
        }
    }
}
