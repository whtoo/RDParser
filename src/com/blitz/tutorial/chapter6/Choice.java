package com.blitz.tutorial.chapter6;

import java.util.List;

public class Choice implements IRuleApplication {
    List<IRuleApplication> exps;
    Choice(List<IRuleApplication> exps){
        this.exps = exps;
    }
    @Override
    public Object eval(Matcher matcher) {
        int origPos = matcher.pos;
        for (IRuleApplication exp : this.exps) {
            matcher.pos = origPos;
            Object cst = exp.eval(matcher);
            if(exp.ruleName() != null) {
                System.out.printf("eval rule %s \n", exp.ruleName());
            }
            if (cst != null) {
                return cst;
            }
        }
        return null;
    }

    @Override
    public String ruleName() {
        return null;
    }
}
