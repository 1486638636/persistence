package com.study.ch;

import com.study.ch.dto.User;
import com.study.ch.io.Resource;
import com.study.ch.sqlSeesion.SqlSession;
import com.study.ch.sqlSeesion.SqlSessionFactory;
import com.study.ch.sqlSeesion.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class TestPersistence {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User userOne = sqlSession.selectOne("com.study.ch.dto.User.selectOne", new User(2L, "李四"));
        List<Object> list = sqlSession.selectList("com.study.ch.dto.User.selectList");
        System.out.println("userOne="+userOne);
        for (Object o : list) {
            System.out.println("userList=" +o);
        }
    }
}
