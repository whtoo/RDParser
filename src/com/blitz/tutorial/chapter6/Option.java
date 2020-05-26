package com.blitz.tutorial.chapter6;

import java.util.ArrayList;
import java.util.List;

/**
 * Only once matched or non-matched.
 */
public class Option implements IRuleApplication {
    IRuleApplication expr;


    Option(IRuleApplication rules){
        this.expr = rules;
    }

    /**
     *
     * @param matcher
     * @return [] | [Expr...] | null
     */
    @Override
    public Object eval(Matcher matcher) {
        int matchCount = 0;
        List<Object> ans = new ArrayList(2);
        while (true){
            Object cst = this.expr.eval(matcher);

            if (cst != null){
                ans.add(cst);
                matchCount++;
            } else {
                break;
            }

            if (matchCount > 1) return null;;
        }

        return ans;
    }

    @Override
    public String ruleName() {
        return null;
    }
}
