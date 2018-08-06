package com.example.demo02_mvp.ui.login;

/**
 * 创建日期：2018/8/2 on 下午4:28
 * 描述:模拟登陆----关联类---------这里主要是抽出MVP中三层的接口,好处就是改需求很方便,使得代码结构更加清晰
 * 为什么要使用接口，这里之所以写在一个类中，也是为了便于管理，不至于项目看上去有太多的类。
 * 作者:yangliang
 */
public class LoginContract {

    /**
     * V：视图层 展示数据，处理用户交互
     */
    interface ILoginView {

        //清除EditText内容
        void clearText();

        //显示进度条
        void showProgress();

        //结束进度条
        void hideProgress();

        //请求回调
        void onLoginResult(Boolean result, int code);

        //提示用户，提升友好交互
        void showInfo(String info);
    }

    /**
     * P:视图与逻辑处理的连接层
     */
    interface ILoginPresenter {
        //清除内容
        void clear();

        //登录
        void goLogin(String name, String password);

        //显示进度条
        void showProgress();

        //结束进度条
        void hideProgress();
    }

    /**
     * M:逻辑(业务)处理层
     */
    interface ILoginModel {

        //获取用户名
        String getUserName();

        //获取密码
        String getPassword();

        //检测正确性
        int checkUserValidity(String name, String password);
    }
}
