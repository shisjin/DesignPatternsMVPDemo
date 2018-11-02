package com.mvpproje.project.base;

/**
 * Created by Administrator on 2018/11/2.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements  BaseView {

    protected T mPresenter;

    @Override
    public void onDestroyView() {
        if (mPresenter!=null){
            mPresenter.detachView();
        }
        super.onDestroyView();
    }
}
