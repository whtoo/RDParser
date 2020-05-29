package com.blitz.tutorial.chapter6;

public class Terminal implements IRuleApplication {
    String str;
    Boolean shouldSkip;

    Terminal(String str){
        this(str,false);
    }
    Terminal(String str,Boolean shouldSkip){
        this.str = str;
        this.shouldSkip = shouldSkip;
    }
    @Override
    public String eval(Matcher matcher) {
        for(int i = 0;i < this.str.length();i++){
            if(!matcher.consume(this.str.substring(i,i+1))){
                return null;
            }
        }

        return this.str;
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

}
