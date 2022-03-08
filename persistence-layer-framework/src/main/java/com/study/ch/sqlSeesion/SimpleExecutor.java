package com.study.ch.sqlSeesion;

import com.study.ch.config.BoundSql;
import com.study.ch.dto.Configuration;
import com.study.ch.dto.MappedStatement;
import com.study.ch.utils.GenericTokenParser;
import com.study.ch.utils.ParameterMapping;
import com.study.ch.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor implements Executor{
    //执行底层jdbc代码，完成参数设置以及结果集的封装
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //step1.注册驱动，获取链接
        DataSource dataSource = configuration.getDataSource();
        Connection connection = dataSource.getConnection();
        //step2.获取sql语句：select * from t_user where id = #{id} and name=#{name}
        //转换sql成预编译语句：select * from t_user where id = ？ and name=？
        String sqlText = mappedStatement.getSqlText();
        BoundSql boundSql = getBoundSql(sqlText);
        //step3.获取预处理对象prepareStatement
        connection.prepareStatement(boundSql.getSqlText());
        //step4.参数设置

        //step5.执行sql

        //step6.封装结果集



        return null;
    }

    /**
     * 完成对#{}的解析操作：1.将#{}替换为?,2.解析出#{}里面的值进行存储
     * @param sqlText
     * @return
     */
    private BoundSql getBoundSql(String sqlText) {
        //标记处理类：配置标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sqlText);
        //#{}里解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parseSql, parameterMappings);
    }
}
