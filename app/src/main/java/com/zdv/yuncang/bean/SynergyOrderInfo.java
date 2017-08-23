package com.zdv.yuncang.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Info: 从星利源得到的客户信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class SynergyOrderInfo implements Parcelable {
    int errcode;
    String errmsg;
    SynergyOrderItemInfo content[];

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public SynergyOrderItemInfo[] getContent() {
        return content;
    }

    public void setContent(SynergyOrderItemInfo[] content) {
        this.content = content;
    }

    public int getShoptype() {
        return shoptype;
    }

    public void setShoptype(int shoptype) {
        this.shoptype = shoptype;
    }

    int shoptype;


    protected SynergyOrderInfo(Parcel in) {
        errcode = in.readInt();
        errmsg = in.readString();
        Parcelable[] parcelables  = in.readParcelableArray(SynergyOrderItemInfo.class.getClassLoader());
        if (parcelables != null) {
            content = Arrays.copyOf(parcelables, parcelables.length, SynergyOrderItemInfo[].class);
        }
        shoptype = in.readInt();

    }
    public SynergyOrderInfo(){

    }
    public static final Creator<SynergyOrderInfo> CREATOR = new Creator<SynergyOrderInfo>() {
        @Override
        public SynergyOrderInfo createFromParcel(Parcel in) {
            return new SynergyOrderInfo(in);
        }

        @Override
        public SynergyOrderInfo[] newArray(int size) {
            return new SynergyOrderInfo[size];
        }
    };


    @Override
    public String toString() {
        return errcode + "--" + errmsg + "--" + content.length + "--" + shoptype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(errcode);
        dest.writeString(errmsg);
        dest.writeParcelableArray(content,flags);
        dest.writeInt(shoptype);
    }

}
