package com.mvpproje.project.model;

import com.mvpproje.project.bean.BaseObjectBean;
import com.mvpproje.project.bean.LoginBean;
import com.mvpproje.project.contract.MainContract;

import io.reactivex.Flowable;

/**
 * Created by Administrator on 2018/11/5.
 */

public class MainModel implements MainContract.Model {
    @Override
    public Flowable<BaseObjectBean<LoginBean>> login(String username, String password) {

        return null;
    }
}
