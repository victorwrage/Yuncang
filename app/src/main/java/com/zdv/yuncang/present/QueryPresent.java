package com.zdv.yuncang.present;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zdv.yuncang.bean.SynergyOrderInfo;
import com.zdv.yuncang.bean.SynergyOrderPostInfo;
import com.zdv.yuncang.bean.SynergyOrderPostResult;
import com.zdv.yuncang.bean.SynergyRequest;
import com.zdv.yuncang.bean.WandiantongLoginInfo;
import com.zdv.yuncang.db.CloudDBUtil;
import com.zdv.yuncang.model.IRequestMode;
import com.zdv.yuncang.model.converter.CustomGsonConverter;
import com.zdv.yuncang.model.converter.CustomXmlConverter;
import com.zdv.yuncang.utils.Constant;
import com.zdv.yuncang.view.ILoginView;
import com.zdv.yuncang.view.IOrderView;
import com.zdv.yuncang.view.IView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/4/6.
 */

public class QueryPresent implements IRequestPresent {
    private IView iView;
    private Context context;
    private IRequestMode iRequestMode;
    private CloudDBUtil dbUtil;
    private static QueryPresent instance = null;

    public void setView(Activity activity) {
        iView = (IView) activity;
    }

    public void setView(Fragment fragment) {
        iView = (IView) fragment;
    }

    private QueryPresent(Context context_) {
        context = context_;
    }

    public static synchronized QueryPresent getInstance(Context context) {
        if (instance == null) {
            return new QueryPresent(context);
        }
        return instance;
    }

    public void initRetrofit(String url, boolean isXml) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();
        try {
            if (isXml) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(CustomXmlConverter.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            } else {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                iRequestMode = retrofit.create(IRequestMode.class);
            }

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    public void initRetrofit2(String url, boolean isXml) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(CustomGsonConverter.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(new OkHttpClient.Builder()
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                            .addNetworkInterceptor(
                                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .build();
            iRequestMode = retrofit.create(IRequestMode.class);

        } catch (IllegalArgumentException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void QueryOder(SynergyRequest synergyRequest) {

        iRequestMode.QueryOrder(synergyRequest.getSecret(), synergyRequest.getUcode(), synergyRequest.getStarttime(),synergyRequest.getStoptime())
                .onErrorReturn(s -> new SynergyOrderInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrder(s));
    }


    @Override
    public void Login(String username, String password, String company) {
        iRequestMode.Login(username, password, company)
                .onErrorReturn(s -> new WandiantongLoginInfo())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((ILoginView) iView).ResolveLoginInfo(s));
    }


    @Override
    public void CommitOrder(SynergyOrderPostInfo data) {
        Gson gson = new Gson();
        String pcodes =  gson.toJson( (String[])data.getPcode().toArray(new String[0]));
        String nums =    gson.toJson( (String[])data.getNum().toArray(new String[0]));


        iRequestMode.CommitOrder(data.getSecret(),data.getUcode(),data.getOcode(),pcodes,nums)
                .onErrorReturn(s -> new SynergyOrderPostResult())
               // .doOnError(s -> s.fillInStackTrace())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> ((IOrderView) iView).ResolveOrderCommit(s));

    }



}
