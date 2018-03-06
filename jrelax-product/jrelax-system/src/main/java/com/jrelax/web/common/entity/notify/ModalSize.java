package com.jrelax.web.common.entity.notify;

/**
 * 弹窗大小
 */
public enum ModalSize {
    Large("modal-lg"), Normal("modal-md"),

    Small("modal-sm"), Little("modal-xs");

    private String text;

    ModalSize(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
