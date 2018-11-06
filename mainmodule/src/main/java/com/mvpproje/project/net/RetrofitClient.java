package com.mvpproje.project.net;

import com.mvpproje.project.custoconverter.FastJsonConverterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Administrator on 2018/11/5.
 */

public class RetrofitClient {
    private static  RetrofitClient instance;
    private APIService apiService;
    private String baseUrl = "http://www.wanandroid.com/";

    public static RetrofitClient getInstance(){
        if (instance==null){
            synchronized (RetrofitClient.class){
                if (instance==null){
                    instance=new RetrofitClient();
                }
            }
        }
        return instance;
    }


    /**
     * 设置Header
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original  = chain.request();
                Request request = original .newBuilder().addHeader("token", "").build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 设置拦截器
     * @return
     */

    private Interceptor getInterceptor() {
       HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor();
        //显示日志
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }


    public APIService getApi(){
        OkHttpClient client = new OkHttpClient.Builder()
                                //添加头header
                                .addInterceptor(getHeaderInterceptor())
                                //添加拦截器
                                .addInterceptor(getInterceptor())
                                .build();
        Retrofit retrofit=new  Retrofit.Builder().client(client)
                                                    //base地址
                                                    .baseUrl(baseUrl)
                                                        //设置数据解析器
                                                   .addConverterFactory(FastJsonConverterFactory.create())
                                                    //.addConverterFactory(GsonConverterFactory.create())
                                                        //设置网络请求适配器，使其支持RxJava与RxAndroid
                                                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                    .build();
        apiService=retrofit.create(APIService.class);
        return apiService;
    }


}
