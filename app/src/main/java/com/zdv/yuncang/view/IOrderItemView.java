package com.zdv.yuncang.view;


import com.zdv.yuncang.bean.SynergyOrderItemInfo;

import java.util.List;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IOrderItemView extends IView{

    void ResolveOrderItem(List<SynergyOrderItemInfo> items);

}
