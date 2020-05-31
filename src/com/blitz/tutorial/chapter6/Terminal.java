package com.blitz.tutorial.chapter6;

public class Terminal implements IRuleApplication {
    String str;
    Boolean shouldSkip;
    Boolean isReserved;

    Terminal(String str){
        this(str,false,false);
    }
    Terminal(String str,Boolean shouldSkip) { this(str,false,false); }
    Terminal(String str,Boolean shouldSkip,Boolean isReserved){
        this.str = str;
        this.shouldSkip = shouldSkip;
        this.isReserved = isReserved;
    }

    static Terminal token(String str) {
        return new Terminal(str,false,true);
    }

    static Terminal sep(String str) {
        return new Terminal(str,true,true);
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
