package com.mvpproje.project.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/5.
 */

public class BaseArrayBean<T> {
    /**
     * status : 1
     * msg : 获取成功
     * result : [] 数组
     */
    private int errorCode;
    private String errorMsg;
    private List<T> list;

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

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
