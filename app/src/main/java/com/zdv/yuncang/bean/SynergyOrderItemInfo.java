package com.zdv.yuncang.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Info: 单条订单信息
 * Created by xiaoyl
 * 创建时间:2017/4/17 14:36
 */

public class SynergyOrderItemInfo implements Parcelable {
    String code;//
    String ucode;
    String createtime;//物品名称
    String totalprice;//物品编码
    String payprice;//物品
    String paystate;
    String status;
    String coster;
    String company_id;
    String solve;
    String shoptype;
    String remark;
    ZDVItemDetail ZdvOrderLists[];

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(ucode);
        dest.writeString(createtime);
        dest.writeString(totalprice);
        dest.writeString(payprice);
        dest.writeString(paystate);
        dest.writeString(status);
        dest.writeString(coster);
        dest.writeString(company_id);
        dest.writeString(solve);
        dest.writeString(shoptype);
        dest.writeString(remark);

    }


    protected SynergyOrderItemInfo(Parcel in) {
        code = in.readString();
        ucode = in.readString();
        createtime = in.readString();
        totalprice = in.readString();
        payprice = in.readString();
        paystate = in.readString();
        status = in.readString();
        coster = in.readString();
        company_id = in.readString();
        solve = in.readString();
        shoptype = in.readString();
        remark = in.readString();

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getPayprice() {
        return payprice;
    }

    public void setPayprice(String payprice) {
        this.payprice = payprice;
    }

    public String getPaystate() {
        return paystate;
    }

    public void setPaystate(String paystate) {
        this.paystate = paystate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoster() {
        return coster;
    }

    public void setCoster(String coster) {
        this.coster = coster;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getSolve() {
        return solve;
    }

    public void setSolve(String solve) {
        this.solve = solve;
    }

    public String getShoptype() {
        return shoptype;
    }

    public void setShoptype(String shoptype) {
        this.shoptype = shoptype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZDVItemDetail[] getZdvOrderLists() {
        return ZdvOrderLists;
    }

    public void setZdvOrderLists(ZDVItemDetail[] zdvOrderLists) {
        ZdvOrderLists = zdvOrderLists;
    }




    @Override
    public String toString() {
        return code + "--" + ucode + "--" + createtime + "--" + totalprice + "--" + payprice+"--"+paystate+"--"+coster+"--"+company_id
                +"--"+solve+"--"+shoptype+"--"+remark+"--"+ZdvOrderLists.length;
    }

    public static final Creator<SynergyOrderItemInfo> CREATOR = new Creator<SynergyOrderItemInfo>() {
        @Override
        public SynergyOrderItemInfo createFromParcel(Parcel in) {
            return new SynergyOrderItemInfo(in);
        }

        @Override
        public SynergyOrderItemInfo[] newArray(int size) {
            return new SynergyOrderItemInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public boolean equals(Object obj_) {
        if(!(obj_ instanceof SynergyOrderItemInfo)){
            return false;
        }
        SynergyOrderItemInfo obj = (SynergyOrderItemInfo)obj_;
        if (obj.getCode() != null && obj.getCode() != null) {
            if (obj.getCode() == getCode()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        String id_str = getCode();
        int ids = id_str.hashCode();
        return ids;
    }
}
