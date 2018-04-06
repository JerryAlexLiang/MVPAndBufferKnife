package com.example.demo02_mvp.utils;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

import com.example.demo02_mvp.R;

/**
 * 创建日期：2018/4/6 on 下午12:35
 * 描述:状态栏一体化(设置状态栏的颜色与app主题色一致 没有过渡感)
 * 作者:yangliang
 */
public class ActionBarUtils {

    Activity activity;

    public ActionBarUtils(Activity activity) {
        this.activity = activity;
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            this.activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            this.activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(this.activity);
            tintManager.setStatusBarTintEnabled(true);
            //设置状态栏颜色
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
            this.activity.getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }

}
