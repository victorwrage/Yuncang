package com.zdv.yuncang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.socks.library.KLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/13 17:21
 */

public class Utils {
    private static Utils instance= null;
    private Utils(){

    }

    public static synchronized Utils getInstance(){

        if(instance == null){
            synchronized (Utils.class) {
                if(instance == null) {
                    return new Utils();
                }
            }
        }
        return instance;
    }


    /**
     * 判断金额的格式，必须精确到小数点后两位
     */
    public String verify(String val) {

        int p = val.indexOf(".") ;
        int l = val.length();
        if (  !val.equals(".")&& (val != null)
                && (!val.isEmpty())
                && (Float.valueOf(val) != 0)) {
            StringBuilder sb = new StringBuilder();
            for(char s:val.toCharArray()){
                sb.append(s);
            }
            if(p!=-1) {
                switch (l - p) {

                    case 1:
                        sb.append('0').append('0');
                        break;
                    case 2:
                        sb.append('0');
                        break;
                    default:
                        break;
                }
            }else{
                sb.append('.').append('0').append('0');
            }
            val = sb.toString();


        } else {
            val = "0.00";
            //   VToast.toast(context, "请输入的大于0的金额 !");
        }
        return val;
    }

    public long ValidateFormat(String date){

        long result = -1L;
        // 指定日期格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
            format.setLenient(false);
            result = format.parse(date).getTime();
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            KLog.v("ValidateFormat error");
        }
        return result;
    }


    public  long getTodayZero() {
        Date date = new Date();
        long l = 24*60*60*1000; //每天的毫秒数
        //date.getTime()是现在的毫秒数，它 减去 当天零点到现在的毫秒数（ 现在的毫秒数%一天总的毫秒数，取余。），理论上等于零点的毫秒数，不过这个毫秒数是UTC+0时区的。
        //减8个小时的毫秒值是为了解决时区的问题。
        return (date.getTime() - (date.getTime()%l) - (long)8* 60 * 60 *1000- (long)24* 60 * 60 *1000);
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public String UrlEnco(String secret){
        String encode = "";
        try {
            encode = URLEncoder.encode(secret, "UTF-8");
            KLog.v(encode);
        } catch (UnsupportedEncodingException e) {
            KLog.v("encode error" + e.getMessage());
        }
        return encode;
    }


}
