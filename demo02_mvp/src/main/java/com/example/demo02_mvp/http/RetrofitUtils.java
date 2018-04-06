package com.example.demo02_mvp.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建日期：2018/4/6 on 下午3:26
 * 描述:Retrofit网络请求工具类
 * 作者:yangliang
 */
public class RetrofitUtils {

    //读取超时时间,单位:秒
    public static final int READ_TIMEOUT = 60;

    //连接超时时间,单位:秒
    public static final int CONN_TIMEOUT = 12;

    private static Retrofit mRetrofit;

    public RetrofitUtils() {

    }

    public static Retrofit newInstance(String url) {

        mRetrofit = null;

        //初始化一个client,不然retrofit会自己默认添加一个
        OkHttpClient client = new OkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .client(client)//添加一个client,不然retrofit会自己默认添加一个
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return mRetrofit;
    }

}
