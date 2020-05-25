package com.blitz.tutorial.chapter6;

public class Terminal implements IRuleApplication {
    String str;

    Terminal(String str){
        this.str = str;
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

}
