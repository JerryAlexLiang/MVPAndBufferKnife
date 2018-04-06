package com.example.demo02_mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.demo02_mvp.utils.ActionBarUtils;

/**
 * 创建日期：2018/4/6 on 下午12:40
 * 描述:
 * 作者:yangliang
 */
public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        new ActionBarUtils(mActivity).setTranslucentStatus();
    }
}
