package com.blitz.tutorial.chapter6;

import com.blitz.tutorial.chapter5.ast.AstNode;
import com.blitz.tutorial.chapter5.ast.IdentifierNode;
import com.blitz.tutorial.chapter5.ast.NumberNode;
import com.blitz.tutorial.chapter5.ast.TerminalNode;
import com.blitz.tutorial.common.Token;

import java.util.List;
import java.util.stream.Stream;

public class RuleApplicaitonBk implements IRuleApplication{
    String _ruleName;
    Boolean shouldSkip;
    Boolean isLexical;

    RuleApplicaitonBk(String ruleName) {
        this(ruleName,false);
    }

    RuleApplicaitonBk(String ruleName, Boolean shouldSkip){
        this(ruleName,shouldSkip,false);
    }
    RuleApplicaitonBk(String ruleName, Boolean shouldSkip, Boolean isLexical){
        this._ruleName = ruleName;
        this.shouldSkip = shouldSkip;
        this.isLexical = isLexical;
    }
    @Override
    public Object eval(Matcher matcher) {
        String name = this._ruleName;
        if(matcher.hasMemorizedResult(name)){
            return matcher.useMemorizedResult(name);
        } else {
            Integer originalPos = matcher.pos;
            IRuleApplication ruleApplication = matcher.rules.get(name);
            if(ruleApplication instanceof RuleApplicaitonBk) {
                RuleApplicaitonBk ruleApplicaitonRef = (RuleApplicaitonBk)ruleApplication;
                matcher.isLexical = ruleApplicaitonRef.isLexicalDef();
            }

            Object cst = ruleApplication.eval(matcher);

            if(cst != null) {
                /*
                TODO 增加语义动作(Semantic actions)
                DONE 增加skip并实现规约
                 */
                if(cst instanceof List){
                    cst = buildNode(name,(List<Object>) cst,originalPos,matcher.pos);
                    System.out.println(String.format("rule{%s} produce %s", name, cst));
                } else if(cst instanceof String && name.equals("opRule")) {
                    cst = buildTerminalNode(name,((String) cst),originalPos,matcher.pos);
                    System.out.println(String.format("rule{%s} produce %s", name, cst));
                }
            }
            matcher.memorizeResult(originalPos,name,cst);

            return cst;
        }
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return this.shouldSkip;
    }

    public Boolean isLexicalDef() { return this.isLexical; }
    @Override
    public String ruleName() {
        return this._ruleName;
    }
    protected Object buildNode(String type,List<Object> parserTree,int start,int end) {

        if(type.equals("name")){
            Object name = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).get();
            return new IdentifierNode(new Token(TokenEnum.STRING.ordinal(),name.toString(),start,end),AstNode.LEAFLTYPE);
        } else if(type.equals("numRule")){
            Object numberObj = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).get();
            String numLiteral = numberObj.toString();
            if(numLiteral.contains(".f")){
                return new NumberNode(new Token(TokenEnum.FLOAT.ordinal(),numLiteral,start,end),AstNode.LEAFLTYPE);
            } else {
                return new NumberNode(new Token(TokenEnum.INT.ordinal(),numLiteral,start,end),AstNode.LEAFLTYPE);
            }
        } else if(type.equals("exprRule")) {


        } else if(type.equals("factorRule")){

        } else if(type.equals("digits")) {
            Object digits = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).orElse("");
            return digits.toString();
        } else if(type.equals("floatRule")) {
            Object digits = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).orElse("");
            String rawText = (digits != null)?digits.toString():"";
            rawText = rawText.replaceAll("\\[*\\]*","");
            return rawText;
        } else if(type.equals("opRule")) {
            Object digits = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).orElse("");
            String rawText = digits.toString().replaceAll("\\[*\\]*","");
            return rawText;
        } else if(type.equals("optKeywordRule")) {
            Object digits = parserTree.stream().flatMap(x -> (x instanceof List)?((List) x).stream():Stream.of(x)).reduce((x, y) -> String.valueOf(x).concat(String.valueOf(y))).orElse("");
            String rawText = digits.toString().replaceAll("\\[*\\]*","");
            return rawText;
        }
        return parserTree;
    }

    protected Object buildTerminalNode(String type,String terminalSymbol,int start,int end) {
        return new TerminalNode(new Token(TokenEnum.STRING.ordinal(),terminalSymbol,start,end));
    }
}
