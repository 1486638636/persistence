package com.study.ch.dto;

/**
 * 存储sql配置文件中标签中的各属性值（一个mappedStatement就是一个sql标签）
 */
public class MappedStatement {
    //id标识
    private String id;

    private String parameterType;

    private String resultType;

    private String sqlText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }
}
