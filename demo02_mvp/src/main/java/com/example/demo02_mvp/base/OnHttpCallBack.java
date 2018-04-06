package com.example.demo02_mvp.base;

/**
 * 创建日期：2018/4/6 on 下午12:45
 * 描述:公共的请求回调监听器
 * CallBack : 回调思想 丢弃Handler
 * 意思S ：成功还是失败了,你总的告诉我一声,我好通知V层来处理
 * [这里可以不用回调,在代码中使用EventBus或者传递一个Handler过来也可以,不建议这样操作]
 * 作者:yangliang
 */
public interface OnHttpCallBack<T> {

    /**
     * 成功了就回调这个方法,可以传递任何形式的数据给调用者
     */
    void onSuccessful(T t);

    /**
     * 失败了就调用这个方法,可以传递错误的请求消息给调用者
     */
    void onFailed(String errorMsg);
}
