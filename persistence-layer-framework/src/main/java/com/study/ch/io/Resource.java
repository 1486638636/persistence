package com.study.ch.io;

import java.io.InputStream;

public class Resource {
    /**
     * 根据配置文件的路径，将配置文件加载成字节输入流，存储到内存中
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        //通过当前类的类加载器获取对应路径的inputstream
        InputStream resourceAsStream = Resource.class.getClassLoader().getResourceAsStream(path);
        return resourceAsStream;
    }
}
