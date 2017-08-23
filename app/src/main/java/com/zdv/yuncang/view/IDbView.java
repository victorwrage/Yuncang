package com.zdv.yuncang.view;


import com.zdv.yuncang.ZDVOrderDetailItem;
import com.zdv.yuncang.ZDVOrderItem;
import com.zdv.yuncang.bean.DbResultBean;

import java.util.List;

/**
 * Info: View interface
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:49
 */

public interface IDbView extends IView {

    void ResolveInsertDb(int type, DbResultBean result);
    void ResolveInsertMerDb(int type, DbResultBean result);
    void ResolveReadDbSynergyOrder(int type, DbResultBean result, List<ZDVOrderItem> items);
    void ResolveReadDbSynergyMer(int type, DbResultBean result, List<ZDVOrderDetailItem> items);
    void ResolveDeleteDb(int type, DbResultBean result);
    void ResolveDeleteMerDb(int type, DbResultBean result);

}
