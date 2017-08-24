package com.zdv.yuncang;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.device.DeviceManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zdv.yuncang.present.QueryPresent;
import com.zdv.yuncang.utils.D2000V1ScanInitUtils;
import com.zdv.yuncang.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class BaseActivity extends Activity {
    protected static final int RECORD_PROMPT_MSG = 0x06;
    protected static final int SCAN_CLOSED = 1020;
    protected D2000V1ScanInitUtils d2000V1ScanInitUtils;
    protected Boolean isInit = false;
    protected Executor executor;

    protected Context context;
    ProgressDialog progressDialog;
    DeviceManager deviceManager;
    QueryPresent present;
    Utils util;
    boolean stop = false;//网络请求标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        deviceManager = new DeviceManager();
        util = Utils.getInstance();
        present = QueryPresent.getInstance(BaseActivity.this);
    }

    protected void showWaitDialog(String tip) {
        progressDialog = new ProgressDialog(BaseActivity.this);
        progressDialog.setMessage(tip);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setOnDismissListener((dia) -> onProgressDissmiss());
        progressDialog.show();

    }

    /**
     *
     */
    protected void onProgressDissmiss() {
        stop = true;
    }

    protected void hideWaitDialog() {
        if(progressDialog!=null) progressDialog.dismiss();
    }


    protected void showDialog(int type, String title, String tip, String posbtn, String negbtn) {
        AlertDialog dialog = null;
        if (negbtn == null) {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .create();
        } else {
            dialog = new AlertDialog.Builder(this).setTitle(title)
                    .setMessage(tip)
                    .setPositiveButton(posbtn, (dia, which) -> confirm(type, dia))
                    .setNegativeButton(negbtn, (dia, which) -> cancel(type, dia)).create();
        }
        dialog.setCancelable(false);
        dialog.show();

    }

    protected void showEditDialog(String count) {
        final EditText inputServer = new EditText(BaseActivity.this);
        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputServer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        inputServer.setText(count);
        inputServer.selectAll();
        inputServer.setFocusable(true);
        inputServer.setFocusableInTouchMode(true);
        inputServer.requestFocus();

        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setTitle("请输入数量").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", (dialog, type) -> edit(inputServer, type, dialog));
        builder.setNegativeButton("取消", (dialog, type) -> cancelEdit(inputServer, type, dialog));
        builder.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) inputServer.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(inputServer, 0);
                           }
                       },
                500);

    }

    public String currentDate(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if(time!=-1) {
            return sdf.format(new Date(time));
        }
        return sdf.format(new Date());
    }

    protected void confirm(int type, DialogInterface dialog) {
        dialog.dismiss();
    }

    protected void cancel(int type, DialogInterface dialog) {
        dialog.dismiss();
    }


    protected void edit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }
    protected void cancelEdit(EditText inputServer, int type, DialogInterface dialog) {
        dialog.dismiss();
    }


}
