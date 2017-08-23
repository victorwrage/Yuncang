package com.zdv.yuncang.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.socks.library.KLog;
import com.zdv.yuncang.CaptureActivity;
import com.zdv.yuncang.MainActivity;
import com.zdv.yuncang.OrderItemActivity;
import com.zdv.yuncang.utils.Constant;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Info:进程常驻
 * Created by xiaoyl
 * 创建时间:2017/4/12 16:20
 */

public class RestartService extends Service {
    private Timer timer;
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        updatTimer();
        super.onCreate();
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

    private void updatTimer() {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                if (!isRunning()) {
                    KLog.v("wrageee" + "app restart now"+Constant.activity);
                   // recovery(RestartService.this);
                    Intent intent1 = new Intent();
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    switch (Constant.activity) {
                        case 0:
                            intent1.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(intent1);
                            break;
                        case 1:
                            intent1.setClass(getApplicationContext(), CaptureActivity.class);
                            startActivity(intent1);
                            break;
                        case 2:
                            intent1.setClass(getApplicationContext(), OrderItemActivity.class);
                            startActivity(intent1);
                            break;
                    }

                }
            }
        }, 200, 1000);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    private void recovery(Context context) {

        ActivityManager manager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> task_info = manager
                .getRunningTasks(20);

        String className = "";

        for (int i = 0; i < task_info.size(); i++) {

            if ("com.o2obaibao.baibaoapp".equals(task_info
                    .get(i).topActivity.getPackageName())) {
                manager.moveTaskToFront(task_info.get(i).id, ActivityManager.MOVE_TASK_WITH_HOME);//关键
                className = task_info.get(i).topActivity .getClassName();


                Intent intentgo = new Intent();
                intentgo.setAction(Intent.ACTION_MAIN);
                intentgo.addCategory(Intent.CATEGORY_LAUNCHER);
                try {
                    intentgo.setComponent(new ComponentName(context, Class.forName(className)));//
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                intentgo.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(intentgo);

            }
        }
    }
}
