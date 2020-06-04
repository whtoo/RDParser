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
    RuleApplicaiton(String ruleName) throws Exception {
        this(ruleName,null);
    }
    RuleApplicaiton(String ruleName,IReduction reduction) throws Exception {
        this(ruleName,reduction,false);
    }

    RuleApplicaiton(String ruleName,IReduction reduction,Boolean shouldSkip)throws Exception{
        this(ruleName,reduction,shouldSkip,false);
    }
    RuleApplicaiton(String ruleName,IReduction reduction,Boolean shouldSkip,Boolean isLexical) throws Exception {
        this._ruleName = ruleName;
        this.shouldSkip = shouldSkip;
        this.isLexical = isLexical;
        this.reduction = reduction;
        if(isLexical == false && reduction == null){
            throw new Exception("Undefined reduction for "+ruleName);
        }
    }
    @Override
    public Object eval(Matcher matcher) {
        String name = this._ruleName;
        if(matcher.hasMemorizedResult(name)){
            return matcher.useMemorizedResult(name);
        } else {
            Integer originalPos = matcher.pos;

            IRuleApplication ruleApplicaiton = matcher.rules.get(name);
            Boolean isLexicalBk = matcher.isLexical;
            matcher.isLexical = this.isLexicalDef();

            Object cst = ruleApplicaiton.eval(matcher);
            System.out.printf("eval rule %s %s \n",this.ruleName(),cst);
            if(cst != null) {
                /*
                DONE 增加语义动作(Semantic actions)
                DONE 增加skip并实现规约
                */
               if(this.isLexical){
                   //词法归并，规约动作为flatten + concat -> string
                   if(this.reduction != null){
                       cst = this.reduction.reduceParser(this,originalPos,matcher.pos,cst,matcher.input.substring(originalPos,matcher.pos));
                   } else {
                       cst = matcher.input.substring(originalPos,matcher.pos);
                   }
               } else {
                   //语法规约
                   if(this.reduction != null){
                        cst = this.reduction.reduceParser(this,originalPos,matcher.pos,cst,matcher.input.substring(originalPos,matcher.pos));
                   }
               }
            }
            matcher.memorizeResult(originalPos,name,cst);
            matcher.isLexical = isLexicalBk;
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
        return new TerminalNode(new Token(TokenEnum.STRING,terminalSymbol,start,end));
    }

    @Override
    public String toString() {
        return this._ruleName;
    }

}
