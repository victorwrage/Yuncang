package com.zdv.yuncang.present;


import com.zdv.yuncang.bean.SynergyOrderPostInfo;
import com.zdv.yuncang.bean.SynergyRequest;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/7 9:46
 */

public interface IRequestPresent {
    void QueryOder(SynergyRequest synergyRequest);

    void Login(String username, String password, String company);

    void CommitOrder(SynergyOrderPostInfo data);


}
