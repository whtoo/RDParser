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
        String subStr = "idStr = 12.2f;";

        Range digitOpt = new Range("0","9");
        Choice charOpt = new Choice(List.of(new Range("a","z"),new Range("A","Z")));
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
        RuleApplicaiton floatRule = new RuleApplicaiton("floatRule");
        RuleApplicaiton numRule = new RuleApplicaiton("numRule");
        /*
            TODO 当'WITHESPACES'规则在'ASSIGN'之前时,当句子里出现空格会导致解析失败
            DONE 重写语法规则，适应可能最长匹配模式优先
         */
        Choice ruleA = new Choice(List.of(assign,whiteSpRule,idRule,numRule));
        /*
            Start ->  ASSIGN | NAME | WITHESPACES | NUMBER
            ASSIGN -> NAME {WITHESPACES} '=' {WITHESPACES} NUMBER ';'
            DIGITS -> ['0'-'9']+
            NAME -> ['a'-'z''A'-'Z']+
            WITHESPACES -> ['\t''\n']+
            NUMBER -> DIGITS {'.' DIGITS}
         */
        productionTable.put("start",ruleA);
        productionTable.put("assign",new Sequence(
                List.of(idRule,
                new RuleApplicaiton("whiteSp"),
                        new Terminal("="),
                        new RuleApplicaiton("whiteSp"),
                        numRule,
                        new RuleApplicaiton("whiteSp"),
                        new Terminal(";"))
                ));
        productionTable.put("digits",new Sequence(List.of(digitRule,digits)));
        productionTable.put("digit",digitOpt);
        productionTable.put("name",new Sequence(List.of(charOpt,name)));
        productionTable.put("whiteSpaces",new Sequence(List.of(emptyOpt,whiteSp)));
        productionTable.put("whiteSp",whiteSp);
        productionTable.put("skipWhiteSpaces",new Not(new Not(whiteSpRule)));
        productionTable.put("floatRule",new Option(new Sequence(List.of(new Terminal("."),new Option(digitRule),new Option(new Terminal("f"))))));
        productionTable.put("numRule",new Sequence(List.of(digitsRule,floatRule)));

        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
