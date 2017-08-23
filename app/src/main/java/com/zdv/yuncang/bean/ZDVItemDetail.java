package com.zdv.yuncang.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/6/25 14:37
 */

public class ZDVItemDetail implements Parcelable {
    String ocode;
    String pcode;
    String barcode;
    String name;
    String unit;
    String act_num;
    String number;
    String price;
    String memprice;
    String cost_type;
    String address;
    String item_code;
    String remark;
    String cwpsl;
    String status = "0";

    public String getCwpsl() {
        return cwpsl;
    }

    public void setCwpsl(String cwpsl) {
        this.cwpsl = cwpsl;
    }


    public String getOcode() {
        return ocode;
    }

    public void setOcode(String ocode) {
        this.ocode = ocode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAct_num() {
        return act_num;
    }

    public void setAct_num(String act_num) {
        this.act_num = act_num;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMemprice() {
        return memprice;
    }

    public void setMemprice(String memprice) {
        this.memprice = memprice;
    }

    public String getCost_type() {
        return cost_type;
    }

    public void setCost_type(String cost_type) {
        this.cost_type = cost_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ocode);
        parcel.writeString(pcode);
        parcel.writeString(barcode);
        parcel.writeString(name);
        parcel.writeString(unit);
        parcel.writeString(act_num);
        parcel.writeString(number);
        parcel.writeString(price);
        parcel.writeString(memprice);
        parcel.writeString(cost_type);
        parcel.writeString(address);
        parcel.writeString(item_code);
        parcel.writeString(remark);
        parcel.writeString(cwpsl);
        parcel.writeString(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected ZDVItemDetail(Parcel in) {
        ocode= in.readString();
        pcode= in.readString();
        barcode= in.readString();
        name= in.readString();
        unit= in.readString();
        act_num= in.readString();
        number= in.readString();
        price= in.readString();
        memprice= in.readString();
        cost_type= in.readString();
        address= in.readString();
        item_code= in.readString();
        remark= in.readString();
        cwpsl= in.readString();
        status= in.readString();
    }

    public static final Creator<ZDVItemDetail> CREATOR = new Creator<ZDVItemDetail>() {
        @Override
        public ZDVItemDetail createFromParcel(Parcel in) {
            return new ZDVItemDetail(in);
        }

        @Override
        public ZDVItemDetail[] newArray(int size) {
            return new ZDVItemDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj_) {
        if(!(obj_ instanceof ZDVItemDetail)){
            return false;
        }
        ZDVItemDetail obj = (ZDVItemDetail)obj_;
        if (obj.getPcode() != null && obj.getPcode() != null) {
            if (obj.getPcode() == getPcode() && obj.getOcode() == getOcode()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        String id_str = getPcode()+getOcode();
        int ids = id_str.hashCode();
        return ids;
    }
}
