package com.mvpproje.project.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/11/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutInt());
        unbinder = ButterKnife.bind(this);
        initView();
    }
    /*
    * 初始化视图
    */
    protected abstract void initView();

    /**
     * 设置布局
     * @return
     */
    protected abstract int getLayoutInt();


    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();

    }
}
