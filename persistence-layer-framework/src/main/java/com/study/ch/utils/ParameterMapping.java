package com.study.ch.utils;

public class ParameterMapping {

    public ParameterMapping(String content) {
        this.content = content;
    }

    //解析出来的{}里面的文本
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
