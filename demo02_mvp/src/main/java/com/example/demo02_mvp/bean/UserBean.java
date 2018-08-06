package com.example.demo02_mvp.bean;

import java.io.Serializable;

/**
 * 创建日期：2018/8/2 on 下午5:50
 * 描述:用户信息
 * 作者:yangliang
 */
public class UserBean implements Serializable {

    private String nickName;
    private String password;

    public UserBean(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public UserBean setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserBean setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
