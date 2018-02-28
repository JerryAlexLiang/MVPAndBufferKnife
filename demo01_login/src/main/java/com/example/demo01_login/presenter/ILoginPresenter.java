package com.example.demo01_login.presenter;

/**
 * 创建日期：2018/2/28 on 下午3:12
 * 描述:
 * 作者:yangliang
 */
public interface ILoginPresenter {

    //清除内容
    void clear();

    //登录
    void goLogin(String name, String password);

    //设置进度条显示状态
    void setProgressBarVisibility(int visibility);
}
