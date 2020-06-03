package com.blitz.tutorial.chapter6;

public class RegexTerminal extends Terminal {
    /**
     *
     * @param str Regex pattern
     */
    RegexTerminal(String str) {
        super(str);
    }

    /**
     *
     * @param str Regex pattern
     * @param isReserved follow Terminal
     */
    RegexTerminal(String str, Boolean isReserved) {
        super(str, isReserved);
    }

    @Override
    public String eval(Matcher matcher) {
        return super.eval(matcher);
    }
}
