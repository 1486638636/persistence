package com.study.ch.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.study.ch.dto.Configuration;
import com.study.ch.io.Resource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sql.ConnectionPoolDataSource;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 该方法将配置文件进行解析，封装到Configuration中去
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws Exception {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();//<configuration>
        List<Element> propertyTags = rootElement.selectNodes("//property");//xpath表达式//表示后面跟的字符串在根标签下的任意位置都能找到

        Properties properties = new Properties();
        for (Element element : propertyTags) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml解析，拿到路径->inputStream，dom4j解析
        List<Element> mapperTags = rootElement.selectNodes("//mapper");
        for (Element element : mapperTags) {
            String mapperPath = element.attributeValue("resource");
            InputStream mapperInputStream = Resource.getResourceAsStream(mapperPath);
            XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(configuration);
            xmlMapperBuilder.parse(mapperInputStream);

        }


        return configuration;
    }
}
