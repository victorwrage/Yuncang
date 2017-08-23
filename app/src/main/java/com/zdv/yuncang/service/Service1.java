package com.zdv.yuncang.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.socks.library.KLog;
import com.zdv.yuncang.MainActivity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Info: 进程常驻
 * Created by xiaoyl
 * 创建时间:2017/4/12 16:19
 */

public class Service1 extends Service {

    @Override
    public void onCreate() {
        KLog.v("victor--Service---onCreate");
        if (!isRunning()) {
            KLog.v("victor--Service---onCreate--startApp");
            Intent intent1 = new Intent();
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent1.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent1);
        }
        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.v("victor--onStartCommand");

        return START_STICKY;
    }

    private boolean isRunning() {
        /*if(sp.getBoolean("enableExit",false)){
            timer.cancel();
            return true;
        }*/
        final int PROCESS_STATE_TOP = 2;
        ActivityManager.RunningAppProcessInfo currentInfo = null;
        Field field = null;
        try {
            field = ActivityManager.RunningAppProcessInfo.class.getDeclaredField("processState");
        } catch (Exception ignored) {
        }
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appList = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo app : appList) {
            if (app.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                    && app.importanceReasonCode == ActivityManager.RunningAppProcessInfo.REASON_UNKNOWN) {
                Integer state = null;
                try {
                    state = field.getInt(app);
                } catch (Exception e) {
                }
                if (state != null && state == PROCESS_STATE_TOP) {
                    currentInfo = app;
                    break;
                }
            }
        }
        if (currentInfo != null) {
            for (String packageName : currentInfo.pkgList) {
                if (getPackageName().equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
