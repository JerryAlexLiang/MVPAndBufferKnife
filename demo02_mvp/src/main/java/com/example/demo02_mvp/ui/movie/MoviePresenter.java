package com.example.demo02_mvp.ui.movie;

import android.util.Log;

import com.example.demo02_mvp.base.OnHttpCallBack;
import com.example.demo02_mvp.bean.MoviesBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/4/6 on 下午4:52
 * 描述:--- P(View和Model的桥梁)  : 获取V层数据,交给M层处理,将处理结果通知给V层
 * P通过将V拿到的数据交给M来处理,P又将处理的结果回显到V
 * 说白点就是P中new出M和V,然后通过调用它们两个的方法来完成操作
 * 作者:yangliang
 */
public class MoviePresenter implements MovieContract.IMoviePresenter {

    //View层
    MovieContract.IMovieView mIMovieView;
    //Model层
    MovieContract.IMovieModel mIMovieModel;

    public int start = 0;//从第几个开始
    public int count = 4;//请求多少个

    //请求到的电影信息对象集合
    List<MoviesBean.SubjectsBean> mMovies = new ArrayList<>();

    public MoviePresenter(MovieContract.IMovieView mIMovieView) {
        this.mIMovieView = mIMovieView;
        //初始化M
        mIMovieModel = new MovieModel();
    }

    /**
     * 获取数据
     */
    @Override
    public void getMovie() {
        //通知View层显示进度框
        mIMovieView.showProgress();

        //每次刷新加载4个
        //有一个请求结果的回调,即我调用请求电影信息的方法了,M层要返回一个成功还是失败的信息给我
        mIMovieModel.getMovie(start, count, new OnHttpCallBack<MoviesBean>() {
            /**
             * 获取电影信息成功了,返回movies对象
             * @param moviesBean 数据
             */
            @Override
            public void onSuccessful(MoviesBean moviesBean) {
                //通知V层隐藏对话框
                mIMovieView.hideProgress();
                //追加数据
                mMovies.addAll(moviesBean.getSubjects());
                //将获取到的信息显示到界面之前
                mIMovieView.showData(mMovies);
                //实现换页效果
                mIMovieView.showBottom(start - 5);
                //通知V层显示加载成功提示
                mIMovieView.showInfo("加载成功~");
            }

            /**
             * 获取电影信息失败，返回错误提示
             * @param errorMsg 提示信息
             */
            @Override
            public void onFailed(String errorMsg) {
                //通知V层隐藏对话框
                mIMovieView.hideProgress();
                //通知V层显示错误信息
                mIMovieView.showInfo(errorMsg);
            }
        });

        //改变请求的起点
        start = start + 4;

    }

    /**
     * 加载更多
     */
    @Override
    public void loadMoreMovie() {
        getMovie();
    }

}
