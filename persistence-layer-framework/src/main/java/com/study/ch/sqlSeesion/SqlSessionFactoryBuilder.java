package com.study.ch.sqlSeesion;

import com.study.ch.config.XmlConfigBuilder;
import com.study.ch.dto.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) throws Exception {
        //step1. 通过dom4j解析配置文件，将解析出来的内容封装在configuration中
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);
        //step2. 创建SqlSessionFactory对象,工厂类：生产sqlSession:回话对象 并返回
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);


        return defaultSqlSessionFactory;

    }
}
