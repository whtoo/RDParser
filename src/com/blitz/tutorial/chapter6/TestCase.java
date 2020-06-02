package com.blitz.tutorial.chapter6;


import com.blitz.tutorial.chapter5.ast.IdentifierNode;
import com.blitz.tutorial.chapter5.ast.NumberNode;
import com.blitz.tutorial.common.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.blitz.tutorial.chapter6.Not.LookAhead;
import static com.blitz.tutorial.chapter6.Terminal.*;

public class TestCase {

    public static void main(String[] args) throws Exception {
        String subStr = "a = 12123.f    ;";
        Range digitSet = new Range("0","9");
        Choice charSet = new Choice(List.of(new Range("a","z"),new Range("A","Z")));
        Choice emptySet = new Choice(List.of(new Terminal("\t"),new Terminal("\n"),new Terminal(" ")));

        //Binding with reserved symbol table

        Repetition whiteSp = new Repetition(emptySet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition name = new Repetition(charSet);
        Repetition digits = new Repetition(digitSet);
        Not lookAheadFloat = LookAhead(new Choice(List.of(digitSet,new Terminal("."))));
        RuleApplicaiton exprRule = new RuleApplicaiton("exprRule",((rule, startPos, offset, cst) -> {
            List cstStream = (List) cst;

            return cst;
        }));

        RuleApplicaiton factorRule = new RuleApplicaiton("factorRule",((rule, startPos, offset, cst) -> {
            if(cst instanceof Token){
                Token castToken = (Token)cst;
                if(castToken.isIdentity()){
                    return new IdentifierNode(castToken);
                } else if(castToken.isNumber()){
                    return new NumberNode(castToken);
                }
            }

            return  cst;
        }));
        RuleApplicaiton idRule = new RuleApplicaiton("name", ((rule, startPos, offset, cst) -> {
            return new Token(TokenEnum.IDENTIFIER,cst.toString(),startPos,offset);
        }), false, true);
        RuleApplicaiton digitsRule = new RuleApplicaiton("digits",null,false,true);
        RuleApplicaiton digitRule = new RuleApplicaiton("digit",null,false,true);
        RuleApplicaiton floatRule = new RuleApplicaiton("floatRule",null,false,true);
        RuleApplicaiton skipRule = new RuleApplicaiton("skipWhiteSpaces",(rule,startPos,offset,cst)-> cst);
        RuleApplicaiton numRule = new RuleApplicaiton("numRule",((rule, startPos, offset, cst) -> {
            if(cst.toString().matches("\\.|f")){
                return new Token(TokenEnum.FLOAT,cst.toString(),startPos,offset);
            } else {
                return new Token(TokenEnum.INT,cst.toString(),startPos,offset);
            }
        }),false, true);
        RuleApplicaiton opRule = new RuleApplicaiton("opRule",((rule, startPos, offset, cst) -> {
            return new Token(TokenEnum.OPERATOR,cst.toString(),startPos,offset);
        }),false,true);
        /*
            DONE 重写语法规则，适应可能最长匹配模式优先
         */
        Repetition startRule = new Repetition(new Sequence(List.of(exprRule,sep(";"))));

        productionTable.put("start",startRule);
        productionTable.put("exprRule",new Sequence(List.of(factorRule,new Repetition(new Sequence(List.of(opRule,factorRule))))));
        productionTable.put("factorRule",new Choice(List.of(idRule,numRule,new Sequence(List.of(sep("("),exprRule,sep(")"))))));
        productionTable.put("digits",new Sequence(List.of(digitRule,digits)));
        productionTable.put("digit",digitSet);
        productionTable.put("name",new Sequence(List.of(charSet,name)));
        productionTable.put("whiteSpaces",new Sequence(List.of(emptySet,whiteSp)));
        productionTable.put("skipWhiteSpaces",new Skip(whiteSp));
        productionTable.put("skipRule",skipRule);
        productionTable.put("floatRule",new Option(new Sequence(List.of(new Terminal("."),new Option(digitsRule),new Option(new Terminal("f"))))));
        productionTable.put("numRule",new Sequence(List.of(lookAheadFloat,new Option(digitsRule),floatRule)));
//        Matcher matcher = new Matcher(productionTable);
//        Object result = matcher.match(subStr);
//        System.out.println(result);
        for (Object ruleName:productionTable.entrySet()
             ) {
            System.out.println(ruleName);
        }

        //TODO: 增加语法字面量定义
   }
}
