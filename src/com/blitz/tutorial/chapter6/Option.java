package com.blitz.tutorial.chapter6;

import javax.imageio.event.IIOReadUpdateListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Only once matched or non-matched.
 */
public class Option implements IRuleApplication {
    IRuleApplication expr;
    Boolean shouldSkip;

    Option(IRuleApplication rules){
        this(rules,false);
    }
    Option(IRuleApplication rules,Boolean shouldSkip) {
        this.expr = rules;
        this.shouldSkip = shouldSkip;
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
            int origPos = matcher.pos;
            Object cst = this.expr.eval(matcher);
            if (cst != null ){
                if(!this.expr.shouldSkip()){
                    if(cst instanceof List && ((List) cst).isEmpty()) continue;
                    if(cst instanceof List) {
                        ans.addAll((Collection<?>) cst);
                    } else {
                        ans.add(cst);

                    }
                }
                matchCount++;
            } else {

                matcher.pos = origPos;
                break;
            }

            if (matchCount > 1) return null;
        }

        return ans;
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

    @Override
    public String ruleName() {
        return null;
    }

    @Override
    public String toString() {
        return "["+ this.expr.toString() +"]";
    }
}
