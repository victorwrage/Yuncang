package com.zdv.yuncang.db;

import android.content.Context;

import com.socks.library.KLog;
import com.zdv.yuncang.ZDVOrderDetailItem;
import com.zdv.yuncang.ZDVOrderItem;
import com.zdv.yuncang.bean.SynergyOrderItemInfo;
import com.zdv.yuncang.bean.ZDVItemDetail;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/4/8 10:34
 */

public class CloudDBUtil {

    //TAG
    private static final String TAG = CloudDBUtil.class.getSimpleName();

    private DAOManager daoManager;

    //构造方法
    public CloudDBUtil(Context context) {
        daoManager = DAOManager.getInstance();
        daoManager.initManager(context);
    }

    /**
     * 对数据库中student表的插入操作
     *
     * @param item
     * @return
     */
    public boolean insertSynergyOrder(SynergyOrderItemInfo item) {
        boolean flag = false;
        flag = daoManager.getDaoSession().insert(item) != -1 ? true : false;
        return flag;
    }

    public boolean insertOrReplaceSynergyOrder(SynergyOrderItemInfo item) {
        ZDVOrderItem synergyMerItem = new ZDVOrderItem((long)item.getCode().hashCode(),
                item.getCode(),
                item.getUcode(),
                item.getCreatetime(),
                item.getTotalprice(),
                item.getPayprice(),
                item.getPaystate(),
                item.getStatus(),
                item.getCoster(),
                item.getCompany_id(),
                item.getSolve(),
                item.getShoptype(),
                item.getRemark());
        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insertOrReplace(synergyMerItem) != -1 ? true : false;

        }catch(Exception e){
            e.fillInStackTrace();
        }
        return flag;
    }

    public boolean insertOrReplaceSynergyMer(ZDVItemDetail item) {
        String id_str  = item.getPcode()+item.getOcode();
        int ids = id_str.hashCode();
        KLog.v(ids+"");
        ZDVOrderDetailItem synergyMerItem = new ZDVOrderDetailItem((long)ids,
                item.getOcode(),
                item.getPcode(),
                item.getBarcode(),
                item.getName(),
                item.getUnit(),
                item.getAct_num(),
                item.getNumber(),
                item.getPrice(),
                item.getMemprice(),
                item.getCost_type(),
                item.getAddress(),
                item.getItem_code(),
                item.getRemark(),
                item.getCwpsl(),
                item.getStatus());

        boolean flag = false;
        try {
            flag = daoManager.getDaoSession().insertOrReplace(synergyMerItem) != -1 ? true : false;

        }catch(Exception e){
            e.fillInStackTrace();
        }
        return flag;
    }



    public boolean deleteSynergyOrder(SynergyOrderItemInfo item) {
        ZDVOrderItem synergyOrderItem = new ZDVOrderItem((long)item.getCode().hashCode());
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(synergyOrderItem);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }

    public boolean deleteSynergyOrderDBBean(ZDVOrderItem item) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(item);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }


    public boolean deleteSynergyMerDBBean(ZDVOrderDetailItem item) {
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(item);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }

    public boolean deleteSynergyMer(ZDVItemDetail item) {
         String ids = item.getPcode()+item.getOcode();
        ZDVOrderDetailItem zdvOrderDetailItem = new ZDVOrderDetailItem((long)ids.hashCode());
        boolean flag = false;
        try {
            //删除指定ID
            daoManager.getDaoSession().delete(zdvOrderDetailItem);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //daoManager.getDaoSession().deleteAll(); //删除所有记录
        return flag;
    }


//    /**
//     * 查询单条
//     *
//     * @param key
//     * @return
//     */
//    public SynergyOrderItem listOneSynergyOrderItem(long key) {
//        return daoManager.getDaoSession().load(SynergyOrderItem.class, key);
//    }
//
//    public WDTMerItem listOneWDTMerItem(long key) {
//        return daoManager.getDaoSession().load(WDTMerItem.class, key);
//    }

    /**
     * 全部查询
     *
     * @return
     */
    public List<ZDVOrderItem> listAllSynergyOrder() {
        return daoManager.getDaoSession().loadAll(ZDVOrderItem.class);
    }
    public List<ZDVOrderDetailItem> listAllSynergyMer() {
        return daoManager.getDaoSession().loadAll(ZDVOrderDetailItem.class);
    }


    /**
     * 原生查询
     */
    public void queryNative() {
        //查询条件
        String where = "where name like ? and _id > ?";
        //使用sql进行查询
        List<SynergyOrderItemInfo> list = daoManager.getDaoSession().queryRaw(SynergyOrderItemInfo.class, where,
                new String[]{"%l%", "6"});
        KLog.i(TAG, list + "");
    }

    /**
     * QueryBuilder查询大于
     */
    public void queryBuilder() {
        //查询构建器
        QueryBuilder<SynergyOrderItemInfo> queryBuilder = daoManager.getDaoSession().queryBuilder(SynergyOrderItemInfo.class);
        //查询年龄大于19的北京
      //  List<SynergyOrderItem> list = queryBuilder.where(SynergyOrderItemDao.Properties.Age.ge(19)).where(SynergyOrderItemDao.Properties.Address.like("北京")).list();
    //    KLog.i(TAG, list + "");
    }
}
