package com.example.demo01_login.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.demo01_login.model.IUser;
import com.example.demo01_login.model.UserModel;
import com.example.demo01_login.view.ILoginView;


/**
 * 创建日期：2018/2/28 on 下午3:17
 * 描述:
 * 作者:yangliang
 */
public class LoginPresenterCompl implements ILoginPresenter {

    ILoginView iLoginView;
    IUser iUser;
    Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        //初始化登录数据
        initUser();
        //初始化handler
        handler = new Handler(Looper.getMainLooper());
    }

    private void initUser() {
        iUser = new UserModel("admin", "mvp");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void goLogin(String name, String password) {
        Boolean isLoginSuccess = true;
        final int code = iUser.checkUserValidity(name, password);//-1,0
        if (code != 0) {
            //登录失败
            isLoginSuccess = false;
        }
        final Boolean result = isLoginSuccess;
        //更新UI
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);
    }

    @Override
    public void setProgressBarVisibility(int visibility) {
        iLoginView.onSetProgressBarVisibility(visibility);
    }
}
