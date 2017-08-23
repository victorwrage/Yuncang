package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Info:
 * Created by xiaoyl
 * 创建时间:2017/5/8 10:11
 */

public class DAOMaker {

    public static void main(String[] args) {
        //生成数据库的实体类,还有版本号
        Schema schema = new Schema(1, "com.zdv.yuncang");
        addOrderItem(schema);
        addSynergyPostItem(schema);
       // addWDTMerItem(schema);
        //指定dao
        schema.setDefaultJavaPackageDao("com.zdv.yuncang.dao");
        try {
            //指定路径
            new DaoGenerator().generateAll(schema, "D:\\work\\Yuncang\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addOrderItem(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("ZDVOrderItem");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("code");
        entity.addStringProperty("ucode");
        entity.addStringProperty("createtime");
        entity.addStringProperty("totalprice");
        entity.addStringProperty("payprice");
        entity.addStringProperty("paystate");
        entity.addStringProperty("status");
        entity.addStringProperty("coster");
        entity.addStringProperty("company_id");
        entity.addStringProperty("solve");
        entity.addStringProperty("shoptype");
        entity.addStringProperty("remark");
    }
    /**
     * 创建数据库的表
     *
     * @param schema
     */
    public static void addSynergyPostItem(Schema schema) {
        //创建数据库的表
        Entity entity = schema.addEntity("ZDVOrderDetailItem");
        //主键 是int类型
        entity.addIdProperty();
        //名称
        entity.addStringProperty("ocode");
        entity.addStringProperty("pcode");
        entity.addStringProperty("barcode");
        entity.addStringProperty("name");
        entity.addStringProperty("unit");
        entity.addStringProperty("act_num");
        entity.addStringProperty("number");
        entity.addStringProperty("price");
        entity.addStringProperty("memprice");
        entity.addStringProperty("cost_type");
        entity.addStringProperty("address");
        entity.addStringProperty("item_code");
        entity.addStringProperty("remark");
        entity.addStringProperty("cwpsl");
        entity.addStringProperty("status");
    }

}
