package com.blitz.tutorial.chapter6;

public class Not implements IRuleApplication {
    IRuleApplication exp;
    Not(IRuleApplication exp){
        this.exp = exp;
    }

    @Override
    public Object eval(Matcher matcher) {
        int origPos = matcher.pos;
        if(this.exp.eval(matcher) == null){
            matcher.pos = origPos;
            return true;
        }
        return null;
    }
}
