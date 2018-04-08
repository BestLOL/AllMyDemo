package com.qiuyi.cn.mysqlite;

/**
 * Created by Administrator on 2018/3/12.
 * Book的内容，与Book 一对多
 */
public class Comments {

    private Integer id;
    private String content;

    private Book book;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
