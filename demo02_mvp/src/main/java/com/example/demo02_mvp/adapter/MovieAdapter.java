package com.example.demo02_mvp.adapter;

import android.content.Context;

import com.example.demo02_mvp.bean.MoviesBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 创建日期：2018/4/6 on 下午5:37
 * 描述:继承第三方万能适配器的RecycleView的适配器
 * 作者:yangliang
 */
public class MovieAdapter extends CommonAdapter<MoviesBean.SubjectsBean>{

    public MovieAdapter(Context context, int layoutId, List<MoviesBean.SubjectsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MoviesBean.SubjectsBean subjectsBean, int position) {

    }
}
