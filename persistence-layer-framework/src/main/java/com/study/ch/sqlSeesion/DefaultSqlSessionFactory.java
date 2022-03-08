package com.study.ch.sqlSeesion;

import com.study.ch.dto.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
