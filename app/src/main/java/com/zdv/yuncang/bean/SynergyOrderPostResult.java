package com.zdv.yuncang.bean;

/**
 * Info: 提交订单信息的结果
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostResult {


    String errcode;//
    String errmsg;//

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return errcode+errmsg;
    }
}
