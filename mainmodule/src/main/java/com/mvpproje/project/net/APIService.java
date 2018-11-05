package com.mvpproje.project.net;

import com.mvpproje.project.bean.BaseObjectBean;
import com.mvpproje.project.bean.LoginBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/11/5.
 */

public interface APIService {


    @FormUrlEncoded
    @POST("user/login")
    Flowable<BaseObjectBean<LoginBean>> login(@Field("username") String username,
                                              @Field("password") String password);

}
