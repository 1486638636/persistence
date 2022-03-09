package com.study.ch.sqlSeesion;

import com.study.ch.config.BoundSql;
import com.study.ch.dto.Configuration;
import com.study.ch.dto.MappedStatement;
import com.study.ch.utils.GenericTokenParser;
import com.study.ch.utils.ParameterMapping;
import com.study.ch.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
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
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //step4.参数设置
        String parameterType = mappedStatement.getParameterType();//参数全路径
        Class<?> parameterTypeClass = getClassType(parameterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //使用反射通过parameterTypeClass获取对应属性值得类型
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true);//防止属性是私有的，设置暴力访问
            Object val = declaredField.get(params[0]);//params是数组，传进来的只是一个user对象，所以params[0]就是传进来的对象
            //设置参数时，下标应该从1开始
            preparedStatement.setObject(i+1, val);
        }
        //step5.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //step6.封装结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        List<Object> list = new ArrayList<Object>();
        while (resultSet.next()){
            Object o = resultTypeClass.newInstance();
            //元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射或内省，根据数据库表和实体的对应关系，完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                System.out.println("value="+value+"columnName="+columnName);
                writeMethod.invoke(o, value);
            }
            list.add(o);
        }
        return (List<E>) list;
    }

    /**
     * 通过类路径获取类的Class对象，提供反射所需的一系列api
     * @param parameterType
     * @return
     * @throws ClassNotFoundException
     */
    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType == null){
            return null;
        }
        Class<?> aClass = Class.forName(parameterType);
        return aClass;
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
