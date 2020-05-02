package com.blitz.tutorial.chapter6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matcher {
    protected List<IRuleApplication> rules;
    protected Integer pos;
    protected String input;
    protected Map<Integer, Map<String,Object>> memoTable;

    public Matcher(List<IRuleApplication> rules) {
        this.rules = rules;
    }

}
