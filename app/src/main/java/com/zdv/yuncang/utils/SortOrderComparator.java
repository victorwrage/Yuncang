package com.zdv.yuncang.utils;


import com.zdv.yuncang.bean.SynergyOrderItemInfo;

import java.util.Comparator;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/27 9:37
 */

public class SortOrderComparator implements Comparator<SynergyOrderItemInfo> {

    @Override
    public int compare(SynergyOrderItemInfo o1, SynergyOrderItemInfo o2) {

        if (o1.getCreatetime() != null && o2.getCreatetime() != null) {
            if (Utils.getInstance().ValidateFormat(o1.getCreatetime()) >= Utils.getInstance().ValidateFormat(o2.getCreatetime())) {
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
