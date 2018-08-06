package com.example.demo02_mvp.ui.login;

/**
 * 创建日期：2018/8/2 on 下午5:48
 * 描述:具体的逻辑(业务)处理(网络请求都在这里)
 * 作者:yangliang
 */
public class LoginModel implements LoginContract.ILoginModel {

    private String name;
    private String password;

    public LoginModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * 获取用户名
     */
    @Override
    public String getUserName() {
        return name;
    }

    /**
     * 获取密码
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 检测正确性
     */
    @Override
    public int checkUserValidity(String name, String password) {
        if (name == null || password == null || !name.equals(getUserName()) || !password.equals(getPassword())) {
            //不符合验证，返回code=-1
            return -1;
        }
        //符合验证，返回code=0
        return 0;
    }
}
