package com.zdv.yuncang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdv.yuncang.R;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;

import java.util.ArrayList;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/21 11:44
 */

public class OrderAdapter extends BaseAdapter {
    ArrayList<SynergyOrderItemInfo> items;
    Context context;
    public OrderAdapter(Context context_, ArrayList<SynergyOrderItemInfo> items_){
        items = items_;
        context = context_;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SynergyOrderItemInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        SynergyOrderItemInfo item = items.get(position);

        if(view ==null) {
            ViewHolder viewHolder = new ViewHolder();
            view = initView(item,viewHolder);

        }else{
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.order_no.setText(item.getCode());
            viewHolder.order_region.setText("");
            viewHolder.order_cash.setText(item.getTotalprice());

            switch(Integer.parseInt(item.getSolve())){
                case 0:
                    viewHolder.order_status.setText("已提交");
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.gray));
                    break;
                case 1:
                    viewHolder.order_status.setText("未处理");
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.red));
                    break;
                case 2:
                    viewHolder.order_status.setText("已拣货");
                    viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.green));
                    break;
            }
        }
        return view;
    }

    private View initView(SynergyOrderItemInfo item, ViewHolder viewHolder){
        View view = View.inflate(context, R.layout.order_item,null);
        viewHolder.order_no = (TextView) view.findViewById(R.id.order_no);
        viewHolder.order_region = (TextView) view.findViewById(R.id.order_region);
        viewHolder.order_cash = (TextView) view.findViewById(R.id.order_cash);
        viewHolder.order_status = (TextView) view.findViewById(R.id.order_status);
        viewHolder.order_no.setText(item.getCode());
        viewHolder.order_region.setText("");
        viewHolder.order_cash.setText(item.getTotalprice());
        switch(Integer.parseInt(item.getSolve())){
            case 0:
                viewHolder.order_status.setText("已提交");
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.gray));
                break;
            case 1:
                viewHolder.order_status.setText("未处理");
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 2:
                viewHolder.order_status.setText("已拣货");
                viewHolder.order_status.setTextColor(context.getResources().getColor(R.color.green));
                break;
        }
        view.setTag(viewHolder);
        return view;
    }

      class ViewHolder{
         TextView order_no,order_region,order_cash,order_status;
    }
}
