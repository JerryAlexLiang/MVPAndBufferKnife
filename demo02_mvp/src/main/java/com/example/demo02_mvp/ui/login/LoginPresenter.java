package com.example.demo02_mvp.ui.login;

import android.os.Handler;
import android.os.Looper;

/**
 * 创建日期：2018/8/4 on 上午10:41
 * 描述:--- P(View和Model的桥梁)  : 获取V层数据,交给M层处理,将处理结果通知给V层
 * P通过将V拿到的数据交给M来处理,P又将处理的结果回显到V
 * 说白点就是P中new出M和V,然后通过调用它们两个的方法来完成操作
 * 作者:yangliang
 */
public class LoginPresenter implements LoginContract.ILoginPresenter {

    private final Handler handler;
    //View层
    LoginContract.ILoginView mILoginView;
    //Model层
    LoginContract.ILoginModel mILoginModel;

    public LoginPresenter(LoginContract.ILoginView mILoginView) {
        this.mILoginView = mILoginView;
        //初始化model
        mILoginModel = new LoginModel("admin", "mvp");
        //初始化handler
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 清除填充内容
     */
    @Override
    public void clear() {
        mILoginView.clearText();
    }

    /**
     * 操作登录逻辑
     */
    @Override
    public void goLogin(String name, String password) {
        Boolean isLoginSuccess = true;
        //获取返回检验码 -1,0
        final int code = mILoginModel.checkUserValidity(name, password);
        if (code != 0) {
            //登录失败
            isLoginSuccess = false;
//            mILoginView.showInfo("登录失败");
        }else {
//            mILoginView.showInfo("登录成功");
        }
        //赋值登录结果标志位result
        final Boolean result = isLoginSuccess;
        //更新UI
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //view层反馈结果
                mILoginView.onLoginResult(result, code);

            }
        }, 2000);
    }

    @Override
    public void showProgress() {
        mILoginView.showProgress();
    }

    @Override
    public void hideProgress() {
        mILoginView.hideProgress();
    }
}
