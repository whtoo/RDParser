package com.blitz.tutorial.chapter6;

import org.antlr.v4.tool.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase {
    /**
            START -> EXPR+
            EXPR -> FACTOR {OP FACTOR}
            EXPR -> NAME {WITHESPACES} '=' {WITHESPACES} NUMBER ';'
            DIGITS -> ['0'-'9']+
            NAME -> ['a'-'z''A'-'Z']+
            WITHESPACES -> ['\t''\n']+
            NUMBER -> DIGITS {'.' DIGITS}
     **/
    public static void main(String[] args) {
        String subStr = "idStr = 112.f;x = 0.168;";

//                "var x = 12;" +
//                "x+=78;" +
//                "if(x>12){" +
//                "x-=12;}" ;

        Range digitSet = new Range("0","9");
        Choice charSet = new Choice(List.of(new Range("a","z"),new Range("A","Z")));
        Choice emptySet = new Choice(List.of(new Terminal("\t"),new Terminal("\n"),new Terminal(" ")));
        Choice opSet = new Choice(List.of(new Terminal("+"),new Terminal("="),new Terminal("-"),new Terminal("*"),new Terminal("/")
                , new Terminal(".")));
        Repetition whiteSp = new Repetition(emptySet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition name = new Repetition(charSet);
        Repetition digits = new Repetition(digitSet);
        RuleApplicaiton assign = new RuleApplicaiton("assign");
        RuleApplicaiton idRule = new RuleApplicaiton("name");
        RuleApplicaiton whiteSpRule = new RuleApplicaiton("whiteSpaces");
        RuleApplicaiton digitsRule = new RuleApplicaiton("digits");
        RuleApplicaiton digitRule = new RuleApplicaiton("digit");
        RuleApplicaiton lookAheadWhiteSpaces = new RuleApplicaiton("skipWhiteSpaces");
        RuleApplicaiton floatRule = new RuleApplicaiton("floatRule");
        RuleApplicaiton numRule = new RuleApplicaiton("numRule");
        RuleApplicaiton opRule = new RuleApplicaiton("opRule");
        /*
            TODO 当'WITHESPACES'规则在'ASSIGN'之前时,当句子里出现空格会导致解析失败
            DONE 重写语法规则，适应可能最长匹配模式优先
         */
        Choice ruleA = new Choice(List.of(new Repetition(assign),whiteSpRule,idRule,numRule));

        productionTable.put("start",ruleA);
        productionTable.put("assign",new Sequence(
                List.of(idRule,
                new RuleApplicaiton("whiteSp"),
                        opRule,
                        new RuleApplicaiton("whiteSp"),
                        numRule,
                        new RuleApplicaiton("whiteSp"),
                        new Terminal(";"))
                ));
        productionTable.put("digits",new Sequence(List.of(digitRule,digits)));
        productionTable.put("digit",digitSet);
        productionTable.put("name",new Sequence(List.of(charSet,name)));
        productionTable.put("whiteSpaces",new Sequence(List.of(emptySet,whiteSp)));
        productionTable.put("whiteSp",whiteSp);
        productionTable.put("skipWhiteSpaces",new Not(new Not(whiteSpRule)));
        productionTable.put("floatRule",new Option(new Sequence(List.of(opRule,new Option(digitsRule),new Option(new Terminal("f"))))));
        productionTable.put("numRule",new Sequence(List.of(new Option(digitsRule),floatRule)));
        productionTable.put("opRule",opSet);
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
