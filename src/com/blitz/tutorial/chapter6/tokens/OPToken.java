package com.blitz.tutorial.chapter6.tokens;

import com.blitz.tutorial.chapter6.TokenEnum;

public class OPToken extends AToken {

    public OPToken(String image) {
        super(image, TokenEnum.OPERATOR);
    }

    public OPToken(String image,int start,int offset){
        super(image, TokenEnum.OPERATOR);
        this.setPos(start,offset,0);
    }

    @Override
    public Boolean test() {
        return null;
    }
}
