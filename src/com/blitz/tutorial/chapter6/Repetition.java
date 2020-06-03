package com.blitz.tutorial.chapter6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repetition implements IRuleApplication {

    IRuleApplication exp;

    Repetition(IRuleApplication exp){
        this.exp = exp;
    }

    @Override
    public Object eval(Matcher matcher) {
        List<Object> ans = new ArrayList<>();
        while (true) {
            int origPos = matcher.pos;
            Object cst = this.exp.eval(matcher);
            if(cst == null){
                matcher.pos = origPos;
                break;
            } else {
                if (!exp.shouldSkip()){
                    if(cst instanceof List && ((List) cst).isEmpty()){
                        continue;
                    }
                    if(cst instanceof List){
                        ans.addAll((Collection<?>) cst);
                    } else {
                        ans.add(cst);
                    }
                }
            }
            /// 已经匹配到文件尾部，终止匹配
            if(matcher.isEOF()) break;;
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
        return false;
    }

    @Override
    public String ruleName() {
        return null;
    }

    @Override
    public String toString() {
        if (exp instanceof Sequence || exp instanceof Repetition || exp instanceof Choice || exp instanceof Option){
            return "("+exp.toString()+")"+"*";
        }
        return exp.toString()+"*";
    }
}
