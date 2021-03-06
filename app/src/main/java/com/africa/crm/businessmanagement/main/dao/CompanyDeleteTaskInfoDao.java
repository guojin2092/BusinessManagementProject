package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTaskInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_DELETE_TASK_INFO".
*/
public class CompanyDeleteTaskInfoDao extends AbstractDao<CompanyDeleteTaskInfo, Long> {

    public static final String TABLENAME = "COMPANY_DELETE_TASK_INFO";

    /**
     * Properties of entity CompanyDeleteTaskInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property CustomerName = new Property(1, String.class, "customerName", false, "CUSTOMER_NAME");
        public final static Property CreateTime = new Property(2, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateTimeDate = new Property(3, Long.class, "createTimeDate", false, "CREATE_TIME_DATE");
        public final static Property Remark = new Property(4, String.class, "remark", false, "REMARK");
        public final static Property State = new Property(5, String.class, "state", false, "STATE");
        public final static Property CompanyName = new Property(6, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property UserNickName = new Property(7, String.class, "userNickName", false, "USER_NICK_NAME");
        public final static Property Id = new Property(8, String.class, "id", false, "ID");
        public final static Property Level = new Property(9, String.class, "level", false, "LEVEL");
        public final static Property RemindTime = new Property(10, String.class, "remindTime", false, "REMIND_TIME");
        public final static Property ContactName = new Property(11, String.class, "contactName", false, "CONTACT_NAME");
        public final static Property UserId = new Property(12, String.class, "userId", false, "USER_ID");
        public final static Property Name = new Property(13, String.class, "name", false, "NAME");
        public final static Property StateName = new Property(14, String.class, "stateName", false, "STATE_NAME");
        public final static Property LevelName = new Property(15, String.class, "levelName", false, "LEVEL_NAME");
        public final static Property CompanyId = new Property(16, String.class, "companyId", false, "COMPANY_ID");
        public final static Property HasRemind = new Property(17, String.class, "hasRemind", false, "HAS_REMIND");
        public final static Property Chosen = new Property(18, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(19, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyDeleteTaskInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyDeleteTaskInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_DELETE_TASK_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"CUSTOMER_NAME\" TEXT," + // 1: customerName
                "\"CREATE_TIME\" TEXT," + // 2: createTime
                "\"CREATE_TIME_DATE\" INTEGER," + // 3: createTimeDate
                "\"REMARK\" TEXT," + // 4: remark
                "\"STATE\" TEXT," + // 5: state
                "\"COMPANY_NAME\" TEXT," + // 6: companyName
                "\"USER_NICK_NAME\" TEXT," + // 7: userNickName
                "\"ID\" TEXT," + // 8: id
                "\"LEVEL\" TEXT," + // 9: level
                "\"REMIND_TIME\" TEXT," + // 10: remindTime
                "\"CONTACT_NAME\" TEXT," + // 11: contactName
                "\"USER_ID\" TEXT," + // 12: userId
                "\"NAME\" TEXT," + // 13: name
                "\"STATE_NAME\" TEXT," + // 14: stateName
                "\"LEVEL_NAME\" TEXT," + // 15: levelName
                "\"COMPANY_ID\" TEXT," + // 16: companyId
                "\"HAS_REMIND\" TEXT," + // 17: hasRemind
                "\"CHOSEN\" INTEGER NOT NULL ," + // 18: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 19: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_DELETE_TASK_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyDeleteTaskInfo entity) {
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
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(6, state);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(7, companyName);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(8, userNickName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(9, id);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(10, level);
        }
 
        String remindTime = entity.getRemindTime();
        if (remindTime != null) {
            stmt.bindString(11, remindTime);
        }
 
        String contactName = entity.getContactName();
        if (contactName != null) {
            stmt.bindString(12, contactName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(13, userId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(14, name);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(15, stateName);
        }
 
        String levelName = entity.getLevelName();
        if (levelName != null) {
            stmt.bindString(16, levelName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(17, companyId);
        }
 
        String hasRemind = entity.getHasRemind();
        if (hasRemind != null) {
            stmt.bindString(18, hasRemind);
        }
        stmt.bindLong(19, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyDeleteTaskInfo entity) {
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
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(6, state);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(7, companyName);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(8, userNickName);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(9, id);
        }
 
        String level = entity.getLevel();
        if (level != null) {
            stmt.bindString(10, level);
        }
 
        String remindTime = entity.getRemindTime();
        if (remindTime != null) {
            stmt.bindString(11, remindTime);
        }
 
        String contactName = entity.getContactName();
        if (contactName != null) {
            stmt.bindString(12, contactName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(13, userId);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(14, name);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(15, stateName);
        }
 
        String levelName = entity.getLevelName();
        if (levelName != null) {
            stmt.bindString(16, levelName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(17, companyId);
        }
 
        String hasRemind = entity.getHasRemind();
        if (hasRemind != null) {
            stmt.bindString(18, hasRemind);
        }
        stmt.bindLong(19, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyDeleteTaskInfo readEntity(Cursor cursor, int offset) {
        CompanyDeleteTaskInfo entity = new CompanyDeleteTaskInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // customerName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // createTimeDate
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // remark
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // state
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // companyName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // userNickName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // id
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // level
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // remindTime
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // contactName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // userId
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // name
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // stateName
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // levelName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // companyId
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // hasRemind
            cursor.getShort(offset + 18) != 0, // chosen
            cursor.getShort(offset + 19) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyDeleteTaskInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCustomerName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreateTimeDate(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setState(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCompanyName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setUserNickName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setLevel(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRemindTime(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setContactName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setUserId(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setStateName(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setLevelName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCompanyId(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setHasRemind(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setChosen(cursor.getShort(offset + 18) != 0);
        entity.setIsLocal(cursor.getShort(offset + 19) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyDeleteTaskInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyDeleteTaskInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyDeleteTaskInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
