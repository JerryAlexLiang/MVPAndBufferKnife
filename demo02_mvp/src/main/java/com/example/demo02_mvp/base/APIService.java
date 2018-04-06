package com.example.demo02_mvp.base;

import com.example.demo02_mvp.bean.MoviesBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 创建日期：2018/4/6 on 下午12:43
 * 描述:API--接口  服务[这里处理的是同一的返回格式 resultCode  resultInfo Data<T>
 * --->这里的data才是返回的结果,T就是泛型 具体返回的been对象或集合]
 * 作者:yangliang
 */
public interface APIService {
    //https://api.douban.com/v2/movie/top250?start=0&count=10

    /**
     * 查询豆瓣排名前250的电影
     *
     * @param start 从第几部开始
     * @param count 几页(一页有12部)
     *              注释：
     *              BASE_URL:https://api.douban.com/
     *              GET请求：/v2/movie/top250
     *              查询参数：start，count
     * @return
     */
    @GET("v2/movie/top250")
    Observable<MoviesBean> getMoviesData(@Query("start") int start, @Query("count") int count);

}
