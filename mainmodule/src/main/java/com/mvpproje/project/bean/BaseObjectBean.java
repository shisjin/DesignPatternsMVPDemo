package com.mvpproje.project.bean;

/**
 * Created by Administrator on 2018/11/5.
 */

public class BaseObjectBean<T> {
    /**
     * status : 1
     * msg : 获取成功
     * result : {} 对象
     */

    private int errorCode;
    private String errorMsg;
    private T resule;
    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResule() {
        return resule;
    }

    public void setResule(T resule) {
        this.resule = resule;
    }



}
