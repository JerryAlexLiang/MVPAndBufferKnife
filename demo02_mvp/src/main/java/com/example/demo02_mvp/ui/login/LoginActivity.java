package com.example.demo02_mvp.ui.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo02_mvp.MainActivity;
import com.example.demo02_mvp.R;
import com.example.demo02_mvp.base.BaseActivity;
import com.example.demo02_mvp.ui.movie.MovieActivity;
import com.example.demo02_mvp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/8/2 on 下午5:46
 * 描述: MVP-模拟登录模块
 * 注释：
 * ---V层： 负责响应用户的交互(获取数据---->提示操作结果)
 * ---M层： 对P层传递过来的Info进行网络请求判断,处理完成之后将处理结果回调给P层
 * ---P层： 传递完数据给M层处理之后,实例化回调对象,成功了就通知V层登录成功,失败了就通知V层显示错误信息
 * 作者: liangyang
 */
public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login_login)
    Button btnLoginLogin;
    @BindView(R.id.btn_login_clear)
    Button btnLoginClear;
    private LoginPresenter loginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //初始化视图
        initView();
        //初始化presenter
        loginPresenter = new LoginPresenter(this);
    }

    private void initView() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitleName.setText("MVP登录");
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("加载中...");
    }

    @OnClick({R.id.iv_title_back, R.id.btn_login_login, R.id.btn_login_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
//                finish();
                break;

            case R.id.btn_login_login:
                //显示进度框
                loginPresenter.showProgress();
                btnLoginLogin.setEnabled(false);
                btnLoginClear.setEnabled(false);
                //登录
                loginPresenter.goLogin(etLoginUsername.getText().toString(),
                        etLoginPassword.getText().toString());
                break;

            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;
        }
    }

    /**
     * 清空内容
     */
    @Override
    public void clearText() {
        etLoginUsername.setText("");
        etLoginPassword.setText("");
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    /**
     * 登录回调
     */
    @Override
    public void onLoginResult(Boolean result, int code) {
        //结束进度框
        loginPresenter.hideProgress();
        btnLoginLogin.setEnabled(true);
        btnLoginClear.setEnabled(true);
        if (result) {
            //result=true
            ToastUtils.showToast(this,"登陆成功");
            //跳转登录后界面
            MainActivity.actionStart(this);
        }
        else {
            //result=false
            ToastUtils.showToast(this,"登陆失败");
        }

    }

    @Override
    public void showInfo(String info) {
        ToastUtils.showToast(this, info);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 返回键退出应用(连按两次)
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long waitTime = 2000;
    private long touchTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                ToastUtils.showToast(this, "再按一次退出应用");
                touchTime = currentTime;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        } else if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
