package com.blitz.tutorial.chapter6;

public class Exclude implements IRuleApplication {
    IRuleApplication exp;

    public Exclude(IRuleApplication ruleApplication){
        this.exp = IRuleApplication.not(ruleApplication);
    }

    /**
     * 返回解析得到的cst
     *
     * @param matcher
     * @return
     */
    @Override
    public Object eval(Matcher matcher) {
        int originalPos = matcher.pos;
        while (this.exp.eval(matcher) != null){
            matcher.pos++;
        }
        matcher.pos-=1;
        if (originalPos == matcher.pos) return null;
        return matcher.input.substring(originalPos,matcher.pos);
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return false;
    }

    /**
     * @return
     */
    @Override
    public String ruleName() {
        return "Exclude";
    }

    @Override
    public String toString() {
        return "!_("+this.exp+")";
    }
}
