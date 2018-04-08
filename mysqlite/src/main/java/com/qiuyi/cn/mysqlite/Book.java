package com.qiuyi.cn.mysqlite;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Administrator on 2018/3/11.
 * Book表
 * Book与Introduce 一对一
 * Book与Comments 一对多
 * Book与Category 多对多
 */
public class Book extends DataSupport{

    private Integer id;
    private String name;
    private String author;
    private Integer pages;
    private Double price;

    //书籍介绍
    private Introduce introduce;

    //书籍内容
    private List<Comments> commentsList;

    //书籍种类
    private List<Category> categoryList;


    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Introduce getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Introduce introduce) {
        this.introduce = introduce;
    }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }




}
