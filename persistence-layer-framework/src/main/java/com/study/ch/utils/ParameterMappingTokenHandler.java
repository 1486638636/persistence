package com.study.ch.utils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParameterMappingTokenHandler implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList();

    public List<ParameterMapping> getParameterMappings() {
        return this.parameterMappings;
    }

    //content是#{}中{}里面的内容
    public String handleToken(String content) {
        this.parameterMappings.add(this.buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}
