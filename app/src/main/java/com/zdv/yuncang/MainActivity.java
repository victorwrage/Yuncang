package com.zdv.yuncang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.socks.library.KLog;
import com.zdv.yuncang.adapter.OrderAdapter;
import com.zdv.yuncang.bean.DbResultBean;
import com.zdv.yuncang.bean.SynergyOrderInfo;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.SynergyOrderPostInfo;
import com.zdv.yuncang.bean.SynergyOrderPostResult;
import com.zdv.yuncang.bean.SynergyRequest;
import com.zdv.yuncang.bean.WandiantongLoginInfo;
import com.zdv.yuncang.bean.ZDVItemDetail;
import com.zdv.yuncang.present.DbPresent;
import com.zdv.yuncang.present.QueryPresent;
import com.zdv.yuncang.service.RestartService;
import com.zdv.yuncang.utils.Constant;
import com.zdv.yuncang.utils.D2000V1ScanInitUtils;
import com.zdv.yuncang.utils.SortOrderComparator;
import com.zdv.yuncang.utils.SortOrderStateComparator;
import com.zdv.yuncang.utils.Utils;
import com.zdv.yuncang.utils.VToast;
import com.zdv.yuncang.view.CustomDatePicker;
import com.zdv.yuncang.view.IDbView;
import com.zdv.yuncang.view.ILoginView;
import com.zdv.yuncang.view.IOrderView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateStatus;

/**
 * 首先请求登录万店通，然后获取订单信息
 */
public class MainActivity extends BaseActivity implements IOrderView, ILoginView, IDbView {

    private static final String SUCCESS = "success";
    private static final String COOKIE_KEY = "cookie";
    private static final int ORDER_FETCH_SUCCESS = 10;//  订单获取成功
    private static final int ORDER_FETCH_FAIL = ORDER_FETCH_SUCCESS + 1;// 订单获取失败
    private static final int ORDER_FETCH_FAIL_NETWORK = ORDER_FETCH_FAIL + 1;// 订单获取失败(网络错误)
    private static final int ORDER_FETCH_NOT = ORDER_FETCH_FAIL_NETWORK + 1;// 订单没记录
    private static final int ORDER_DB_FETCH_SUCCESS = ORDER_FETCH_NOT + 1;//
    private static final int ORDER_DB_FETCH_FAIL= ORDER_DB_FETCH_SUCCESS + 1;//
    private static final int ORDER_MER_DB_FETCH_SUCCESS = ORDER_DB_FETCH_FAIL + 1;//
    private static final int ORDER_MER_DB_FETCH_FAIL= ORDER_MER_DB_FETCH_SUCCESS + 1;


    private static final int LOGIN_SUCCESS = ORDER_MER_DB_FETCH_FAIL + 1;// 登录万店通成功
    private static final int LOGIN_FAIL = LOGIN_SUCCESS + 1;// 登录万店通成功
    private static final int POST_ITEM_SUCESS = LOGIN_FAIL + 1;//提交单条成功
    private static final int POST_ITEM_FAIL = POST_ITEM_SUCESS + 1;//
    private static final int POST_SUCCESS = POST_ITEM_FAIL + 1;// 提交成功
    private static final int POST_FAIL = POST_SUCCESS + 1;//

    private static final int ORDER_STATUS_VERIFY_POST = 0;//订单已拣货已提交
    private static final int ORDER_STATUS_VERIFY_NOT = 1;//订单未拣货
    private static final int ORDER_STATUS_VERIFY = 2;//订单已拣货

    /**
     * 对话框类型
     */
    private static final int ORDER_HAS_CONFIRM = 1024;
    private static final int EXIT_CONFIRM = ORDER_HAS_CONFIRM + 1;
    private static final int NETWORD_CONFIRM = EXIT_CONFIRM + 1;
    private static final int FETCH_ORDER_CONFIRM = NETWORD_CONFIRM + 1;
    private static final int POST_RETRY_CONFIRM = FETCH_ORDER_CONFIRM + 1;
    private static final int POST_CONFIRM = POST_RETRY_CONFIRM + 1;

