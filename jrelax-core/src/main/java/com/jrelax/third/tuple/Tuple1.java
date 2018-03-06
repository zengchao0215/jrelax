package com.jrelax.third.tuple;

import java.util.Optional;

public class Tuple1<A> extends Tuple {
    private A a;

    public Tuple1(A a) {
        this.a = a;
    }

    public A first() {
        return this.a;
    }
}
