package com.mvpproje.project.base;
import com.uber.autodispose.AutoDisposeConverter;
/**
 * Created by Administrator on 2018/11/2.
 */

public interface BaseView {


    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();

}
