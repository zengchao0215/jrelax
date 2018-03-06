package com.jrelax.third.tuple;

public class Tuple2<A, B> extends Tuple {
    private A a;
    private B b;

    public Tuple2(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A first() {
        return this.a;
    }

    public B last() {
        return this.b;
    }
}
