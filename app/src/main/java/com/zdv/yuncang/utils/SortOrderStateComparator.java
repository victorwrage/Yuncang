package com.zdv.yuncang.utils;


import com.zdv.yuncang.bean.SynergyOrderItemInfo;

import java.util.Comparator;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/27 9:37
 */

public class SortOrderStateComparator implements Comparator<SynergyOrderItemInfo> {

    @Override
    public int compare(SynergyOrderItemInfo o1, SynergyOrderItemInfo o2) {

        if ( Integer.parseInt(o1.getSolve()) <=  Integer.parseInt(o2.getSolve())) {
            return 1;
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
