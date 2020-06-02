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
     * @param shouldSkip follow Terminal
     */
    RegexTerminal(String str, Boolean shouldSkip) {
        super(str, shouldSkip);
    }

    /**
     *
     * @param str Regex pattern
     * @param shouldSkip follow Terminal
     * @param isReserved follow Terminal
     */
    RegexTerminal(String str, Boolean shouldSkip, Boolean isReserved) {
        super(str, shouldSkip, isReserved);
    }

    @Override
    public String eval(Matcher matcher) {
        return super.eval(matcher);
    }
}
