package com.example.lixiao.deomdemo.bean;

/**
 * Created by lixiao on 2017/10/26.
 */

public class DataBean {
    private String name; // 在ListView中显示的数据
    private char indexName; // 拼音首字母

    public DataBean() {
    }

    public DataBean(String name, char indexName) {
        this.name = name;
        this.indexName = indexName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getIndexName() {
        return indexName;
    }

    public void setIndexName(char indexName) {
        this.indexName = indexName;
    }
}
