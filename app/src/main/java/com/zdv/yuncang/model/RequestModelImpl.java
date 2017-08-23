package com.zdv.yuncang.model;


import com.zdv.yuncang.bean.SynergyOrderInfo;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.SynergyOrderPostResult;
import com.zdv.yuncang.bean.WandiantongLoginInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.Query;

/**
 * Info:接口实现类
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:42
 */

public class RequestModelImpl implements IRequestMode {
    IRequestMode iRequestMode;

    @Override
    public Observable<SynergyOrderInfo> QueryOrder(@Field("secret") String secret, @Field("ucode") String ucode, @Field("starttime") String starttime, @Field("stoptime") String stoptime) {
        return iRequestMode.QueryOrder( secret, ucode, starttime, stoptime);
    }

    @Override
    public Observable<ArrayList<SynergyOrderItemInfo>> QueryOrderSingle(@Query("ddh") String ddh) {
        return iRequestMode.QueryOrderSingle( ddh);
    }

    @Override
    public Observable<SynergyOrderPostResult> CommitOrder(@Field("secret") String secret, @Field("ucode") String ucode, @Field("ocode") String ocode,
                                                          @Field("pcode") String pcode,   @Field("num") String num) {
        return iRequestMode.CommitOrder(secret, ucode, ocode, pcode, num);
    }


    @Override
    public Observable<WandiantongLoginInfo> Login(@Field("username") String username, @Field("password") String password, @Field("company_id") String company_id) {
        return iRequestMode.Login(username, password,company_id);
    }

}
