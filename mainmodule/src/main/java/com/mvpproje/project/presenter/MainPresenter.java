package com.mvpproje.project.presenter;

import com.mvpproje.project.base.BasePresenter;
import com.mvpproje.project.bean.BaseObjectBean;
import com.mvpproje.project.bean.LoginBean;
import com.mvpproje.project.contract.MainContract;
import com.mvpproje.project.model.MainModel;
import com.mvpproje.project.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/11/6.
 */

public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter{
    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void login(String username, String password) {
        //view是否绑定，如果没有绑定就不执行网络请求
        if (!isViewAttached()){
            return;
        }
        mView.showLoading();
        model.login(username,password).compose(RxScheduler.<BaseObjectBean<LoginBean>>Flo_io_main())
                .as(mView.<BaseObjectBean<LoginBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void accept(BaseObjectBean<LoginBean> loginBeanBaseObjectBean) throws Exception {
                        mView.onSuccess(loginBeanBaseObjectBean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });


    }


    /*@Override
    public void login(String username, String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.login(username, password)
                .compose(RxScheduler.<BaseObjectBean<LoginBean>>Flo_io_main())
                .as(mView.<BaseObjectBean<LoginBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void accept(BaseObjectBean<LoginBean> bean) throws Exception {
                        mView.onSuccess(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }*/


}
