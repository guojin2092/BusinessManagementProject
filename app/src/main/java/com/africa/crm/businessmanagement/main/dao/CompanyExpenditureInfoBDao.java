package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_EXPENDITURE_INFO_B".
*/
public class CompanyExpenditureInfoBDao extends AbstractDao<CompanyExpenditureInfoB, Long> {

    public static final String TABLENAME = "COMPANY_EXPENDITURE_INFO_B";

    /**
     * Properties of entity CompanyExpenditureInfoB.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property CreateTime = new Property(2, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateTimeDate = new Property(3, Long.class, "createTimeDate", false, "CREATE_TIME_DATE");
        public final static Property EstimateId = new Property(4, String.class, "estimateId", false, "ESTIMATE_ID");
        public final static Property Price = new Property(5, String.class, "price", false, "PRICE");
        public final static Property Remark = new Property(6, String.class, "remark", false, "REMARK");
        public final static Property UserId = new Property(7, String.class, "userId", false, "USER_ID");
        public final static Property PayDate = new Property(8, String.class, "payDate", false, "PAY_DATE");
        public final static Property CompanyId = new Property(9, String.class, "companyId", false, "COMPANY_ID");
        public final static Property CompanyName = new Property(10, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property EstimateTitle = new Property(11, String.class, "estimateTitle", false, "ESTIMATE_TITLE");
        public final static Property UserNickName = new Property(12, String.class, "userNickName", false, "USER_NICK_NAME");
        public final static Property IsLocal = new Property(13, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyExpenditureInfoBDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyExpenditureInfoBDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_EXPENDITURE_INFO_B\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"CREATE_TIME\" TEXT," + // 2: createTime
                "\"CREATE_TIME_DATE\" INTEGER," + // 3: createTimeDate
                "\"ESTIMATE_ID\" TEXT," + // 4: estimateId
                "\"PRICE\" TEXT," + // 5: price
                "\"REMARK\" TEXT," + // 6: remark
                "\"USER_ID\" TEXT," + // 7: userId
                "\"PAY_DATE\" TEXT," + // 8: payDate
                "\"COMPANY_ID\" TEXT," + // 9: companyId
                "\"COMPANY_NAME\" TEXT," + // 10: companyName
                "\"ESTIMATE_TITLE\" TEXT," + // 11: estimateTitle
                "\"USER_NICK_NAME\" TEXT," + // 12: userNickName
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 13: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_EXPENDITURE_INFO_B\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyExpenditureInfoB entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(3, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(4, createTimeDate);
        }
 
        String estimateId = entity.getEstimateId();
        if (estimateId != null) {
            stmt.bindString(5, estimateId);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(6, price);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(7, remark);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(8, userId);
        }
 
        String payDate = entity.getPayDate();
        if (payDate != null) {
            stmt.bindString(9, payDate);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(10, companyId);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(11, companyName);
        }
 
        String estimateTitle = entity.getEstimateTitle();
        if (estimateTitle != null) {
            stmt.bindString(12, estimateTitle);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(13, userNickName);
        }
        stmt.bindLong(14, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyExpenditureInfoB entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(3, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(4, createTimeDate);
        }
 
        String estimateId = entity.getEstimateId();
        if (estimateId != null) {
            stmt.bindString(5, estimateId);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(6, price);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(7, remark);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(8, userId);
        }
 
        String payDate = entity.getPayDate();
        if (payDate != null) {
            stmt.bindString(9, payDate);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(10, companyId);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(11, companyName);
        }
 
        String estimateTitle = entity.getEstimateTitle();
        if (estimateTitle != null) {
            stmt.bindString(12, estimateTitle);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(13, userNickName);
        }
        stmt.bindLong(14, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyExpenditureInfoB readEntity(Cursor cursor, int offset) {
        CompanyExpenditureInfoB entity = new CompanyExpenditureInfoB( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // createTimeDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // estimateId
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // price
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // remark
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // userId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // payDate
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // companyId
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // companyName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // estimateTitle
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // userNickName
            cursor.getShort(offset + 13) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyExpenditureInfoB entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTimeDate(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setEstimateId(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPrice(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRemark(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUserId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPayDate(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCompanyId(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCompanyName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setEstimateTitle(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUserNickName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setIsLocal(cursor.getShort(offset + 13) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyExpenditureInfoB entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyExpenditureInfoB entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyExpenditureInfoB entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
