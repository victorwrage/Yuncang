package com.zdv.yuncang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdv.yuncang.R;
import com.zdv.yuncang.bean.ZDVItemDetail;

import java.util.ArrayList;


/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/21 11:44
 */
public class OrderItemAdapter extends BaseAdapter {
    ArrayList<ZDVItemDetail> items;
    Context context;
    public OrderItemAdapter(Context context_, ArrayList<ZDVItemDetail> items_){
        items = items_;
        context = context_;
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ZDVItemDetail getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ZDVItemDetail item = items.get(position);

        if(view ==null) {
            ViewHolder viewHolder = new ViewHolder();
            view = initView(item,viewHolder);
        }else{
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.mer_name.setText(item.getName());
            viewHolder.mer_price.setText(item.getPrice());
            viewHolder.mer_count.setText(item.getCwpsl()==null?item.getNumber():item.getCwpsl());
            viewHolder.mer_unit.setText(item.getUnit());
            viewHolder.mer_region.setText("");
            String status = item.getStatus()==null?"0": item.getStatus();
            if(status.equals("1")){
                view.setBackgroundColor(context.getResources().getColor(R.color.green));
            }else{
                view.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            }
        }
        return view;
    }

    private View initView(ZDVItemDetail item, ViewHolder viewHolder){

        View view = View.inflate(context, R.layout.order_item_detail,null);
        viewHolder.mer_name = (TextView) view.findViewById(R.id.mer_name);
        viewHolder.mer_price = (TextView) view.findViewById(R.id.mer_price);
        viewHolder.mer_count = (TextView) view.findViewById(R.id.mer_count);
        viewHolder.mer_unit = (TextView) view.findViewById(R.id.mer_unit);
        viewHolder.mer_region = (TextView) view.findViewById(R.id.mer_region);

        viewHolder.mer_name.setText(item.getName());
        viewHolder.mer_price.setText(item.getPrice());
        viewHolder.mer_count.setText(item.getCwpsl()==null?item.getNumber():item.getCwpsl());
        viewHolder.mer_unit.setText(item.getUnit());
        viewHolder.mer_region.setText("");
        String status = item.getStatus()==null?"0": item.getStatus();
        if(status.equals("1")){
            view.setBackgroundColor(context.getResources().getColor(R.color.green));
        }else{
            view.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }

        view.setTag(viewHolder);
        return view;
    }

      class ViewHolder{
         TextView mer_name,mer_price,mer_count,mer_unit,mer_region;
    }
}
