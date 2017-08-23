package com.zdv.yuncang.present;


import com.zdv.yuncang.ZDVOrderDetailItem;
import com.zdv.yuncang.ZDVOrderItem;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.ZDVItemDetail;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IDbPresent {
    void InsertReplaceSynergyOrder(SynergyOrderItemInfo item);
    void InsertReplaceSynergyMer(ZDVItemDetail item);
    void DeleteSynergyOrder(SynergyOrderItemInfo item);
    void DeleteSynergyOrderDBBean(ZDVOrderItem item);
    void DeleteSynergyMer(ZDVItemDetail item);
    void DeleteSynergyMerDBBean(ZDVOrderDetailItem item);

    void GetSynergyOrder();
    void GetSynergyMer();
}
