package com.example.demo02_mvp.ui.movie;

import android.content.Context;

import com.example.demo02_mvp.base.OnHttpCallBack;
import com.example.demo02_mvp.bean.MoviesBean;

import java.util.List;

/**
 * 创建日期：2018/4/6 on 下午4:17
 * 描述:获取豆瓣top250的关联类---------这里主要是抽出MVP中三层的接口,好处就是改需求很方便,使得代码结构更加清晰
 * 为什么要使用接口，这里之所以写在一个类中，也是为了便于管理，不至于项目看上去有太多的类。
 * 作者:yangliang
 */
public class MovieContract {

    /**
     * V:视图，展示数据,处理用户交互
     */
    interface IMovieView {
        //设置最底下的加载更多显示的内容    加载中-->点击加载更多
        void showBottom(int lastIndex);

        //获取上下文对象
        Context getCurContext();

        //显示进度条
        void showProgress();

        //隐藏进度条
        void hideProgress();

        //显示数据到View上
        void showData(List<MoviesBean.SubjectsBean> movies);

        //提示用户,提升友好交互
        void showInfo(String info);
    }

    /**
     * P:视图与逻辑处理的连接层
     */
    interface IMoviePresenter {
        //获取数据
        void getMovie();

        //加载更多
        void loadMoreMovie();
    }

    /**
     * M:逻辑(业务)处理层
     */
    interface IMovieModel {
        //https://api.douban.com/v2/movie/top250?start=0&count=10
        //获取数据
        void getMovie(int start, int count, OnHttpCallBack<MoviesBean> callBack);
    }

}
