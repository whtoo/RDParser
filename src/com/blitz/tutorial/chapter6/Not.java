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

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return false;
    }

    @Override
    public String ruleName() {
        return null;
    }

    @Override
    public String toString() {
        return "!"+this.exp.toString();
    }
}
