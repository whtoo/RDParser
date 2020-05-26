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
        String subStr = "idStr = 12";

        List charSet = new ArrayList();
        char start = 'a';
        char upperStart = 'A';
        for (int i = 0; i < 26; i++) {
            charSet.add(new Terminal(String.valueOf(start)));
            charSet.add(new Terminal(String.valueOf(upperStart)));

            start+=1;
            upperStart+=1;
        }
        start = '0';
        List digitSet = new ArrayList();
        for (int i = 0; i < 10; i++) {
            digitSet.add(new Terminal(String.valueOf(start)));
            start+=1;
        }

        Choice digitOpt = new Choice(digitSet);
        Choice charOpt = new Choice(charSet);
        Choice emptyOpt = new Choice(List.of(new Terminal("\t"),new Terminal("\n"),new Terminal(" ")));
        Repetition whiteSp = new Repetition(emptyOpt);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition name = new Repetition(charOpt);
        Repetition digits = new Repetition(digitOpt);
        RuleApplicaiton assign = new RuleApplicaiton("assign");
        RuleApplicaiton idRule = new RuleApplicaiton("name");
        RuleApplicaiton whiteSpRule = new RuleApplicaiton("whiteSpaces");
        RuleApplicaiton digitsRule = new RuleApplicaiton("digits");
        RuleApplicaiton digitRule = new RuleApplicaiton("digit");
        RuleApplicaiton lookAheadWhiteSpaces = new RuleApplicaiton("skipWhiteSpaces");
        /*
            TODO 当'WITHESPACES'规则在'ASSIGN'之前时,当句子里出现空格会导致解析失败
         */
        Choice ruleA = new Choice(List.of(assign,whiteSpRule,idRule));
        /*
            Start ->  ASSIGN | NAME | WITHESPACES
            ASSIGN -> NAME '=' DIGITS
            DIGITS -> ['0'-'9']+
            NAME -> ['a'-'z''A'-'Z']+
            WITHESPACES -> ['\t''\n']+
         */
        productionTable.put("start",ruleA);
        productionTable.put("assign",new Sequence(List.of(idRule,new RuleApplicaiton("whiteSp"),new Terminal("="),new RuleApplicaiton("whiteSp"),digitsRule)));
        productionTable.put("digits",new Sequence(List.of(digitRule,digits)));
        productionTable.put("digit",digitOpt);
        productionTable.put("name",new Sequence(List.of(charOpt,name)));
        productionTable.put("whiteSpaces",new Sequence(List.of(emptyOpt,whiteSp)));
        productionTable.put("whiteSp",whiteSp);
        productionTable.put("skipWhiteSpaces",new Not(new Not(whiteSpRule)));

        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
