package com.blitz.tutorial.chapter6;

import java.util.List;
import java.util.stream.Collectors;

public class Choice implements IRuleApplication {
    List<IRuleApplication> exps;
    Boolean shouldSkip;

    Choice(List<IRuleApplication> exps){
        this(exps,false);
    }

    Choice(List<IRuleApplication> exps,Boolean shouldSkip){
        this.exps = exps;
        this.shouldSkip = shouldSkip;
    }

    @Override
    public Object eval(Matcher matcher) {
        int origPos = matcher.pos;
        for (IRuleApplication exp : this.exps) {

            matcher.pos = origPos;
            Object cst = exp.eval(matcher);
            if(exp.ruleName() != null && cst != null) {
                System.out.printf("eval rule %s %s \n", exp.ruleName(),cst.toString());
            }
            if (cst != null) {
                return cst;
            }
        }
        return null;
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return shouldSkip;
    }

    @Override
    public String ruleName() {
        return null;
    }

    @Override
    public String toString() {
        return "("+this.exps.stream().map(v -> v.toString() ).collect(Collectors.joining("/"))+")";
    }
}
