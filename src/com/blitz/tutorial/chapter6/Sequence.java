package com.blitz.tutorial.chapter6;

import java.util.ArrayList;
import java.util.List;

public class Sequence implements IRuleApplication {
    List<IRuleApplication> exps;
    Sequence(List<IRuleApplication> exps){
        this.exps = exps;
    }
    @Override
    public Object eval(Matcher matcher) {
        List<Object> ans = new ArrayList<>(3);
        for (int i =0;i<this.exps.size();) {

            while (!matcher.isLexical && matcher.skipActions().eval(matcher) != null);

            IRuleApplication exp = this.exps.get(i);
            Object cst = exp.eval(matcher);
            if (cst == null) {
                return null;
            }

            if (!(exp instanceof Not)) {
                //此时表示expr成功解析
                i++;
                if (!exp.shouldSkip()){
                    ans.add(cst);
                }
            } else {
                i++;
            }
        }
        return ans;
    }

    /**
     * 测试是否跳过当前模式
     *
     * @return False means the current part should be skipped.
     */
    @Override
    public Boolean shouldSkip() {
        return false;
    }

    @Override
    public String ruleName() {
        return null;
    }
}
