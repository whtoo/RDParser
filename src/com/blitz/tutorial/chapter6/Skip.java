package com.blitz.tutorial.chapter6;

import java.util.List;

public class Skip implements IRuleApplication {
    IRuleApplication epxr;
    Skip(IRuleApplication skipRule){
        this.epxr = skipRule;
    }

    /**
     * 返回解析得到的cst
     *
     * @param matcher
     * @return
     */
    @Override
    public Object eval(Matcher matcher) {
        int start = matcher.pos;

        Object cst = this.epxr.eval(matcher);

        System.out.println(String.format("skip %s from %s to %s",epxr.toString(),start,matcher.pos));
        if(cst instanceof List && ((List) cst).isEmpty()){
            cst = null;
        }
        return cst;
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return
     */
    @Override
    public Boolean shouldSkip() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String ruleName() {
        return "Skip";
    }

    @Override
    public String toString() {
        return "_("+this.epxr.toString()+")";
    }
}
