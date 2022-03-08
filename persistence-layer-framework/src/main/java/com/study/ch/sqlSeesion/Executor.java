package com.study.ch.sqlSeesion;

import com.study.ch.dto.Configuration;
import com.study.ch.dto.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
