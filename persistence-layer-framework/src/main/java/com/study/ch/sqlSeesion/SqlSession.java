package com.study.ch.sqlSeesion;

import java.util.List;

public interface SqlSession {
    /**
     * 查询所有
     */
    <T> List<T> selectList(String statementId, Object... params) throws Exception;

    /**
     * 根据条件查询单个
     */
    <T> T selectOne(String statementId, Object... params) throws Exception;

}
