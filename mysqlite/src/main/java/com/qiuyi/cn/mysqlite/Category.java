package com.qiuyi.cn.mysqlite;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 * 书本类别
 * 与Book多对多
 * 一本书可能属于多个类别
 * 一个类别可能包含多本书
 */
public class Category {

    private Integer id;
    private String name;

    private List<Book> bookList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
