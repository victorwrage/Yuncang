package com.zdv.yuncang.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Info:进程常驻
 * Created by xiaoyl
 * 创建时间:2017/4/12 16:19
 */

public class Service2 extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
