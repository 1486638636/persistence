package com.study.ch.config;

import com.study.ch.dto.Configuration;
import com.study.ch.dto.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream mapperInputStream) throws Exception {
        Document document = new SAXReader().read(mapperInputStream);
        Element rootElement = document.getRootElement();//mapper标签
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String parameterType = element.attributeValue("parameterType");
            String resultType = element.attributeValue("resultType");
            String sqlText = element.getTextTrim();

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setResultType(resultType);
            mappedStatement.setSqlText(sqlText);

            String namespace = rootElement.attributeValue("namespace");
            configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
        }
    }

}
