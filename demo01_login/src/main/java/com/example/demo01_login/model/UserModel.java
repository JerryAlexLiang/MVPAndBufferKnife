package com.example.demo01_login.model;

/**
 * 创建日期：2018/2/28 on 下午3:09
 * 描述:
 * 作者:yangliang
 */
public class UserModel implements IUser {

    String name;
    String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int checkUserValidity(String name, String password) {
        if (name == null || password == null || !name.equals(getName()) || !password.equals(getPassword())) {
            return -1;
        }
        return 0;
    }
}
