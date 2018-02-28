package com.example.demo01_login.view;

/**
 * 创建日期：2018/2/28 on 下午3:04
 * 描述:
 * 作者:yangliang
 */
public interface ILoginView {

    //清除内容
    void onClearText();

    //请求回调
    void onLoginResult(Boolean result, int code);

    //设置进度条显示状态
    void onSetProgressBarVisibility(int visibility);

}
