package com.mvpproje.project.base;

/**
 * Created by Administrator on 2018/11/2.
 */

public class BasePresenter<V extends BaseView> {
    protected V mView;

    /**
     * 绑定view，一般在初始化中调用该方法
     *
     * @param mView view
     */
    public void  attachView(V mView){
        this.mView= mView;
    }

    /**
     * 解除绑定view，一般在onDestroy中调用
     */
    public void detachView(){
        this.mView=null;
    }
    /**
     * View是否绑定
     *
     * @return
     */
    public boolean isViewAttached(){
        return mView!=null;
    }

}
