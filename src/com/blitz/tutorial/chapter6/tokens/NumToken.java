package com.blitz.tutorial.chapter6.tokens;


import com.blitz.tutorial.chapter6.TokenEnum;

public class NumToken extends AToken {

    public NumToken(String image, TokenEnum tokenEnum) {
        super(image, tokenEnum);
    }

    public NumToken(String image, TokenEnum tokenEnum,int start,int offset) {
        super(image, tokenEnum);
        this.setPos(start,offset,0);
    }
    @Override
    public Boolean test() {
        return false;
    }
}
