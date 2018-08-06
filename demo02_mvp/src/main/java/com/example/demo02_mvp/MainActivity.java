package com.example.demo02_mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo02_mvp.base.BaseActivity;
import com.example.demo02_mvp.ui.movie.MovieActivity;
import com.example.demo02_mvp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_title_back)
    ImageView ivTitleBack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.btn_mvp_demo_one)
    Button btnMvpDemoOne;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        ivTitleBack.setVisibility(View.GONE);
        tvTitleName.setText(R.string.main_title);
    }

    @OnClick(R.id.btn_mvp_demo_one)
    public void onViewClicked() {
//        Intent intent = new Intent(this, MovieActivity.class);
//        startActivity(intent);
        MovieActivity.actionStart(this);
    }

}
