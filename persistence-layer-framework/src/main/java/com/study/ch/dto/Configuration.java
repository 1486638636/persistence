package com.study.ch.dto;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    /**
     * 数据库配置信息（包含driverClass,jdbcUrl,username,password）
     */
    private DataSource dataSource;

    /**
     * sql标签对应实体. key:statementId(namespace.id),value:封装好的mappedStatement标签对象
     */
    private Map<String, MappedStatement> mappedStatementMap = new HashMap();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
