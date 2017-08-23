package com.zdv.yuncang.bean;

/**
 * Info:数据库操作返回bean
 * Created by xiaoyl
 * 创建时间:2017/5/6 10:00
 */

public class DbResultBean {
   String msg;
   int status;

    public DbResultBean(int status,String msg){
        this.status=status;
        this.msg=msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return msg+status;
    }
}
