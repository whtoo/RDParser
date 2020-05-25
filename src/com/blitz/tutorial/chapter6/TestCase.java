package com.blitz.tutorial.chapter6;

import org.antlr.v4.tool.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase {
    /**
     * Grammar:
     * lists -> list lists
     * list -> name (, name)*
     * name -> [a-z]+
     */
    public static void main(String[] args) {
        String subStr = "idStr";
        List charSet = new ArrayList();
        char start = 'a';
        char upperStart = 'A';
        for (int i = 0; i < 26; i++) {
            charSet.add(new Terminal(String.valueOf(start)));
            charSet.add(new Terminal(String.valueOf(upperStart)));

            start+=1;
            upperStart+=1;
        }
        Choice charOpt = new Choice(charSet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition identityRegex = new Repetition(charOpt);
        RuleApplicaiton idRule = new RuleApplicaiton("identityRegex");
        productionTable.put("start",idRule);
        productionTable.put("identityRegex",identityRegex);
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
