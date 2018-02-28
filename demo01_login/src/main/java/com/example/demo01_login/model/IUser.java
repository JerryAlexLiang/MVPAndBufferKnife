package com.example.demo01_login.model;

/**
 * 创建日期：2018/2/28 on 下午3:07
 * 描述:
 * 作者:yangliang
 */
public interface IUser {

    //获取用户名
    String getName();

    //获取密码
    String getPassword();

    //检测正确性
    int checkUserValidity(String name, String password);
}
