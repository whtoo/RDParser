package com.blitz.tutorial.chapter6;

public class RuleApplicaiton implements IRuleApplication{
    String ruleName;
    RuleApplicaiton(String ruleName) {
        this.ruleName = ruleName;
    }

    @Override
    public Object eval(Matcher matcher) {
        String name = this.ruleName;
        if(matcher.hasMemorizedResult(name)){
            return matcher.useMemorizedResult(name);
        } else {
            Integer originalPos = matcher.pos;
            Object cst = matcher.rules.get(name).eval(matcher);
            matcher.memorizeResult(originalPos,name,cst);
            return cst;
        }
    }
}
