package com.blitz.tutorial.chapter6;


import com.blitz.tutorial.chapter5.ast.IdentifierNode;
import com.blitz.tutorial.chapter5.ast.NumberNode;
import com.blitz.tutorial.common.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.blitz.tutorial.chapter6.Terminal.*;

public class TestCase {
    /**
            START -> [ STMT ]opt (; | EOL)
            STMT -> EXPR { EXPR }
            EXPR -> TERM "=" EXPR1 | TERM OP_ASSIGN EXPR1 | EXPR10
            EXPR10 -> FACTOR {OP FACTOR}
            TERM ->
            FACTOR -> NUMBER | NAME | "(" EXPR ")"
            DIGITS -> ['0'-'9']+
            NAME -> ['a'-'z''A'-'Z']+
            WHITESPACES -> ['\t'' ''\n']+
            NUMBER -> DIGITS [ '.' DIGITS ]
     **/
    public static void main(String[] args) throws Exception {

        String subStr = "a = 12123.f    ;";
        Range digitSet = new Range("0","9");
        Choice charSet = new Choice(List.of(new Range("a","z"),new Range("A","Z")));
        Choice emptySet = new Choice(List.of(new Terminal("\t"),new Terminal("\n"),new Terminal(" ")));
        // Binding with operator analysis
        Choice opSet = new Choice(List.of(
                token("="),
                token("+"),
                token("-"),
                token("*"),
                token("/"),
                token("%")));

        //Binding with reserved symbol table

        Repetition whiteSp = new Repetition(emptySet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);
        Repetition name = new Repetition(charSet);
        Repetition digits = new Repetition(digitSet);
        Not lookAhead = new Not(new Not(new Choice(List.of(digitSet,new Terminal(".")))));
        /*
        删掉两条冗余规则，因为其在推导中无用
         */
        RuleApplicaiton exprRule = new RuleApplicaiton("exprRule",((rule, startPos, offset, cst) -> {
            List cstStream = (List) cst;

            return cst;
        }));
        RuleApplicaiton factorRule = new RuleApplicaiton("factorRule",((rule, startPos, offset, cst) -> {
            if(cst instanceof Token){
                Token castToken = (Token)cst;
                if(castToken.type == TokenEnum.IDENTIFIER.ordinal()){
                    return new IdentifierNode(castToken);
                } else if(castToken.type == TokenEnum.INT.ordinal()){
                    return new NumberNode(castToken);
                } else if(castToken.type == TokenEnum.FLOAT.ordinal()){
                    return new NumberNode(castToken);
                }
            }

            return  cst;
        }));
        RuleApplicaiton idRule = new RuleApplicaiton("name", ((rule, startPos, offset, cst) -> {
            return new Token(TokenEnum.IDENTIFIER.ordinal(),cst.toString(),startPos,offset);
        }), false, true);
        RuleApplicaiton digitsRule = new RuleApplicaiton("digits",null,false,true);
        RuleApplicaiton digitRule = new RuleApplicaiton("digit",null,false,true);
        RuleApplicaiton floatRule = new RuleApplicaiton("floatRule",null,false,true);
        RuleApplicaiton skipRule = new RuleApplicaiton("skipWhiteSpaces",(rule,startPos,offset,cst)-> cst);
        RuleApplicaiton numRule = new RuleApplicaiton("numRule",((rule, startPos, offset, cst) -> {
            if(cst.toString().matches("\\.|f")){
                return new Token(TokenEnum.FLOAT.ordinal(),cst.toString(),startPos,offset);
            } else {
                return new Token(TokenEnum.INT.ordinal(),cst.toString(),startPos,offset);
            }
        }),false, true);
        RuleApplicaiton opRule = new RuleApplicaiton("opRule",((rule, startPos, offset, cst) -> {
            return new Token(TokenEnum.IDENTIFIER.ordinal(),cst.toString(),startPos,offset);
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
        productionTable.put("numRule",new Sequence(List.of(lookAhead,new Option(digitsRule),floatRule)));
        productionTable.put("opRule",opSet);
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);

    }
}
