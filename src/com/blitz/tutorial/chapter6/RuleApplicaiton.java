package com.blitz.tutorial.chapter6;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if(cst != null) {
                /*
                TODO 增加语义动作(Semantic actions)
                 */
                if(cst instanceof List){
                    List castCst = (List) cst;
                    List flatCst = (List) castCst;
                    System.out.println(String.format("rule{%s} produce %s", name, flatCst));
                }
            }
            matcher.memorizeResult(originalPos,name,cst);

            return cst;
        }
    }

    @Override
    public String ruleName() {
        return this._ruleName;
    }
}