    @Bind(R.id.rl_xly_back)
    RelativeLayout rl_xly_back;
    @Bind(R.id.iv_scan)
    ImageView iv_scan;
    @Bind(R.id.iv_picker)
    ImageView iv_picker;
    @Bind(R.id.search_start_tv)
    TextView search_start_tv;
    @Bind(R.id.search_end_tv)
    TextView search_end_tv;
    @Bind(R.id.search_tv)
    TextView search_tv;

    @Bind(R.id.tv_xly_account)
    TextView tv_xly_account;
    @Bind(R.id.tv_xly_jieshou)
    TextView tv_xly_jieshou;
    @Bind(R.id.lv_xyl)
    ListView lv_xyl;
    @Bind(R.id.date_lay)
    LinearLayout date_lay;
    @Bind(R.id.empty_lay)
    RelativeLayout empty_lay;

    CustomDatePicker customDatePicker;

    EditText et_username;
    EditText et_shopid;
    EditText et_password;
    CheckBox chk_remember;
    TextView btn_login;

    View popupWindowView;
    private PopupWindow popupWindow;
    WandiantongLoginInfo cur_user;
    SharedPreferences sp;

    QueryPresent present;
    DbPresent dbPresent;
    Utils util;
    OrderAdapter adapter;
    ArrayList<SynergyOrderItemInfo> items;

    ArrayList<SynergyOrderItemInfo> confirmed_items;



    SynergyOrderItemInfo cur_item;
    boolean needUpdate = false;
    SynergyOrderPostInfo post_item;
    boolean isLogin;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case LOGIN_SUCCESS:
                    hideWaitDialog();
                    isLogin = true;
                    popupWindow.dismiss();
                    fetchZDVOrder();
                    break;
                case LOGIN_FAIL:
                    hideWaitDialog();
                    VToast.toast(context, "登录失败");
                    showPopupWindow();
                    break;
                case ORDER_FETCH_FAIL:
                    VToast.toast(context, "没有订单");
                    hideWaitDialog();
                    break;
                case ORDER_FETCH_FAIL_NETWORK:
                    VToast.toast(context, "网络错误");
                    hideWaitDialog();
                    showDialog(FETCH_ORDER_CONFIRM, "提示", "获取订单失败，重试？", "是", "否");
                    break;
                case ORDER_FETCH_SUCCESS:
                    hideWaitDialog();
                    dbPresent.GetSynergyOrder();//查询订单缓存
                    adapter.notifyDataSetChanged();
                    break;
                case ORDER_FETCH_NOT:
                    hideWaitDialog();
                    break;
                case ORDER_DB_FETCH_SUCCESS:
                    dbPresent.GetSynergyMer();//查询物品缓存
                    adapter.notifyDataSetChanged();
                    break;
                case ORDER_DB_FETCH_FAIL:
                    dbPresent.GetSynergyMer();//查询物品缓存
                    adapter.notifyDataSetChanged();
                    break;
                case ORDER_MER_DB_FETCH_SUCCESS:

                    break;
                case ORDER_MER_DB_FETCH_FAIL:

