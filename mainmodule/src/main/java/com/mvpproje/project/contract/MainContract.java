package com.mvpproje.project.contract;

import com.mvpproje.project.base.BaseView;
import com.mvpproje.project.bean.BaseObjectBean;
import com.mvpproje.project.bean.LoginBean;

import io.reactivex.Flowable;

/**
 * Created by Administrator on 2018/11/5.
 */

public interface MainContract {
    interface  Model{
        Flowable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView{
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);
        void onSuccess(BaseObjectBean<LoginBean> bean);
    }

    interface Presenter{
        /**
         * 登陆
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
    }

}
