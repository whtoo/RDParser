package com.blitz.tutorial.chapter6;


import com.blitz.tutorial.chapter6.ast.RangeNode;
import com.blitz.tutorial.chapter6.tokens.EOFToken;
import com.blitz.tutorial.chapter6.tokens.IdToken;
import com.blitz.tutorial.chapter6.tokens.OPToken;
import com.blitz.tutorial.chapter6.tokens.StrToken;

import static com.blitz.tutorial.chapter6.IRuleApplication.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * grammar -> productions
 * productions -> startRule OtherRules
 * startRule -> "start" arrow ruleBody
 * OtherRules -> {rule}*
 * rule -> identity "->" ruleBody
 * identity -> (alphabet)+
 * ruleBody -> (ruleEle)+ EOL
 * ruleEle -> (sequence | choice | repetition | addition | app | not | opt |skip | range | terminal | lookahead )
 * sequence ->  ruleBody (whitespace ruleBody)+
 * choice -> "(" ruleBody ("|" ruleBody)* ")"
 * repetition -> "(" ruleBody ")" "*"
 * addition -> "(" ruleBody ")" "+"
 * opt -> "[" "]"
 * range -> "<" (identity|integer)+ , (identity|integer)+ ">"
 * app -> identity
 * terminal -> (stringLiteral | charLiteral)
 * stringLiteral -> "\"" !("\"" | "\n") "\""
 * charLiteral -> '\'' !('\'' | '\m') '\''
 * lookahead -> "!!" ruleBody
 * not -> "!" ruleBody
 * whitespace -> ("\t" | " ")+
 * arrow -> "->"
 * alphabet -> (<'a','z'>|<'A','Z'>)
 * alnum -> (alphabet|<'0','9'>)
 * EOL -> "\n"
 */
public class TestCase {

    public static void main(String[] args) throws Exception {
        String subStr = Files.readString(Path.of(new File("src/com/blitz/tutorial/chapter6/main.cx").getAbsolutePath()));

        IRuleApplication digitSet = range("0","9");

        IRuleApplication charSet = choice(new Range("a","z"),new Range("A","Z"));
        IRuleApplication emptySet = choice(new Terminal("\t"),new Terminal(" "));
        IRuleApplication alnum = choice(charSet,digitSet);
        IRuleApplication strSet = addtion(seq(charSet,digitSet));
        //Binding with reserved symbol table
        IRuleApplication whiteSp = repetition(emptySet);
        Map productionTable = new HashMap<String,IRuleApplication>(3);

        IRuleApplication idRule = lexer("idRule", ((rule, startPos, offset, cst,rawImage) -> {
            return new IdToken(rawImage,startPos,offset);
        }));
        IRuleApplication digitsRule = lexer("digits", null);
        IRuleApplication digitRule = lexer("digit",null);
        IRuleApplication skipRule = rule("skipWhiteSpaces",(rule,startPos,offset,cst,rawImage)-> cst);

        IRuleApplication opRule = lexer("opRule",(rule, startPos, offset, cst,rawImage) -> {
            return new OPToken(rawImage,startPos,offset);
        });

        IRuleApplication stringLiteral = seq(sep("\""),not(choice(sep("\n"),sep("\""))),idRule,sep("\""));
        IRuleApplication charLiteral = seq(sep("\'"),not(choice(sep("\n"),sep("\'"))),idRule,sep("\'"));
        IRuleApplication productionRule = rule("productionRule",(rule,starPos,offset,cst,rawImage)-> {
            return cst;
        });

        IRuleApplication stringLiteralRule = lexer("stringLiteralRule",(rule,starPos,offset,cst,rawImage)-> {
            StrToken token = new StrToken(rawImage.substring(1,offset-starPos-1));
            token.setPos(starPos,offset);
            return token;
        });

        IRuleApplication charLiteralRule = lexer("charLiteralRule",(rule,starPos,offset,cst,rawImage)-> {
            StrToken token = new StrToken(rawImage.substring(1,offset-starPos-1));
            token.setPos(starPos,offset);
            return token;
        });

        IRuleApplication terminal = choice(stringLiteralRule,charLiteralRule);
        IRuleApplication terminalRule = lexer("terminalRule",(rule,starPos,offset,cst,rawImage)-> {
            return cst;
        });

        IRuleApplication startRule = rule("startRule",(rule,starPos,offset,cst,rawImage)-> {
            return  cst;
        });
/**
 * grammar -> productions
 * productions -> firstRule restRules
 * firstRule -> "start" arrow ruleBody
 * restRules -> {rule}*
 * ruleFormat -> identity "->" ruleBody
 * ruleBody -> (ruleEle)+ EOL
 * ruleEle -> (sequence | choice | repetition | addition | app | not | opt |skip | range | terminal | lookahead )
 * sequence ->  ruleBody (whitespace ruleBody)+
 * choice -> "(" ruleBody ("|" ruleBody)* ")"
 * repetition -> "(" ruleBody ")" "*"
 * addition -> "(" ruleBody ")" "+"
 * opt -> "(" ruleBody ")" "?" | ruleBody "?"
 * range -> "<" terminal "," terminal ">" ✅
 * app -> identity ✅
 * lookahead -> "!!" ruleBody
 * not -> "!" ruleBody
 */     IRuleApplication EOLRule = lexer("EOLRule",(rule, startPos, offset, cst, rawImage) -> new OPToken("\n"));
        IRuleApplication EOFRule = lexer("EOFRule", (rule, startPos, offset, cst, rawImage) -> new EOFToken());
        IRuleApplication rangeRule = lexer("rangeRule",(rule, startPos, offset, cst, rawImage) -> new RangeNode((List)cst));
        IRuleApplication ruleFormatRule = rule("ruleFormat",(rule, startPos, offset, cst, rawImage) -> cst);
        productionTable.put("start",startRule);
        productionTable.put("startRule",repetition(option(seq(productionRule,option(EOFRule)))));
        productionTable.put("terminalRule",terminal);
        productionTable.put("productionRule",addtion(seq(idRule,opRule,terminalRule,skip(EOLRule))));
        productionTable.put("charLiteralRule",charLiteral);
        productionTable.put("stringLiteralRule",stringLiteral);
        productionTable.put("digit",digitSet);
        productionTable.put("idRule",addtion(charSet));
        productionTable.put("whiteSpaces",seq(emptySet,whiteSp));
        productionTable.put("skipWhiteSpaces",new Skip(whiteSp));
        productionTable.put("stringLiteral",stringLiteral);
        productionTable.put("charLiteral",charLiteral);
        productionTable.put("skipRule",skipRule);
        productionTable.put("opRule",token("->"));
        productionTable.put("rangeRule",seq(sep("<"),terminalRule,sep(","),terminalRule,sep(">")));
        productionTable.put("EOLRule",sep("\n"));
        productionTable.put("EOFRule",token(String.valueOf(((char)-1))));
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);
    }
}