                    break;
                case POST_ITEM_SUCESS:
                    /** 删除数据库条目*/
                    String delete_order_ids = post_item.getOcode();
                    SynergyOrderItemInfo delete_list_item = null;
                    for(SynergyOrderItemInfo synergyOrderItemInfo:items){
                        if(synergyOrderItemInfo.getCode().equals(delete_order_ids)){
                            delete_list_item = synergyOrderItemInfo;
                            /** 刷新界面*/
                            synergyOrderItemInfo.setSolve(ORDER_STATUS_VERIFY_POST+"");
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    if(delete_list_item!=null) {
                        dbPresent.DeleteSynergyOrder(delete_list_item);
                    }else{
                        KLog.v("错误");
                        return;
                    }
                    ArrayList<ZDVOrderDetailItem> delete_list_detail_item = new ArrayList<>();
                    for (ArrayList<ZDVOrderDetailItem> d_l_s :  Constant.cache_detail_items) {
                        if (d_l_s.get(0).getOcode().equals(delete_order_ids)) {
                            delete_list_detail_item = d_l_s;
                            break;
                        }
                    }
                    for(ZDVOrderDetailItem d_d_i:delete_list_detail_item){
                        dbPresent.DeleteSynergyMerDBBean(d_d_i);
                    }
                    /** 删除内存条目*/
                    Constant.cache_detail_items.remove(delete_list_detail_item);
                    confirmed_items.remove(delete_list_item);

                    //  items.remove(delete_list_item);


                    /** 继续提交*/
                    if(confirmed_items.size()==0){
                        handler.sendEmptyMessage(POST_SUCCESS);
                    }else{
                        doSynergyPost();
                    }
                    break;
                case POST_ITEM_FAIL:
                    hideWaitDialog();
                    showDialog(POST_CONFIRM, "提示", "提交失败，是否继续？", "是", "否");
                    break;
                case POST_SUCCESS:
                    VToast.toast(context,"提交成功");
                    hideWaitDialog();
                    break;

            }
        }
    };

    private void fetchUpdate() {

        Bmob.initialize(this, Constant.PUBLIC_BMOB_KEY);
        // BmobUpdateAgent.initAppVersion(context);
        BmobUpdateAgent.setUpdateListener((updateStatus, updateInfo) -> {
            if (updateStatus == UpdateStatus.Yes) {//版本有更新

            } else if (updateStatus == UpdateStatus.No) {
                KLog.v("版本无更新");
                if (!Constant.MESSAGE_UPDATE_TIP.equals("")) {
                    VToast.toast(context, Constant.MESSAGE_UPDATE_TIP);
                }else{
                    Constant.MESSAGE_UPDATE_TIP = "<<捌号云仓>>是最新版本";
                }
            } else if (updateStatus == UpdateStatus.EmptyField) {//此提示只是提醒开发者关注那些必填项，测试成功后，无需对用户提示
                KLog.v("请检查你AppVersion表的必填项，1、target_size（文件大小）是否填写；2、path或者android_url两者必填其中一项。");
            } else if (updateStatus == UpdateStatus.IGNORED) {
                KLog.v("该版本已被忽略更新");
            } else if (updateStatus == UpdateStatus.ErrorSizeFormat) {
                KLog.v("请检查target_size填写的格式，请使用file.length()方法获取apk大小。");
            } else if (updateStatus == UpdateStatus.TimeOut) {
                KLog.v("查询出错或查询超时");
            }
        });
        BmobUpdateAgent.update(MainActivity.this);
    }

    private void fetchZDVOrder() {
        SynergyRequest request = new SynergyRequest();
        request.setSecret(cur_user.getSecret());
        request.setUcode(cur_user.getContent().getCode());
        //request.setMdlD("1165");
        // long dec = (long) 3600000 * (long) 24 * (long) 1;
        long dec = util.getTodayZero();

        String start_t = currentDate(dec, "yyyy-MM-dd HH:mm:ss");
        String end_t = currentDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        KLog.v(start_t + "----" + end_t);
        request.setStarttime(start_t);
        request.setStoptime(end_t);
        present.initRetrofit(Constant.URL_WANDIAN, false);
        showWaitDialog("正在查询订单");
        present.QueryOder(request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
        } else {
            KLog.v("请稍后");
            showWaitDialog("请稍后");
            promptHandler.postDelayed(() -> hideWaitDialog(), 5000);
        }
        KLog.v("onResume" + d2000V1ScanInitUtils.getStart());
        executor.execute(() -> startScan());
    }
    private Handler promptHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECORD_PROMPT_MSG:
                    sendData((String) msg.obj);
                    break;
                case SCAN_CLOSED:

                    break;
                default:
                    break;
            }
        }
    };
    private void sendData(String obj) {
        KLog.v("sendData" + obj);
        cur_item = null;
        Iterator<SynergyOrderItemInfo> v_s = items.iterator();
        while (v_s.hasNext()) {
            SynergyOrderItemInfo a_t = v_s.next();
            if (a_t.getCode().equals(obj.trim())) {
                cur_item = a_t;
            }
        }
        if (cur_item != null) {
            KLog.v("sendData"+ cur_item.getSolve());
            if (Integer.parseInt(cur_item.getSolve()) != ORDER_STATUS_VERIFY_POST) {
                gotoOrderItem();
            } else {
                showWaitDialog("请稍候");
                rl_xly_back.postDelayed( ()->{ hideWaitDialog();
                    scan();},1000);
                VToast.toast(context, "订单已经拣货");
            }
        } else {
            showWaitDialog("请稍候");
            rl_xly_back.postDelayed( ()->{ hideWaitDialog();
                scan();},1000);
            VToast.toast(context, "没有找到对应的单号");
        }
    }

    public void scan() {
        startScan();
    }

    private void startScan() {
        if (!d2000V1ScanInitUtils.getStart()) {
            d2000V1ScanInitUtils.open();
        }
        d2000V1ScanInitUtils.d2000V1ScanOpen();
    }

    @Override
    protected void onStop() {
        KLog.v("onStop");
        super.onStop();
        if (d2000V1ScanInitUtils.getStart()) {
            d2000V1ScanInitUtils.setStart(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        d2000V1ScanInitUtils = D2000V1ScanInitUtils.getInstance(MainActivity.this, promptHandler);
    }

    @Override
    protected void onDestroy() {
        d2000V1ScanInitUtils.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xlyordera);
        ButterKnife.bind(MainActivity.this);
        initDate();
        initView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !isLogin) {
            LoginAuto();
        }
    }

    private void initDate() {
        Constant.activity = 0;
        //  startService(new Intent(MainActivity.this,Service1.class));//进程常驻，
        //  startService(new Intent(MainActivity.this, RestartService.class));
        executor = Executors.newSingleThreadScheduledExecutor();
        sp = getSharedPreferences(COOKIE_KEY, 0);
        present = QueryPresent.getInstance(context);
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.setView(MainActivity.this);
        dbPresent = DbPresent.getInstance(context);
        dbPresent.setView(MainActivity.this);
        util = Utils.getInstance();

        items = new ArrayList<>();
        adapter = new OrderAdapter(context, items);
        lv_xyl.setAdapter(adapter);
    }

    private void onItemClick(int position) {
        date_lay.setVisibility(View.GONE);
        cur_item = items.get(position);
    }

    private void initView() {
        RxView.clicks(rl_xly_back).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Exit());
        RxView.clicks(iv_scan).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Scan());
        RxView.clicks(iv_picker).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> ShowScope());
        RxView.clicks(tv_xly_account).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> showPostDialog());
        RxView.clicks(tv_xly_jieshou).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> VerifyOrder());
        popupWindowView = getLayoutInflater().inflate(R.layout.pop_login, null);
        et_username = (EditText) popupWindowView.findViewById(R.id.et_username);

        lv_xyl.setOnItemClickListener((parent, view, position, id) -> onItemClick(position));
        lv_xyl.setEmptyView(findViewById(R.id.empty_lay));

        et_shopid = (EditText) popupWindowView.findViewById(R.id.et_shopid);
        et_password = (EditText) popupWindowView.findViewById(R.id.et_password);
        btn_login = (TextView) popupWindowView.findViewById(R.id.btn_login);

        chk_remember = (CheckBox) popupWindowView.findViewById(R.id.chk_remember);
        RxView.clicks(btn_login).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Login());

        RxView.clicks(search_start_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Select(1));
        RxView.clicks(search_end_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> Select(2));
        RxView.clicks(search_tv).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(s -> ApplyScope());

        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.AnimationTopFade);
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(() -> onDissmiss());
        et_username.setText(sp.getString("user_name", ""));
        et_password.setText(sp.getString("user_pw", ""));
        et_shopid.setText(sp.getString("shop_id", ""));
        Constant.cookie.put("user_name", sp.getString("user_name", ""));
        Constant.cookie.put("user_pw", sp.getString("user_pw", ""));
        Constant.cookie.put("shop_id", sp.getString("shop_id", ""));


        Drawable drawable = getResources().getDrawable(R.drawable.verify);
        drawable.setBounds(0, 2, 30, 30);
        tv_xly_jieshou.setCompoundDrawables(null, drawable, null, null);

        Drawable drawable2 = getResources().getDrawable(R.drawable.commit);
        drawable2.setBounds(0, 2, 30, 30);
        tv_xly_account.setCompoundDrawables(null, drawable2, null, null);

        Drawable drawable3 = getResources().getDrawable(R.drawable.search);
        drawable3.setBounds(0, 2, 30, 30);
        search_tv.setCompoundDrawables(null, drawable3, null, null);

        Drawable drawable4 = getResources().getDrawable(R.drawable.login);
        drawable4.setBounds(0, 2, 30, 30);
        btn_login.setCompoundDrawables(null, drawable4, null, null);

        fetchUpdate();
    }

    /**
     * 显示日期选择
     */
    private void ShowScope() {
        if (date_lay.getVisibility() == View.VISIBLE) {
            date_lay.setVisibility(View.GONE);
        } else {
            long dec = util.getTodayZero();
            String start_t = currentDate(dec, "yyyy-MM-dd HH:mm:ss");
            search_start_tv.setText(start_t);
            search_end_tv.setText(currentDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
            date_lay.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 日期段查询订单
     */
    private void ApplyScope() {

        date_lay.setVisibility(View.GONE);

        SynergyRequest request = new SynergyRequest();
        request.setSecret(cur_user.getSecret());
        request.setUcode(cur_user.getContent().getCode());

        request.setStarttime(search_start_tv.getText().toString());
        request.setStoptime(search_end_tv.getText().toString());
        present.initRetrofit(Constant.URL_WANDIAN, false);

        showWaitDialog("正在查询订单");
        present.QueryOder(request);
    }

    /**
     * 弹出日期选择框
     *
     * @param type
     */
    private void Select(int type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker(MainActivity.this, time -> {
            long dec = (long) 3600000 * (long) 24 * (long) 7;
            switch (type) {
                case 1:

                    if (util.ValidateFormat(time) >= util.ValidateFormat(search_end_tv.getText().toString())) {
                        VToast.toast(context, "开始日期不能在结束日期之后");
                        return;
                    }

                    if (util.ValidateFormat(search_end_tv.getText().toString()) - util.ValidateFormat(time) > dec) {
                        VToast.toast(context, "查询范围最多为7天");
                        return;
                    }
                    search_start_tv.setText(time + ":00");
                    break;
                case 2:
                    if (util.ValidateFormat(time) <= util.ValidateFormat(search_start_tv.getText().toString())) {
                        VToast.toast(context, "结束日期不能在开始日期之前");
                        return;
                    }

                    if (util.ValidateFormat(time) - util.ValidateFormat(search_start_tv.getText().toString()) > dec) {
                        VToast.toast(context, "查询范围最多为7天");
                        return;
                    }
                    search_end_tv.setText(time + ":00");
                    break;
            }
        }, "2010-01-01 00:00", now);
        customDatePicker.showSpecificTime(true);
        customDatePicker.setIsLoop(true);
        customDatePicker.show(search_start_tv.getText().toString());
    }

    private void Exit() {
       /* Intent service = new Intent(context, RestartService.class);
        stopService(service);
        finish();*/
        showDialog(EXIT_CONFIRM, "提示", "是否退出?", "确认", "取消");
    }

    private void Login() {
        if (!util.isNetworkConnected(context)) {
            VToast.toast(context, "没有网络连接");
            return;
        }

        if (et_username.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入用户名");
        } else if (et_password.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入密码");
        } else if (et_shopid.getText().toString().trim().equals("")) {
            VToast.toast(context, "请输入门店号");
        } else {
            SharedPreferences.Editor editor = sp.edit();
            if (chk_remember.isChecked()) {
                editor.putString("user_name", et_username.getText().toString().trim());
                editor.putString("user_pw", et_password.getText().toString().trim());
                editor.putString("shop_id", et_shopid.getText().toString().trim());
                editor.commit();
            } else {
                editor.clear();
                editor.commit();
            }
            showWaitDialog("正在登录");
            present.Login(et_username.getText().toString().trim(), et_password.getText().toString().trim(), et_shopid.getText().toString().trim());
        }
    }

    /**
     * 扫描订单
     */
    private void Scan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 验证定单数量
     */
    private void VerifyOrder() {
        if (cur_item == null) {
            VToast.toast(context, "请选择一个订单");
            return;
        }
        if (Integer.parseInt(cur_item.getSolve()) == ORDER_STATUS_VERIFY_POST) {
            VToast.toast(context, "订单已经提交，不能拣货");
            return;
        }
        gotoOrderItem();
    }


    private void showPostDialog() {
        if (confirmed_items.size() == 0) {
            VToast.toast(context, "没有已经确认的订单");
            return;
        }
        showDialog(POST_CONFIRM, "提示", "确定提交" + confirmed_items.size() + "个订单吗？提交后不能更改", "是", "否");
    }

    /**
     * 提交订单
     */
    private void Post() {
        KLog.v("Post" + "commit_items-" + confirmed_items.size());
        showWaitDialog("正在提交");
        doSynergyPost();
    }


    /**
     * Post新利源订单状态
     */
    private void doSynergyPost() {
        KLog.v(confirmed_items.get(0).toString());
        SynergyOrderItemInfo cur_post = confirmed_items.get(0);
        ArrayList < String > pcodes = new ArrayList<>();
        ArrayList<String> nums = new ArrayList<>();
        for (ArrayList<ZDVOrderDetailItem> p_d : Constant.cache_detail_items) {
            if (p_d.get(0).getOcode().equals(cur_post.getCode())) {
                for( ZDVOrderDetailItem z_i:p_d) {
                    pcodes.add(z_i.getPcode());
                    nums.add(z_i.getCwpsl());
                }
                break;
            }
        }
        post_item = new SynergyOrderPostInfo();
        post_item.setSecret(cur_user.getSecret());
        post_item.setUcode(cur_user.getContent().getCode());
        post_item.setOcode(cur_post.getCode());
        post_item.setNum(nums);
        post_item.setPcode(pcodes);
        present.initRetrofit(Constant.URL_WANDIAN, false);
        present.CommitOrder(post_item);
    }

    @Override
    public void ResolveOrder(SynergyOrderInfo item) {
        if (item.getErrmsg() == null) {
            handler.sendEmptyMessage(ORDER_FETCH_FAIL_NETWORK);
            return;
        }
        if (!item.getErrmsg().equals(SUCCESS)) {
            VToast.toast(context, "没有查询到订单");
            handler.sendEmptyMessage(ORDER_FETCH_FAIL);
            return;
        }
        if(items==null) {
            items = new ArrayList<>();
        }else{
            items.clear();
        }

        if (Constant.cache_detail_items!= null) {
            Constant.cache_detail_items.clear();
        }else{
            Constant.cache_detail_items = new ArrayList<>();
        }
        if (Constant.detail_items!= null) {
            Constant.detail_items.clear();
        }else{
            Constant.detail_items = new ArrayList<>();
        }
        if (confirmed_items != null) {
            confirmed_items.clear();
        }else{
            confirmed_items = new ArrayList<>();
        }

        SynergyOrderItemInfo iter[] = item.getContent();
        for (SynergyOrderItemInfo i_t : iter) {
            if(i_t.getSolve().equals(ORDER_STATUS_VERIFY_NOT+"")){
                i_t.setSolve(ORDER_STATUS_VERIFY_POST+"");
            }else if(i_t.getSolve().equals(ORDER_STATUS_VERIFY_POST+"")){
                i_t.setSolve(ORDER_STATUS_VERIFY_NOT+"");
            }
            items.add(i_t);
            ArrayList<ZDVItemDetail> t_z_i = new ArrayList<>();
            for(ZDVItemDetail z_i:i_t.getZdvOrderLists()){
                t_z_i.add(z_i);
            }
            Constant.detail_items.add(t_z_i);
        }

        SortOrderComparator sort = new SortOrderComparator();
        Collections.sort(items, sort);
        handler.sendEmptyMessage(ORDER_FETCH_SUCCESS);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bundle bundle = data.getExtras();
            KLog.v(bundle.getString("result"));

            if (!util.isNetworkConnected(context)) {
                VToast.toast(context, "貌似没有网络");
                return;
            }
            Iterator<SynergyOrderItemInfo> v_s = items.iterator();
            while (v_s.hasNext()) {
                SynergyOrderItemInfo a_t = v_s.next();
                if (a_t.getCode().equals(bundle.getString("result"))) {
                    cur_item = a_t;
                }
            }
            if (cur_item != null) {

                if (Integer.parseInt(cur_item.getSolve()) != ORDER_STATUS_VERIFY_POST) {
                    gotoOrderItem();
                } else {
                    VToast.toast(context, "订单已经拣货");
                }
            } else {
                VToast.toast(context, "没有找到对应的单号");
            }
        } else if (resultCode == RESULT_CANCELED) {
        } else if (resultCode == ORDER_HAS_CONFIRM) {//已经修改成功
          /*  showWaitDialog("请稍候");
            rl_xly_back.postDelayed( ()->{ hideWaitDialog();
                startScan();},2000);*/
            //SynergyOrderItemInfo backItem = data.getParcelableExtra("commit_item");
            ArrayList<ZDVItemDetail> backMerItems = data.getParcelableArrayListExtra("commit_mer_items");
            cur_item.setSolve("" + ORDER_STATUS_VERIFY);
            adapter.notifyDataSetChanged();

            ArrayList<ZDVOrderDetailItem> cur_alter_items = new ArrayList<>();
            /** 更新数据库缓存*/
            dbPresent.InsertReplaceSynergyOrder(cur_item);
            for (ZDVItemDetail m_i : backMerItems) {
                KLog.v("getStatus"+m_i.getStatus());
                dbPresent.InsertReplaceSynergyMer(m_i);
            }
            /** 更新内存缓存*/
            Boolean isOrderItemExist = false;
            for(SynergyOrderItemInfo order_i:confirmed_items){
                if(order_i.getCode().equals(cur_item.getCode())){
                    isOrderItemExist = true;
                    break;
                }
            }
            if(!isOrderItemExist){
                confirmed_items.add(cur_item);
            }

            for (ArrayList<ZDVOrderDetailItem> synergyMerItems : Constant.cache_detail_items) {
                if (synergyMerItems.get(0).getOcode().equals(cur_item.getCode())) {
                    cur_alter_items = synergyMerItems;
                }
            }
            Constant.cache_detail_items.remove(cur_alter_items);
            ArrayList<ZDVOrderDetailItem> a_items = new ArrayList<>();
            for (ZDVItemDetail u_item : backMerItems) {
                String ids = u_item.getOcode() + u_item.getPcode();
                ZDVOrderDetailItem add_i = new ZDVOrderDetailItem((long) ids.hashCode(),
                        u_item.getOcode(),
                        u_item.getPcode(),
                        u_item.getBarcode(),
                        u_item.getName(),
                        u_item.getUnit(),
                        u_item.getAct_num(),
                        u_item.getNumber(),
                        u_item.getPrice(),
                        u_item.getMemprice(),
                        u_item.getCost_type(),
                        u_item.getAddress(),
                        u_item.getItem_code(),
                        u_item.getRemark(),
                        u_item.getCwpsl(),
                        u_item.getStatus());
                a_items.add(add_i);
            }
            Constant.cache_detail_items.add(a_items);

        }
    }

    private void gotoOrderItem() {

        d2000V1ScanInitUtils.close();
        d2000V1ScanInitUtils.close3();
        isInit = false;
        Intent intent = new Intent(MainActivity.this, OrderItemActivity.class);
        intent.putExtra("cur_item", cur_item);
        intent.putExtra("secret", cur_user.getSecret());
        intent.putExtra("ucode", cur_user.getContent().getCode());
        intent.putExtra("company_id", cur_user.getContent().getCompany_id());
        startActivityForResult(intent, 0);
    }

    @Override
    protected void confirm(int type, DialogInterface dialog) {
        super.confirm(type, dialog);
        switch (type) {
            case EXIT_CONFIRM:
                Intent service = new Intent(context, RestartService.class);
                stopService(service);
                System.exit(0);
                break;
            case NETWORD_CONFIRM:
                LoginAuto();
                break;
            case FETCH_ORDER_CONFIRM:
                fetchZDVOrder();
                break;
            case POST_RETRY_CONFIRM:
                doSynergyPost();
                break;
            case POST_CONFIRM:
                Post();
                break;

        }
    }

    @Override
    protected void cancel(int type, DialogInterface dialog) {
        super.cancel(type, dialog);
        KLog.v("cancel");
        hideWaitDialog();
    }

    private void LoginAuto() {
        if (sp.getString("user_name", "").equals("")
                || sp.getString("user_pw", "").equals("")
                || sp.getString("shop_id", "").equals("")) {
            showPopupWindow();
            return;
        }
        present.Login(sp.getString("user_name", ""), sp.getString("user_pw", ""), sp.getString("shop_id", ""));
        showWaitDialog("正在登录...");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Exit();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void ResolveLoginInfo(WandiantongLoginInfo info) {
        if (info.getContent() == null) {
            handler.sendEmptyMessage(LOGIN_FAIL);
            return;
        }
        KLog.v(info.toString());
        info.setSecret(util.UrlEnco(info.getSecret()));

        cur_user = info;
        handler.sendEmptyMessage(LOGIN_SUCCESS);
    }

    @Override
    public void ResolveOrderCommit(SynergyOrderPostResult item) {
        KLog.v(item.toString());
        if (item.getErrmsg() != null && item.getErrmsg().equals(SUCCESS)) {
            handler.sendEmptyMessage(POST_ITEM_SUCESS);
            return;
        }
        handler.sendEmptyMessage(POST_ITEM_FAIL);
    }


    private void showPopupWindow() {
        backgroundAlpha(0.5f);
        popupWindow.showAtLocation(getLayoutInflater().inflate(R.layout.activity_xlyordera, null),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void onDissmiss() {
        backgroundAlpha(1.0f);
        if (cur_user == null || cur_user.getSecret() == null) {
            Exit();
        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    @Override
    public void ResolveInsertDb(int type, DbResultBean result) {
        KLog.v(result.toString());
    }

    @Override
    public void ResolveInsertMerDb(int type, DbResultBean result) {
        KLog.v(result.toString());
    }

    @Override
    public void ResolveReadDbSynergyOrder(int type, DbResultBean result, List<ZDVOrderItem> items_) {

        if (items_.size() == 0){
            //  handler.sendEmptyMessage(ORDER_DB_FETCH_FAIL);
        }
        confirmed_items = new ArrayList<>();
        Iterator<SynergyOrderItemInfo> v_s = items.iterator();
        while (v_s.hasNext()) {
            SynergyOrderItemInfo a_t = v_s.next();
            for (ZDVOrderItem i : items_) {
                if (a_t.getCode().equals(i.getCode())) {
                    a_t.setSolve(i.getSolve());
                    if (Integer.parseInt(a_t.getSolve()) == ORDER_STATUS_VERIFY) {
                        confirmed_items.add(a_t);
                    }
                }
            }
        }
        SortOrderStateComparator sort1 = new SortOrderStateComparator();//状态排序
        Collections.sort(items, sort1);
        handler.sendEmptyMessage(ORDER_DB_FETCH_SUCCESS);
    }

    @Override
    public void ResolveReadDbSynergyMer(int type, DbResultBean result, List<ZDVOrderDetailItem> items_) {
        KLog.v(result.toString());
        if (items_.size() == 0) return;
        Constant.cache_detail_items = new ArrayList<>();
        /**组装提交*/
        for (SynergyOrderItemInfo c_s : confirmed_items) {
            ArrayList<ZDVOrderDetailItem> confirmed_zdv_items = new ArrayList<>();
            for (ZDVOrderDetailItem zdvDetailItem : items_) {
                if (zdvDetailItem.getOcode().equals(c_s.getCode())) {
                    confirmed_zdv_items.add(zdvDetailItem);
                }
            }
            KLog.v("confirmed_zdv_items"+ confirmed_zdv_items.size());
            Constant.cache_detail_items.add(confirmed_zdv_items);
        }
        KLog.v("Constant.cache_detail_items"+Constant.cache_detail_items.size());
    }


    @Override
    public void ResolveDeleteDb(int type, DbResultBean result) {
        KLog.v(result.toString());
    }

    @Override
    public void ResolveDeleteMerDb(int type, DbResultBean result) {
        KLog.v(result.toString());
    }
}
