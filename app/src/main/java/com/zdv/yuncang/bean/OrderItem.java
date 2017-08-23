package com.zdv.yuncang.bean;

/**
 * Info:订单的bean
 * Created by xiaoyl
 * 创建时间:2017/4/17 10:00
 */

public class OrderItem {
   String order_no;
   String order_region;
   String order_cash;
   String order_status;


    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_region() {
        return order_region;
    }

    public void setOrder_region(String order_region) {
        this.order_region = order_region;
    }

    public String getOrder_cash() {
        return order_cash;
    }

    public void setOrder_cash(String order_cash) {
        this.order_cash = order_cash;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return order_no+"|"+order_region+"|"+order_cash+"|"+order_status+"|";
    }
}
