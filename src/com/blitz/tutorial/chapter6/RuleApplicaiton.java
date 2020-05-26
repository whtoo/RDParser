package com.blitz.tutorial.chapter6;

public class RuleApplicaiton implements IRuleApplication{
    String _ruleName;

    RuleApplicaiton(String ruleName) {
        this._ruleName = ruleName;
    }

    @Override
    public Object eval(Matcher matcher) {
        String name = this._ruleName;
        if(matcher.hasMemorizedResult(name)){
            return matcher.useMemorizedResult(name);
        } else {
            Integer originalPos = matcher.pos;
            Object cst = matcher.rules.get(name).eval(matcher);

            matcher.memorizeResult(originalPos,name,cst);

            return cst;
        }
    }

    @Override
    public String ruleName() {
        return this._ruleName;
    }
}
