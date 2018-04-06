package com.example.demo02_mvp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 创建日期：2018/4/6 on 下午3:36
 * 描述:Toast工具类,优化Toast
 * 作者:yangliang
 */
public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示对话框
     *
     * @param context 上下文
     * @param content 要显示的内容
     */
    @SuppressLint("ShowToast")
    public static void showToast(Context context, String content) {

        if (mToast == null) {
            mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();


    }
}
