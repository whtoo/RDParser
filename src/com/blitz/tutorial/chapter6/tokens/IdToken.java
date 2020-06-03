package com.blitz.tutorial.chapter6.tokens;

import com.blitz.tutorial.chapter6.TokenEnum;

public class IdToken extends AToken {
    public IdToken(String image) {
        super(image, TokenEnum.IDENTIFIER);
    }

    public IdToken(String image,int start,int offset) {
        super(image, TokenEnum.IDENTIFIER);
        this.setPos(start,offset,0);
    }
    @Override
    public Boolean test() {
        return false;
    }
}
