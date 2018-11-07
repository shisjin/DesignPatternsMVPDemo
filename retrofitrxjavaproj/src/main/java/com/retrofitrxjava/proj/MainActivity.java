package com.retrofitrxjava.proj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.retrofitrxjava.proj.rxjavautil.RxJavaHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity  extends AppCompatActivity {
   private Unbinder unbinder ;
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder= ButterKnife.bind(this);
    }

    @OnClick(R.id.get_data)
    public void  getData(){
        RxJavaHelper.zip();
    }


}
