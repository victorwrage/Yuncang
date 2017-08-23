package com.zdv.yuncang.utils;


import com.zdv.yuncang.bean.ZDVItemDetail;

import java.util.Comparator;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/27 9:37
 */

public class SortComparator implements Comparator<ZDVItemDetail> {

    @Override
    public int compare(ZDVItemDetail o1, ZDVItemDetail o2) {

        if(o1.getAddress()!=null && o2.getAddress()!=null){
            if(Integer.parseInt(o1.getAddress())>= Integer.parseInt(o2.getAddress())){
                return 1;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
