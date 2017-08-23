package com.zdv.yuncang.view;

import com.zdv.yuncang.bean.SynergyOrderInfo;
import com.zdv.yuncang.bean.SynergyOrderPostResult;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IOrderView extends IView {

    void ResolveOrder(SynergyOrderInfo item);

    void ResolveOrderCommit(SynergyOrderPostResult item);


}
