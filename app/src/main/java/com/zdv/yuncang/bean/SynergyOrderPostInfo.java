package com.zdv.yuncang.bean;

import java.util.ArrayList;

/**
 * Info: 提交的订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/24 14:36
 */

public class SynergyOrderPostInfo {

   String secret;
   String ucode;
   String ocode;
   ArrayList<String> pcode;
   ArrayList<String> num;

    public ArrayList<String> getPcode() {
        return pcode;
    }

    public void setPcode(ArrayList<String> pcode) {
        this.pcode = pcode;
    }

    public ArrayList<String> getNum() {
        return num;
    }

    public void setNum(ArrayList<String> num) {
        this.num = num;
    }

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

    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }


}
