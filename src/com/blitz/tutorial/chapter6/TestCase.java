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


        IRuleApplication digitsRule = lexer("digits", null);
        IRuleApplication digitRule = lexer("digit",null);
        IRuleApplication skipRule = rule("skipWhiteSpaces",(rule,startPos,offset,cst,rawImage)-> cst);

        IRuleApplication arrowRule = lexer("arrowRule",(rule, startPos, offset, cst,rawImage) -> {
            return new OPToken(rawImage,startPos,offset);
        });

        IRuleApplication stringLiteral = seq(sep("\""),exclude(choice(sep("\n"),sep("\""))),sep("\""));
        IRuleApplication charLiteral = seq(sep("\'"),exclude(choice(sep("\n"),sep("\'"))),sep("\'"));

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

        IRuleApplication idRule = lexer("idRule", ((rule, startPos, offset, cst,rawImage) -> {
            return new IdToken(rawImage,startPos,offset);
        }));
        IRuleApplication startRule = rule("startRule",(rule,starPos,offset,cst,rawImage)-> {
            return  new IdToken(rawImage);
        });
/**
 * grammar -> productions☑️
 * productions -> firstRule restRules☑️
 * firstRule -> "start" arrow productionBody☑️
 * restRules -> {production}*☑️
 * production -> identity arrow ruleBody ✅ --- current work target
 * choice -> "(" ruleBody ("|" ruleBody)* ")"✅
 * repetition -> "(" ruleBody ")" "*"✅
 * addition -> "(" ruleBody ")" "+"☑️
 * opt -> "(" ruleBody ")" "?" | ruleBody "?"☑️
 * range -> "<" terminal "," terminal ">" ✅
 * app -> identity ✅
 * lookahead -> "!!" ruleBody☑️
 * not -> "!" ruleBody☑️
 */     IRuleApplication EOLRule = lexer("EOLRule",(rule, startPos, offset, cst, rawImage) -> new OPToken("\n"));
        IRuleApplication EOFRule = lexer("EOFRule", (rule, startPos, offset, cst, rawImage) -> new EOFToken());

        IRuleApplication productionRule = rule("productionRule",(rule,starPos,offset,cst,rawImage)-> {
            return cst;
        });
        IRuleApplication productionBodyRule = rule("productionBodyRule",(rule, startPos, offset, cst, rawImage) -> cst);
        IRuleApplication productionElementRule = rule("productionElementRule",(rule, startPos, offset, cst, rawImage)->cst);
        IRuleApplication seqRule = rule("seqRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication repRule = rule("repRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication choiceRule = rule("choiceRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication additionRule = rule("additionRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication optRule = rule("optRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication rangeRule = lexer("rangeRule",(rule, startPos, offset, cst, rawImage) -> new RangeNode((List)cst));
        IRuleApplication appRule = rule("appRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication lookAheadRule = rule("lookAheadRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication notRule = rule("notRule",((rule, startPos, offset, cst, rawImage) -> cst));
        IRuleApplication terminalRule = lexer("terminalRule",(rule,starPos,offset,cst,rawImage)-> {
            return cst;
        });
        IRuleApplication nonSeqElementRule = rule("nonSeqElementRule",(((rule, startPos, offset, cst, rawImage) -> cst)));
        IRuleApplication whiteSpacesRule = rule("whiteSpacesRule",((rule, startPos, offset, cst, rawImage) -> cst));
        productionTable.put("start",startRule);

        productionTable.put("startRule",seq(productionRule,option(EOFRule)));

        productionTable.put("terminalRule",terminal);
        // productionRule -> idRule "->" productionBodyRule
        productionTable.put("productionRule",addtion(seq(idRule,arrowRule,productionBodyRule)));
        // productionBodyRule -> productionElementRule+ "\n"
        productionTable.put("productionBodyRule",seq(choice(seqRule,nonSeqElementRule),option(EOLRule)));
        // nonSeqElementRule -> (choice | repetition | addition | app | not | opt |skip | range | terminal | lookahead )
        productionTable.put("nonSeqElementRule",choice(terminalRule,appRule,rangeRule,choiceRule,repRule,additionRule,optRule,lookAheadRule,notRule));
        // productionElementRule -> (sequence | choice | repetition | addition | app | not | opt |skip | range | terminal | lookahead )✅
        productionTable.put("productionElementRule",seq(lookAhead(choice(sep("("),sep("!"),sep("!!"),sep("\""),sep("\'"),charSet)),choice(terminalRule,appRule,rangeRule,seqRule,choiceRule,repRule,additionRule,optRule,lookAheadRule,notRule)));
        // sequence ->  ruleBody (whitespace ruleBody)+✅
        productionTable.put("seqRule",addtion(nonSeqElementRule));
        // choice -> "(" ruleBody ("|" ruleBody)* ")"✅
        productionTable.put("choiceRule",seq(sep("("),productionElementRule,repetition(seq(sep("|"),productionElementRule)),sep(")")));
        // repetition -> "(" ruleBody ")" "*" | ruleBody "*"✅
        productionTable.put("repRule",choice(seq(sep("("),productionElementRule,sep(")"),sep("*")),seq(productionElementRule,sep("*"))));
        // addition -> "(" ruleBody ")" "+" | ruleBody "+"✅
        productionTable.put("additionRule",choice(seq(sep("("),productionElementRule,sep(")"),sep("+")),seq(productionElementRule,sep("+"))));
        // opt -> "(" ruleBody ")" "?" | ruleBody "?"✅
        productionTable.put("optRule",choice(seq(sep("("),productionElementRule,sep(")"),sep("?")),seq(productionElementRule,sep("?"))));
        // * range -> "<" terminal "," terminal ">" ✅
        productionTable.put("rangeRule",seq(sep("<"),terminalRule,sep(","),terminalRule,sep(">")));
        // * app -> identity ✅
        productionTable.put("appRule",idRule);
        // * lookahead -> "!!" ruleBody✅
        productionTable.put("lookAheadRule",seq(sep("!!"),productionElementRule));
        // * not -> "!" ruleBody✅
        productionTable.put("notRule",seq(sep("!"),productionElementRule));
        productionTable.put("charLiteralRule",charLiteral);
        productionTable.put("stringLiteralRule",stringLiteral);
        productionTable.put("digit",digitSet);
        productionTable.put("idRule",addtion(charSet));
        productionTable.put("whiteSpacesRule",addtion(emptySet));
        productionTable.put("skipWhiteSpaces",new Skip(whiteSp));
        productionTable.put("stringLiteral",stringLiteral);
        productionTable.put("charLiteral",charLiteral);
        productionTable.put("skipRule",skipRule);
        productionTable.put("arrowRule",token("->"));
        productionTable.put("rangeRule",seq(sep("<"),terminalRule,sep(","),terminalRule,sep(">")));
        productionTable.put("EOLRule",sep("\n"));
        productionTable.put("EOFRule",token(String.valueOf(((char)-1))));
        Matcher matcher = new Matcher(productionTable);
        Object result = matcher.match(subStr);
        System.out.println(result);
    }
}
