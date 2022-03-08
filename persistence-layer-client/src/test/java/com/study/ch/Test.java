package com.study.ch;

import com.study.ch.dto.User;
import com.study.ch.io.Resource;
import com.study.ch.sqlSeesion.SqlSession;
import com.study.ch.sqlSeesion.SqlSessionFactory;
import com.study.ch.sqlSeesion.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;

import java.io.InputStream;

public class Test {

    public void test() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.selectOne("",new User(1, "张三"));
    }
}
