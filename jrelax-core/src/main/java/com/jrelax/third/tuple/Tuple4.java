package com.jrelax.third.tuple;

public class Tuple4<A, B, C, D> extends Tuple {
    private A a;
    private B b;
    private C c;
    private D d;

    public Tuple4(A a, B b, C c, D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public A first() {
        return this.a;
    }

    public B second() {
        return this.b;
    }

    public C third() {
        return this.c;
    }

    public D last() {
        return this.d;
    }
}
