package com.zdv.yuncang.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.zdv.yuncang.ZDVOrderItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ZDVORDER_ITEM".
*/
public class ZDVOrderItemDao extends AbstractDao<ZDVOrderItem, Long> {

    public static final String TABLENAME = "ZDVORDER_ITEM";

    /**
     * Properties of entity ZDVOrderItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Code = new Property(1, String.class, "code", false, "CODE");
        public final static Property Ucode = new Property(2, String.class, "ucode", false, "UCODE");
        public final static Property Createtime = new Property(3, String.class, "createtime", false, "CREATETIME");
        public final static Property Totalprice = new Property(4, String.class, "totalprice", false, "TOTALPRICE");
        public final static Property Payprice = new Property(5, String.class, "payprice", false, "PAYPRICE");
        public final static Property Paystate = new Property(6, String.class, "paystate", false, "PAYSTATE");
        public final static Property Status = new Property(7, String.class, "status", false, "STATUS");
        public final static Property Coster = new Property(8, String.class, "coster", false, "COSTER");
        public final static Property Company_id = new Property(9, String.class, "company_id", false, "COMPANY_ID");
        public final static Property Solve = new Property(10, String.class, "solve", false, "SOLVE");
        public final static Property Shoptype = new Property(11, String.class, "shoptype", false, "SHOPTYPE");
        public final static Property Remark = new Property(12, String.class, "remark", false, "REMARK");
    };


    public ZDVOrderItemDao(DaoConfig config) {
        super(config);
    }
    
    public ZDVOrderItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ZDVORDER_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CODE\" TEXT," + // 1: code
                "\"UCODE\" TEXT," + // 2: ucode
                "\"CREATETIME\" TEXT," + // 3: createtime
                "\"TOTALPRICE\" TEXT," + // 4: totalprice
                "\"PAYPRICE\" TEXT," + // 5: payprice
                "\"PAYSTATE\" TEXT," + // 6: paystate
                "\"STATUS\" TEXT," + // 7: status
                "\"COSTER\" TEXT," + // 8: coster
                "\"COMPANY_ID\" TEXT," + // 9: company_id
                "\"SOLVE\" TEXT," + // 10: solve
                "\"SHOPTYPE\" TEXT," + // 11: shoptype
                "\"REMARK\" TEXT);"); // 12: remark
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ZDVORDER_ITEM\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ZDVOrderItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(2, code);
        }
 
        String ucode = entity.getUcode();
        if (ucode != null) {
            stmt.bindString(3, ucode);
        }
 
        String createtime = entity.getCreatetime();
        if (createtime != null) {
            stmt.bindString(4, createtime);
        }
 
        String totalprice = entity.getTotalprice();
        if (totalprice != null) {
            stmt.bindString(5, totalprice);
        }
 
        String payprice = entity.getPayprice();
        if (payprice != null) {
            stmt.bindString(6, payprice);
        }
 
        String paystate = entity.getPaystate();
        if (paystate != null) {
            stmt.bindString(7, paystate);
        }
 
        String status = entity.getStatus();
        if (status != null) {
            stmt.bindString(8, status);
        }
 
        String coster = entity.getCoster();
        if (coster != null) {
            stmt.bindString(9, coster);
        }
 
        String company_id = entity.getCompany_id();
        if (company_id != null) {
            stmt.bindString(10, company_id);
        }
 
        String solve = entity.getSolve();
        if (solve != null) {
            stmt.bindString(11, solve);
        }
 
        String shoptype = entity.getShoptype();
        if (shoptype != null) {
            stmt.bindString(12, shoptype);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(13, remark);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ZDVOrderItem readEntity(Cursor cursor, int offset) {
        ZDVOrderItem entity = new ZDVOrderItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // code
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // ucode
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createtime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // totalprice
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // payprice
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // paystate
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // status
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // coster
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // company_id
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // solve
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // shoptype
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12) // remark
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ZDVOrderItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCode(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUcode(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreatetime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTotalprice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPayprice(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPaystate(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setStatus(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCoster(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCompany_id(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setSolve(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setShoptype(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setRemark(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ZDVOrderItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ZDVOrderItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}