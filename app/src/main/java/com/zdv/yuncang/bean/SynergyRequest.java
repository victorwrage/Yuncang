package com.zdv.yuncang.bean;

/**
 * Info: 获取订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/21 10:00
 */

public class SynergyRequest {

    private String secret="";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStoptime() {
        return stoptime;
    }

    public void setStoptime(String stoptime) {
        this.stoptime = stoptime;
    }

    private String ucode="";
    private String starttime="";
    private String stoptime="";

}
