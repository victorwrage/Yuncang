package com.zdv.yuncang.present;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.zdv.yuncang.ZDVOrderDetailItem;
import com.zdv.yuncang.ZDVOrderItem;
import com.zdv.yuncang.bean.DbResultBean;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.ZDVItemDetail;
import com.zdv.yuncang.db.CloudDBUtil;
import com.zdv.yuncang.utils.Constant;
import com.zdv.yuncang.view.IDbView;
import com.zdv.yuncang.view.IView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/4/6.
 */

public class DbPresent implements IDbPresent {
    private IView iView;
    private Context context;
    private IDbPresent iRequestMode;
    private static CloudDBUtil dbUtil;
    private static DbPresent instance = null;

    public void setView(Activity activity) {
        iView = (IView) activity;
    }

    public void setView(Fragment fragment) {
        iView = (IView) fragment;
    }

    private DbPresent(Context context_) {
        context = context_;
    }

    public static synchronized DbPresent getInstance(Context context) {
        if (instance == null) {
            dbUtil = new CloudDBUtil(context);
            return new DbPresent(context);
        }
        return instance;
    }



    @Override
    public void InsertReplaceSynergyOrder(SynergyOrderItemInfo item) {
        Observable.just(dbUtil.insertOrReplaceSynergyOrder(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveInsertDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"订单缓存更新成功":"订单缓存更新失败")));
    }

    @Override
    public void InsertReplaceSynergyMer( ZDVItemDetail item) {
        Observable.just(dbUtil.insertOrReplaceSynergyMer(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveInsertMerDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"物品缓存更新成功":"物品缓存更新失败")));
    }

    @Override
    public void DeleteSynergyOrderDBBean(ZDVOrderItem item) {
        Observable.just(dbUtil.deleteSynergyOrderDBBean(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"订单缓存删除成功":"订单缓存删除失败")));
    }

    @Override
    public void DeleteSynergyOrder(SynergyOrderItemInfo item) {
        Observable.just(dbUtil.deleteSynergyOrder(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"订单缓存删除成功":"订单缓存删除失败")));
    }

    @Override
    public void DeleteSynergyMerDBBean(ZDVOrderDetailItem item) {
        Observable.just(dbUtil.deleteSynergyMerDBBean(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteMerDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"订单缓存删除成功":"订单缓存删除失败")));

    }

    @Override
    public void DeleteSynergyMer(ZDVItemDetail item) {
        Observable.just(dbUtil.deleteSynergyMer(item))
                .onErrorReturn(s -> false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveDeleteMerDb(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s?1:0,s?"订单缓存删除成功":"订单缓存删除失败")));

    }


    @Override
    public void GetSynergyOrder() {
        Observable.just(dbUtil.listAllSynergyOrder())
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveReadDbSynergyOrder(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s.size()>0?1:0,s.size()>0?"订单缓存获取成功":"订单缓存获取失败"),
                                s));
    }

    @Override
    public void GetSynergyMer() {
        Observable.just(dbUtil.listAllSynergyMer())
                .onErrorReturn(s -> new ArrayList<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IDbView) iView).
                        ResolveReadDbSynergyMer(Constant.CAHCE_SYNERGY_ORDER,new DbResultBean(s.size()>0?1:0,s.size()>0?"商品缓存获取成功":"商品缓存获取失败"),
                                s));
    }
}
