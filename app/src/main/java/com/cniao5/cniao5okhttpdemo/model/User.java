package com.cniao5.cniao5okhttpdemo.model;

import java.io.Serializable;

/**
 * Created by Ivan on 2016/10/6.
 */

public class User implements Serializable {


    private String username;
    private String id;

    private String head_url;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
}
