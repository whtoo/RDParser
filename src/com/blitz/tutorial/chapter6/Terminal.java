package com.blitz.tutorial.chapter6;

public class Terminal implements IRuleApplication {
    String str;

    Boolean isReserved;

    Terminal(String str){
        this(str,false);
    }

    Terminal(String str,Boolean isReserved){
        this.str = str;
        this.isReserved = isReserved;
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

    @Override
    public String ruleName() {
        return null;
    }

    @Override
    public String toString() {
        return "\'"+this.str+"\'";
    }
}
