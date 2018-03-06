package com.jrelax.third.tuple;

public class Tuple3<A, B, C> extends Tuple {
    private A a;
    private B b;
    private C c;

    public Tuple3(A a, B b, C c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public A first() {
        return this.a;
    }

    public B second() {
        return this.b;
    }

    public C last() {
        return this.c;
    }
}
