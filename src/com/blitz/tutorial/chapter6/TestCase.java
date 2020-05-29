package com.blitz.tutorial.chapter6;

import org.antlr.v4.tool.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCase {
    /**
            START -> [ STMT ]opt (; | EOL)
            STMT -> EXPR { EXPR }
            EXPR -> FACTOR {OP FACTOR}
            DIGITS -> ['0'-'9']+
            NAME -> ['a'-'z''A'-'Z']+
            WHITESPACES -> ['\t'' ''\n']+
            NUMBER -> DIGITS [ '.' DIGITS ]
            FACTOR -> NUMBER | NAME | "(" EXPR ")"
     **/
    public static void main(String[] args) {
        int a = +1,b = 1,c = 2;
        a = b += c=1;
        System.out.printf("a:%d b:%d c:%d \n",a,b,c);
        String subStr = " idStr = 112  ;x = 177;";
        Range digitSet = new Range("0","9");
        Choice charSet = new Choice(List.of(new Range("a","z"),new Range("A","Z")));
        Choice emptySet = new Choice(List.of(new Terminal("\t"),new Terminal("\n"),new Terminal(" ")));
        // Binding with operator analysis
        Choice opSet = new Choice(List.of(
                new Terminal("+"),
                new Terminal("="),
                new Terminal("-"),
                new Terminal("*"),
                new Terminal("/"),
                new Terminal("<"),
                new Terminal(">"),
                new Terminal("="),
                new Terminal("<="),
                new Terminal(">=")));
        //Binding with reserved symbol table


        Repetition whiteSp = new Repetition(emptySet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition name = new Repetition(charSet);
        Repetition digits = new Repetition(digitSet);
        /*
        删掉两条冗余规则，因为其在推导中无用
         */
        RuleApplicaiton assign = new RuleApplicaiton("assign");
        RuleApplicaiton idRule = new RuleApplicaiton("name",false,true);
        RuleApplicaiton digitsRule = new RuleApplicaiton("digits",false,true);
        RuleApplicaiton digitRule = new RuleApplicaiton("digit",false,true);
        RuleApplicaiton floatRule = new RuleApplicaiton("floatRule",false,true);
        RuleApplicaiton numRule = new RuleApplicaiton("numRule",false,true);
        RuleApplicaiton opRule = new RuleApplicaiton("opRule",false,true);
        /*
            TODO 当'WITHESPACES'规则在'ASSIGN'之前时,当句子里出现空格会导致解析失败
            DONE 重写语法规则，适应可能最长匹配模式优先
         */
        Choice ruleA = new Choice(List.of(new Repetition(assign),idRule,numRule));

        productionTable.put("start",ruleA);
        productionTable.put("assign",new Sequence(
                List.of(
                        idRule,
                        opRule,
                        numRule,
                        new Terminal(";",true))
                ));
        productionTable.put("digits",new Sequence(List.of(digitRule,digits)));
        productionTable.put("digit",digitSet);
        productionTable.put("name",new Sequence(List.of(charSet,name)));
        productionTable.put("whiteSpaces",new Sequence(List.of(emptySet,whiteSp)));
        productionTable.put("skipWhiteSpaces",new Skip(whiteSp));
        productionTable.put("floatRule",new Option(new Sequence(List.of(new Terminal("."),new Option(digitsRule),new Option(new Terminal("f"))))));
        productionTable.put("numRule",new Sequence(List.of(new Option(digitsRule),floatRule)));
        productionTable.put("opRule",opSet);
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
