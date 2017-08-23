package com.zdv.yuncang.model;


import com.zdv.yuncang.bean.SynergyOrderInfo;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.SynergyOrderPostResult;
import com.zdv.yuncang.bean.WandiantongLoginInfo;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xyl on 2017/4/6.
 */

public interface IRequestMode {

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=SearchOrderBatchByTime")
    Observable<SynergyOrderInfo> QueryOrder(@Field("secret") String secret, @Field("ucode") String ucode, @Field("starttime") String starttime, @Field("stoptime") String stoptime);

    @GET("synergyMallServcie/order/getDetailByScmForZDW.jhtml?")
    Observable<ArrayList<SynergyOrderItemInfo>> QueryOrderSingle(@Query("ddh") String ddh);

    @FormUrlEncoded
    @POST("index.php?g=Api&m=Order&a=UpdateActualNumOfProduct")
    Observable<SynergyOrderPostResult> CommitOrder(@Field("secret") String secret, @Field("ucode") String ucode, @Field("ocode") String ocode,
                                                   @Field("pcode") String pcode,   @Field("num") String num);


    @FormUrlEncoded
    @POST("index.php?g=Api&m=Index&a=Login")
    Observable<WandiantongLoginInfo> Login(@Field("username") String username, @Field("password") String password, @Field("company_id") String company_id);

}
