package com.blitz.tutorial.chapter6.tokens;

import com.blitz.tutorial.chapter6.TokenEnum;

public class StrToken extends AToken {
    public StrToken(String image) {
        super(image, TokenEnum.STRING);
    }

    public StrToken(String image,int start,int offset){
        super(image,TokenEnum.STRING);
        this.setPos(start,offset,0);
    }
    @Override
    public Boolean test() {
        return null;
    }
}
