package com.example.demo02_mvp.ui.movie;

import com.example.demo02_mvp.base.APIService;
import com.example.demo02_mvp.base.GlobalField;
import com.example.demo02_mvp.base.OnHttpCallBack;
import com.example.demo02_mvp.bean.MoviesBean;
import com.example.demo02_mvp.http.RetrofitUtils;
import com.socks.library.KLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 创建日期：2018/4/6 on 下午4:36
 * 描述:具体的逻辑(业务)处理(网络请求都在这里)
 * 作者:yangliang
 */
public class MovieModel implements MovieContract.IMovieModel {

    @Override
    public void getMovie(int start, int count, final OnHttpCallBack<MoviesBean> callBack) {

        RetrofitUtils.newInstance(GlobalField.MOVIE_TOP250_URL)
                .create(APIService.class)
                .getMoviesData(start, count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<MoviesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //失败的时候回调这个方法,可以传递错误的请求消息给调用者
//                        callBack.onFailed("请求失败~");
                        //以下可以忽略 直接 callBack.onFaild("请求失败");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFailed("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFailed("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFailed("网络连接超时!!");
                        } else {
                            callBack.onFailed("发生未知错误" + e.getMessage());
                            KLog.e(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(MoviesBean moviesBean) {
                        //请求成功---回调,成功了就回调这个方法,可以传递任何形式的数据给调用者
                        callBack.onSuccessful(moviesBean);
                        KLog.e(moviesBean.toString());
                        KLog.e(moviesBean.getSubjects().toString());
                    }
                });

    }
}
