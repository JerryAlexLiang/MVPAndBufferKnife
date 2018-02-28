package com.example.demo01_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.demo01_login.presenter.LoginPresenterCompl;
import com.example.demo01_login.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/2/28 on 下午3:01
 * 描述: MVP 登录demo
 * 作者: liangyang
 */
public class MainActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.btn_login_clear)
    Button btnLoginClear;
    @BindView(R.id.progress_login)
    ProgressBar progressLogin;

    private LoginPresenterCompl loginPresenterCompl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化Presenter接口
        loginPresenterCompl = new LoginPresenterCompl(this);
        loginPresenterCompl.setProgressBarVisibility(View.INVISIBLE);

    }

    @OnClick({R.id.btn_login_login, R.id.btn_login_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                loginPresenterCompl.setProgressBarVisibility(View.VISIBLE);
                btnLoginLogin.setEnabled(false);
                btnLoginClear.setEnabled(false);
                loginPresenterCompl.goLogin(etLoginUsername.getText().toString(),
                        etLoginPassword.getText().toString());
                break;

            case R.id.btn_login_clear:
                loginPresenterCompl.clear();
                break;
        }
    }

    @Override
    public void onClearText() {
        etLoginUsername.setText("");
        etLoginPassword.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        loginPresenterCompl.setProgressBarVisibility(View.INVISIBLE);
        btnLoginLogin.setEnabled(true);
        btnLoginClear.setEnabled(true);
        if (result) {
            //result=true
            Toast.makeText(this, "登陆成功~", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SuccessActivity.class));

        } else {
            Toast.makeText(this, "登录失败，请重试~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressLogin.setVisibility(visibility);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
