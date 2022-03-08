package com.study.ch.sqlSeesion;

import com.study.ch.dto.Configuration;
import com.study.ch.dto.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <T> List<T> selectList(String statementId, Object... params) throws Exception {
        //将要去完成对simpleExecutor里的query方法调用
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        simpleExecutor.query(configuration, mappedStatement, params);

        return null;
    }

    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = this.selectList(statementId, params);
        if(objects.size() == 1){
            return (T) objects.get(0);
        }else{
            throw new RuntimeException("查询结果为空或者结果行过多！！");
        }
    }
}
