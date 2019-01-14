package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_EXPENDITURE_INFO".
*/
public class CompanyExpenditureInfoDao extends AbstractDao<CompanyExpenditureInfo, Long> {

    public static final String TABLENAME = "COMPANY_EXPENDITURE_INFO";

    /**
     * Properties of entity CompanyExpenditureInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property StartDate = new Property(2, String.class, "startDate", false, "START_DATE");
        public final static Property CreateTime = new Property(3, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateTimeDate = new Property(4, Long.class, "createTimeDate", false, "CREATE_TIME_DATE");
        public final static Property ActualPrice = new Property(5, String.class, "actualPrice", false, "ACTUAL_PRICE");
        public final static Property Title = new Property(6, String.class, "title", false, "TITLE");
        public final static Property Remark = new Property(7, String.class, "remark", false, "REMARK");
        public final static Property UserId = new Property(8, String.class, "userId", false, "USER_ID");
        public final static Property EstimatePrice = new Property(9, String.class, "estimatePrice", false, "ESTIMATE_PRICE");
        public final static Property EndDate = new Property(10, String.class, "endDate", false, "END_DATE");
        public final static Property CompanyId = new Property(11, String.class, "companyId", false, "COMPANY_ID");
        public final static Property CompanyName = new Property(12, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property UserNickName = new Property(13, String.class, "userNickName", false, "USER_NICK_NAME");
    }


    public CompanyExpenditureInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyExpenditureInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_EXPENDITURE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"START_DATE\" TEXT," + // 2: startDate
                "\"CREATE_TIME\" TEXT," + // 3: createTime
                "\"CREATE_TIME_DATE\" INTEGER," + // 4: createTimeDate
                "\"ACTUAL_PRICE\" TEXT," + // 5: actualPrice
                "\"TITLE\" TEXT," + // 6: title
                "\"REMARK\" TEXT," + // 7: remark
                "\"USER_ID\" TEXT," + // 8: userId
                "\"ESTIMATE_PRICE\" TEXT," + // 9: estimatePrice
                "\"END_DATE\" TEXT," + // 10: endDate
                "\"COMPANY_ID\" TEXT," + // 11: companyId
                "\"COMPANY_NAME\" TEXT," + // 12: companyName
                "\"USER_NICK_NAME\" TEXT);"); // 13: userNickName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_EXPENDITURE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyExpenditureInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(3, startDate);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(5, createTimeDate);
        }
 
        String actualPrice = entity.getActualPrice();
        if (actualPrice != null) {
            stmt.bindString(6, actualPrice);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(9, userId);
        }
 
        String estimatePrice = entity.getEstimatePrice();
        if (estimatePrice != null) {
            stmt.bindString(10, estimatePrice);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(11, endDate);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(12, companyId);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(13, companyName);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(14, userNickName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyExpenditureInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String startDate = entity.getStartDate();
        if (startDate != null) {
            stmt.bindString(3, startDate);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(4, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(5, createTimeDate);
        }
 
        String actualPrice = entity.getActualPrice();
        if (actualPrice != null) {
            stmt.bindString(6, actualPrice);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(7, title);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(9, userId);
        }
 
        String estimatePrice = entity.getEstimatePrice();
        if (estimatePrice != null) {
            stmt.bindString(10, estimatePrice);
        }
 
        String endDate = entity.getEndDate();
        if (endDate != null) {
            stmt.bindString(11, endDate);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(12, companyId);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(13, companyName);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(14, userNickName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyExpenditureInfo readEntity(Cursor cursor, int offset) {
        CompanyExpenditureInfo entity = new CompanyExpenditureInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // startDate
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createTime
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // createTimeDate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // actualPrice
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // title
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // remark
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // userId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // estimatePrice
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // endDate
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // companyId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // companyName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13) // userNickName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyExpenditureInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStartDate(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreateTimeDate(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setActualPrice(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTitle(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRemark(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUserId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEstimatePrice(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setEndDate(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCompanyId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCompanyName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setUserNickName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyExpenditureInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyExpenditureInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyExpenditureInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}