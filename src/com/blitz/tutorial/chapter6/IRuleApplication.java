package com.blitz.tutorial.chapter6;

import javax.imageio.event.IIOReadUpdateListener;
import java.util.List;

/**
 * 语法规则推导
 */
public interface IRuleApplication{
    /**
     * 返回解析得到的cst
     * @param matcher
     * @return
     */
    Object eval(Matcher matcher);

    /**
     * 测试是否跳过当前模式
     * @return
     */
    default Boolean shouldSkip(){
        return false;
    }
    /**
     *
     * @return
     */
    String ruleName();

    static IRuleApplication seq(IRuleApplication ...ruleApplications){
        return new Sequence(List.of(ruleApplications));
    }

    static IRuleApplication addtion(IRuleApplication ruleApplication){
        return new Sequence(List.of(ruleApplication,new Repetition(ruleApplication)));
    }

    static IRuleApplication choice(IRuleApplication ...ruleApplications){
        return new Choice(List.of(ruleApplications));
    }
    static IRuleApplication range(String start,String end) {
        return new Range(start,end);
    }
    static IRuleApplication repetition(IRuleApplication ruleApplications){
        return new Repetition(ruleApplications);
    }

    static IRuleApplication token(String str) {
        return new Terminal(str,true);
    }
    static IRuleApplication not(IRuleApplication ruleApplication){
        return new Not(ruleApplication);
    }
    static IRuleApplication rule(String ruleName,IReduction reduction) throws Exception {
        return new RuleApplicaiton(ruleName, reduction != null? reduction:(rule, startPos, offset, cst,rawImage) -> cst);
    }
    static IRuleApplication option(IRuleApplication ruleApplication){
        return new Option(ruleApplication);
    }
    static IRuleApplication sep(String str) {
        return new Skip(new Terminal(str));
    }
    static IRuleApplication skip(IRuleApplication ruleApplication) {return new Skip(ruleApplication);}
    static Not lookAhead(IRuleApplication ruleApplication){
        return new Not(new Not(ruleApplication));
    }

    static IRuleApplication lexer(String ruleName,IReduction reduction) throws Exception {
        return new RuleApplicaiton(ruleName, reduction,false,true);
    }
}
