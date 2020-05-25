package com.blitz.tutorial.chapter6;

import java.util.ArrayList;
import java.util.List;

public class Sequence implements IRuleApplication {
    List<IRuleApplication> exps;
    Sequence(List<IRuleApplication> exps){
        this.exps = exps;
    }
    @Override
    public Object eval(Matcher matcher) {
        List<Object> ans = new ArrayList<>(3);
        for (IRuleApplication exp : this.exps) {
            Object cst = exp.eval(matcher);
            if (cst == null) {
                return null;
            }
            if (!(exp instanceof Not)) {
                ans.add(cst);
            }
        }
        return ans;
    }

    @Override
    public String ruleName() {
        return null;
    }
}
