package com.jrelax.third.tuple;

public class Tuple5<A, B, C, D, E> extends Tuple {
    private A a;
    private B b;
    private C c;
    private D d;
    private E e;

    public Tuple5(A a, B b, C c, D d, E e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
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

    public D fourth() {
        return this.d;
    }

    public E last() {
        return this.e;
    }
}
