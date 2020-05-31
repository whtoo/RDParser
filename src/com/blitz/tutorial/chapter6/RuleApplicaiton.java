package com.blitz.tutorial.chapter6;

import com.blitz.tutorial.chapter5.ast.AstNode;
import com.blitz.tutorial.chapter5.ast.IdentifierNode;
import com.blitz.tutorial.chapter5.ast.NumberNode;
import com.blitz.tutorial.chapter5.ast.TerminalNode;
import com.blitz.tutorial.common.Token;
import org.antlr.v4.tool.Rule;

import java.util.List;
import java.util.stream.Stream;

public class RuleApplicaiton implements IRuleApplication{
    String _ruleName;
    Boolean shouldSkip;
    Boolean isLexical;
    IReduction reduction;
    RuleApplicaiton(String ruleName) {
        this(ruleName,null);
    }
    RuleApplicaiton(String ruleName,IReduction reduction) {
        this(ruleName,reduction,false);
    }

    RuleApplicaiton(String ruleName,IReduction reduction,Boolean shouldSkip){
        this(ruleName,reduction,shouldSkip,false);
    }
    RuleApplicaiton(String ruleName,IReduction reduction,Boolean shouldSkip,Boolean isLexical){
        this._ruleName = ruleName;
        this.shouldSkip = shouldSkip;
        this.isLexical = isLexical;
        this.reduction = reduction;
    }
    @Override
    public Object eval(Matcher matcher) {
        String name = this._ruleName;
        if(matcher.hasMemorizedResult(name)){
            return matcher.useMemorizedResult(name);
        } else {
            Integer originalPos = matcher.pos;
            IRuleApplication ruleApplication = matcher.rules.get(name);
            if(ruleApplication instanceof RuleApplicaiton) {
                RuleApplicaiton ruleApplicaitonRef = (RuleApplicaiton)ruleApplication;
                matcher.isLexical = ruleApplicaitonRef.isLexicalDef();
            }

            Object cst = ruleApplication.eval(matcher);

            if(cst != null) {
                /*
                DONE 增加语义动作(Semantic actions)
                DONE 增加skip并实现规约
                */
               if(this.isLexical){
                   //词法归并，规约动作为flatten + concat -> string
                   if(this.reduction != null){
                       cst = this.reduction.reduceParser(this,originalPos,matcher.pos,matcher.input.substring(originalPos,matcher.pos));
                   } else {
                       cst = matcher.input.substring(originalPos,matcher.pos);
                   }
               } else {
                   //语法规约
                   if(this.reduction != null){
                        cst = this.reduction.reduceParser(this,originalPos,matcher.pos,cst);
                   }
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
       return parserTree;
    }

    protected Object buildTerminalNode(String type,String terminalSymbol,int start,int end) {
        return new TerminalNode(new Token(TokenEnum.STRING.ordinal(),terminalSymbol,start,end));
    }
}
