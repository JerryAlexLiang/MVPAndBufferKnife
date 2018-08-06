package com.example.demo02_mvp.ui.movie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.demo02_mvp.R;
import com.example.demo02_mvp.base.BaseActivity;
import com.example.demo02_mvp.bean.MoviesBean;
import com.example.demo02_mvp.utils.ToastUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * 创建日期：2018/4/6 on 下午4:53
 * 描述: 豆瓣排名前250名
 * 注释：
 * ---V层： 负责响应用户的交互(获取数据---->提示操作结果)
 * ---M层： 对P层传递过来的Info进行网络请求判断,处理完成之后将处理结果回调给P层
 * ---P层： 传递完数据给M层处理之后,实例化回调对象,成功了就通知V层登录成功,失败了就通知V层显示错误信息
 * <p>
 * 作者: liangyang
 */
public class MovieActivity extends BaseActivity implements MovieContract.IMovieView {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.store_house_ptr_frame)
    PtrFrameLayout storeHousePtrFrame;

    private TextView load_more;//加载更多的按钮
    private ProgressDialog progressDialog;
    private MoviePresenter mMoviePresenter;

    public static void actionStart(Context context){
        Intent intent =new Intent(context,MovieActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        //初始化视图
        initView();
        //通过P -> 加载数据 -> M ->V (启动软件时默认加载)
        mMoviePresenter = new MoviePresenter(this);
        mMoviePresenter.getMovie();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        tvTitleName.setText(R.string.movie_show_title);
        //初始化刷新控件
        initPtr();
        //设置RecyclerView
        rvMovieList.setLayoutManager(new LinearLayoutManager(mActivity));//设置为ListView的布局
        rvMovieList.setItemAnimator(new DefaultItemAnimator());//设置动画
        rvMovieList.addItemDecoration(new DividerItemDecoration(this,
                com.example.demo02_mvp.utils.DividerItemDecoration.VERTICAL_LIST));//添加分割线
    }

    /**
     * 初始化(配置)下拉刷新组件
     */
    private void initPtr() {
        //下面是一些基础的配置,直接拿来用就可以 不用深究
        storeHousePtrFrame.setResistance(1.7f);
        storeHousePtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        storeHousePtrFrame.setDurationToClose(200);
        storeHousePtrFrame.setDurationToCloseHeader(1000);
        storeHousePtrFrame.setPullToRefresh(false);
        storeHousePtrFrame.setKeepHeaderWhenRefresh(true);

        StoreHouseHeader header = new StoreHouseHeader(this);
        float scale = getResources().getDisplayMetrics().density;
        header.setPadding(0, (int) (15 * scale + 0.5f), 0, (int) (15 * scale + 0.5f));
        header.initWithString("HDL");//自定义头显示的字样,设置图片的话看另外的api
        header.setTextColor(Color.RED);
        header.setBackgroundColor(Color.parseColor("#11000000"));
        storeHousePtrFrame.setHeaderView(header);//添加头
        storeHousePtrFrame.addPtrUIHandler(header);//同时也要加上这一句

        storeHousePtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                /**
                 * 下拉刷新的时候加载更多数据
                 */
                mMoviePresenter.loadMoreMovie();

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        storeHousePtrFrame.refreshComplete();
                    }
                }, 150);//为了增加用户体验 延迟0.15s再通知刷新结束
            }
        });
    }

    @OnClick(R.id.iv_title_back)
    public void onViewClicked() {
        finish();
    }

    /**
     * 设置最底下的加载更多显示的内容
     * 加载中-->点击加载更多
     *
     * @param lastIndex
     */
    @Override
    public void showBottom(int lastIndex) {
        //设置最底下的加载更多显示的内容，加载中-->点击加载更多
        load_more.setText("点击加载更多");
        rvMovieList.scrollToPosition(lastIndex);
    }

    /**
     * 获取上下文对象
     *
     * @return
     */
    @Override
    public Context getCurContext() {
        return this;
    }

    /**
     * 显示进度条
     */
    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "提示", "正在获取中，请稍后...");
    }

    /**
     * 隐藏进度条
     */
    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    /**
     * 显示数据到View上
     *
     * @param movies
     */
    @Override
    public void showData(List<MoviesBean.SubjectsBean> movies) {

        CommonAdapter<MoviesBean.SubjectsBean> commonAdapter =
                new CommonAdapter<MoviesBean.SubjectsBean>(this, R.layout.move_rv_item, movies) {

                    @Override
                    protected void convert(ViewHolder holder, MoviesBean.SubjectsBean subjectsBean, int position) {
                        String title = (position + 1) + "、" + subjectsBean.getTitle() + "/" + subjectsBean.getOriginal_title();
                        //设置电影名字
                        holder.setText(R.id.tv_movie_title, title);

                        String doc = "";
                        for (MoviesBean.SubjectsBean.DirectorsBean directorsBean :
                                subjectsBean.getDirectors()) {
                            doc += directorsBean.getName() + " ";
                        }
                        //设置导演
                        holder.setText(R.id.tv_movie_doc, "导演：" + doc);

                        String casts = "";
                        for (MoviesBean.SubjectsBean.CastsBean castsBean : subjectsBean.getCasts()) {
                            casts += castsBean.getName() + "  ";
                        }
                        //设置主演
                        holder.setText(R.id.tv_movie_art, "主演:" + casts);

                        String genres = "";
                        for (String genre : subjectsBean.getGenres()) {
                            genres += genre + " ";
                        }
                        holder.setText(R.id.tv_movie_type, subjectsBean.getYear() + " / " + genres);//年份+分级
                        holder.setText(R.id.tv_movie_grade, subjectsBean.getRating().getAverage() + "");//评分

                        //图片
                        ImageView iv_pic = holder.getView(R.id.iv_movie_pic);
                        Glide.with(mActivity)
                                .load(subjectsBean.getImages().getSmall())
                                .into(iv_pic);

                    }
                };

        /**
         * 配置加载更多(通用适配器里面的类)
         */
        //加载更多的包装器(传入通用适配器)
        LoadMoreWrapper mLoadMoreWrapper = new LoadMoreWrapper(commonAdapter);
        View view = View.inflate(mActivity, R.layout.rv_bottom_load_more, null);
        //要设置一下的布局参数,因为布局填充到包装器的时候,自己的一些属性会无效
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(mLayoutParams);
        load_more = view.findViewById(R.id.tv_load_more);

        //监听点击加载更多事件
        load_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击加载更多-->加载中
                load_more.setText("加载中...");
                //通过P->加载更多数据
                mMoviePresenter.loadMoreMovie();
            }
        });
        mLoadMoreWrapper.setLoadMoreView(view);

        //注意:这里添加的是包装器 不是适配器哦
        rvMovieList.setAdapter(mLoadMoreWrapper);

    }

    /**
     * 提示用户,提升友好交互
     *
     * @param info
     */
    @Override
    public void showInfo(String info) {
//ToastUtils.showToast(this, info);
        ToastUtils.showToast(this, info);
    }
}
