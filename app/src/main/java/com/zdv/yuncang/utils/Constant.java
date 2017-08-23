package com.zdv.yuncang.utils;

import com.zdv.yuncang.ZDVOrderDetailItem;
import com.zdv.yuncang.bean.ZDVItemDetail;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xiaoyl on 2017/4/6.
 */

public class Constant {


    //public static final String URL_SYNERGY = "http://119.147.210.211:9999/";//星利源
   // public static final String URL_SYNERGY = "http://10.1.10.141:8889/";//星利源
    public static final String URL_SYNERGY = "http://www.synergyiclub.com/";//星利源正式地址
    public static final String URL_BAIBAO = "http://www.o2obaibao.com/index.php/";//百宝
    public static final String URL_WANDIAN = "http://wdt.qianhaiwei.com/ThinkCmf/";//万店通


    public static final int DEFAULT_TIMEOUT = 10*60;//超时时间(S)

    public static final int CAHCE_WDT_MERCHANDISE = 2020;//缓存更新标志(万店通商品)
    public static final int CAHCE_SYNERGY_ORDER = CAHCE_WDT_MERCHANDISE+1;//缓存更新标志(星利源订单)

    public static final String PUBLIC_BMOB_KEY = "b8814fa71c73135a03bf09cd8f93f0e0";//BMOB APP KEY
    public static  String MESSAGE_UPDATE_TIP = "";//


    public static  int activity = 0;

    public static HashMap<String,String> cookie  = new HashMap<>();

    public static ArrayList<ArrayList<ZDVOrderDetailItem>> cache_detail_items  = new ArrayList<>();
    public static ArrayList<ArrayList<ZDVItemDetail>> detail_items  = new ArrayList<>();


}
