package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_SERVICE_RECORD_INFO".
*/
public class CompanyServiceRecordInfoDao extends AbstractDao<CompanyServiceRecordInfo, Long> {

    public static final String TABLENAME = "COMPANY_SERVICE_RECORD_INFO";

    /**
     * Properties of entity CompanyServiceRecordInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property CustomerName = new Property(1, String.class, "customerName", false, "CUSTOMER_NAME");
        public final static Property CreateTime = new Property(2, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateTimeDate = new Property(3, Long.class, "createTimeDate", false, "CREATE_TIME_DATE");
        public final static Property Phone = new Property(4, String.class, "phone", false, "PHONE");
        public final static Property Remark = new Property(5, String.class, "remark", false, "REMARK");
        public final static Property Reason = new Property(6, String.class, "reason", false, "REASON");
        public final static Property Track = new Property(7, String.class, "track", false, "TRACK");
        public final static Property State = new Property(8, String.class, "state", false, "STATE");
        public final static Property Type = new Property(9, String.class, "type", false, "TYPE");
        public final static Property CompanyName = new Property(10, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Solution = new Property(11, String.class, "solution", false, "SOLUTION");
        public final static Property UserNickName = new Property(12, String.class, "userNickName", false, "USER_NICK_NAME");
        public final static Property ProductId = new Property(13, String.class, "productId", false, "PRODUCT_ID");
        public final static Property TypeName = new Property(14, String.class, "typeName", false, "TYPE_NAME");
        public final static Property Id = new Property(15, String.class, "id", false, "ID");
        public final static Property Level = new Property(16, String.class, "level", false, "LEVEL");
        public final static Property Email = new Property(17, String.class, "email", false, "EMAIL");
        public final static Property EditAble = new Property(18, String.class, "editAble", false, "EDIT_ABLE");
        public final static Property UserId = new Property(19, String.class, "userId", false, "USER_ID");
        public final static Property Name = new Property(20, String.class, "name", false, "NAME");
        public final static Property StateName = new Property(21, String.class, "stateName", false, "STATE_NAME");
        public final static Property LevelName = new Property(22, String.class, "levelName", false, "LEVEL_NAME");
        public final static Property CompanyId = new Property(23, String.class, "companyId", false, "COMPANY_ID");
        public final static Property ProductName = new Property(24, String.class, "productName", false, "PRODUCT_NAME");
        public final static Property Chosen = new Property(25, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(26, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyServiceRecordInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyServiceRecordInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_SERVICE_RECORD_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"CUSTOMER_NAME\" TEXT," + // 1: customerName
                "\"CREATE_TIME\" TEXT," + // 2: createTime
                "\"CREATE_TIME_DATE\" INTEGER," + // 3: createTimeDate
                "\"PHONE\" TEXT," + // 4: phone
                "\"REMARK\" TEXT," + // 5: remark
                "\"REASON\" TEXT," + // 6: reason
                "\"TRACK\" TEXT," + // 7: track
                "\"STATE\" TEXT," + // 8: state
                "\"TYPE\" TEXT," + // 9: type
                "\"COMPANY_NAME\" TEXT," + // 10: companyName
                "\"SOLUTION\" TEXT," + // 11: solution
                "\"USER_NICK_NAME\" TEXT," + // 12: userNickName
                "\"PRODUCT_ID\" TEXT," + // 13: productId
                "\"TYPE_NAME\" TEXT," + // 14: typeName
                "\"ID\" TEXT," + // 15: id
                "\"LEVEL\" TEXT," + // 16: level
                "\"EMAIL\" TEXT," + // 17: email
                "\"EDIT_ABLE\" TEXT," + // 18: editAble
                "\"USER_ID\" TEXT," + // 19: userId
                "\"NAME\" TEXT," + // 20: name
                "\"STATE_NAME\" TEXT," + // 21: stateName
                "\"LEVEL_NAME\" TEXT," + // 22: levelName
                "\"COMPANY_ID\" TEXT," + // 23: companyId
                "\"PRODUCT_NAME\" TEXT," + // 24: productName
                "\"CHOSEN\" INTEGER NOT NULL ," + // 25: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 26: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_SERVICE_RECORD_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyServiceRecordInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String customerName = entity.getCustomerName();
        if (customerName != null) {
            stmt.bindString(2, customerName);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(3, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(4, createTimeDate);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(6, remark);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(7, reason);
        }
 
        String track = entity.getTrack();
        if (track != null) {
            stmt.bindString(8, track);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(9, state);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(10, type);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(11, companyName);
        }
 
        String solution = entity.getSolution();
        if (solution != null) {
            stmt.bindString(12, solution);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(13, userNickName);
        }
 
        String productId = entity.getProductId();
        if (productId != null) {
            stmt.bindString(14, productId);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(15, typeName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(16, id);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(17, level);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(18, email);
        }
 
        String editAble = entity.getEditAble();
        if (editAble != null) {
            stmt.bindString(19, editAble);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(20, userId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(21, name);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(22, stateName);
        }
 
        String levelName = entity.getLevelName();
        if (levelName != null) {
            stmt.bindString(23, levelName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(24, companyId);
        }
 
        String productName = entity.getProductName();
        if (productName != null) {
            stmt.bindString(25, productName);
        }
        stmt.bindLong(26, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(27, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyServiceRecordInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String customerName = entity.getCustomerName();
        if (customerName != null) {
            stmt.bindString(2, customerName);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(3, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(4, createTimeDate);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(5, phone);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(6, remark);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(7, reason);
        }
 
        String track = entity.getTrack();
        if (track != null) {
            stmt.bindString(8, track);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(9, state);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(10, type);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(11, companyName);
        }
 
        String solution = entity.getSolution();
        if (solution != null) {
            stmt.bindString(12, solution);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(13, userNickName);
        }
 
        String productId = entity.getProductId();
        if (productId != null) {
            stmt.bindString(14, productId);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(15, typeName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(16, id);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(17, level);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(18, email);
        }
 
        String editAble = entity.getEditAble();
        if (editAble != null) {
            stmt.bindString(19, editAble);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(20, userId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(21, name);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(22, stateName);
        }
 
        String levelName = entity.getLevelName();
        if (levelName != null) {
            stmt.bindString(23, levelName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(24, companyId);
        }
 
        String productName = entity.getProductName();
        if (productName != null) {
            stmt.bindString(25, productName);
        }
        stmt.bindLong(26, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(27, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyServiceRecordInfo readEntity(Cursor cursor, int offset) {
        CompanyServiceRecordInfo entity = new CompanyServiceRecordInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // customerName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // createTimeDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // phone
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // remark
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // reason
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // track
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // state
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // type
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // companyName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // solution
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // userNickName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // productId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // typeName
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // id
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // level
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // email
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // editAble
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // userId
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // name
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // stateName
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // levelName
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // companyId
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // productName
            cursor.getShort(offset + 25) != 0, // chosen
            cursor.getShort(offset + 26) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyServiceRecordInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCustomerName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTimeDate(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setPhone(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRemark(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setReason(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTrack(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setState(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setType(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCompanyName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSolution(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUserNickName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setProductId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setTypeName(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setId(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setLevel(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setEmail(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setEditAble(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setUserId(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setName(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setStateName(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setLevelName(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setCompanyId(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setProductName(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setChosen(cursor.getShort(offset + 25) != 0);
        entity.setIsLocal(cursor.getShort(offset + 26) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyServiceRecordInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyServiceRecordInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyServiceRecordInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
