package com.mvpproje.project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.widget.Toast;

import com.mvpproje.project.base.BaseMvpActivity;
import com.mvpproje.project.bean.BaseObjectBean;
import com.mvpproje.project.bean.LoginBean;
import com.mvpproje.project.contract.MainContract;
import com.mvpproje.project.presenter.MainPresenter;
import com.mvpproje.project.util.ProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresenter>implements MainContract.View {
    @BindView(R.id.et_username_login)
    TextInputEditText etUsernameLogin;
    @BindView(R.id.et_password_login)
    TextInputEditText etPasswordLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @return 帐号
     */
    private String getUsername() {
        return etUsernameLogin.getText().toString().trim();
    }

    /**
     * @return 密码
     */
    private String getPassword() {
        return etPasswordLogin.getText().toString().trim();
    }

    public void showLoading() {
        ProgressDialog.getInstance().show(this);
    }

    @Override
    public void hideLoading() {
        ProgressDialog.getInstance().dismiss();
    }

    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(BaseObjectBean<LoginBean> bean) {
        Toast.makeText(this, bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void initView() {
          mPresenter= new MainPresenter();
          mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutInt() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.btn_signin_login)
    public  void onViewClicked(){
        if (getUsername().isEmpty()||getPassword().isEmpty()){
            Toast.makeText(this, "帐号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.login(getUsername(),getPassword());
    }
}
