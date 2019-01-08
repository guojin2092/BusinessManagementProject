package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteClientInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_DELETE_CLIENT_INFO".
*/
public class CompanyDeleteClientInfoDao extends AbstractDao<CompanyDeleteClientInfo, Long> {

    public static final String TABLENAME = "COMPANY_DELETE_CLIENT_INFO";

    /**
     * Properties of entity CompanyDeleteClientInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property CreateTime = new Property(2, String.class, "createTime", false, "CREATE_TIME");
        public final static Property IndustryName = new Property(3, String.class, "industryName", false, "INDUSTRY_NAME");
        public final static Property Remark = new Property(4, String.class, "remark", false, "REMARK");
        public final static Property WorkerNum = new Property(5, String.class, "workerNum", false, "WORKER_NUM");
        public final static Property Tel = new Property(6, String.class, "tel", false, "TEL");
        public final static Property CompanyName = new Property(7, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Address = new Property(8, String.class, "address", false, "ADDRESS");
        public final static Property YearIncome = new Property(9, String.class, "yearIncome", false, "YEAR_INCOME");
        public final static Property UserId = new Property(10, String.class, "userId", false, "USER_ID");
        public final static Property UserNickName = new Property(11, String.class, "userNickName", false, "USER_NICK_NAME");
        public final static Property Name = new Property(12, String.class, "name", false, "NAME");
        public final static Property CompanyId = new Property(13, String.class, "companyId", false, "COMPANY_ID");
        public final static Property Head = new Property(14, String.class, "head", false, "HEAD");
        public final static Property Industry = new Property(15, String.class, "industry", false, "INDUSTRY");
        public final static Property Chosen = new Property(16, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(17, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyDeleteClientInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyDeleteClientInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_DELETE_CLIENT_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"CREATE_TIME\" TEXT," + // 2: createTime
                "\"INDUSTRY_NAME\" TEXT," + // 3: industryName
                "\"REMARK\" TEXT," + // 4: remark
                "\"WORKER_NUM\" TEXT," + // 5: workerNum
                "\"TEL\" TEXT," + // 6: tel
                "\"COMPANY_NAME\" TEXT," + // 7: companyName
                "\"ADDRESS\" TEXT," + // 8: address
                "\"YEAR_INCOME\" TEXT," + // 9: yearIncome
                "\"USER_ID\" TEXT," + // 10: userId
                "\"USER_NICK_NAME\" TEXT," + // 11: userNickName
                "\"NAME\" TEXT," + // 12: name
                "\"COMPANY_ID\" TEXT," + // 13: companyId
                "\"HEAD\" TEXT," + // 14: head
                "\"INDUSTRY\" TEXT," + // 15: industry
                "\"CHOSEN\" INTEGER NOT NULL ," + // 16: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 17: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_DELETE_CLIENT_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyDeleteClientInfo entity) {
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
 
        String industryName = entity.getIndustryName();
        if (industryName != null) {
            stmt.bindString(4, industryName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String workerNum = entity.getWorkerNum();
        if (workerNum != null) {
            stmt.bindString(6, workerNum);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(7, tel);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(8, companyName);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(9, address);
        }
 
        String yearIncome = entity.getYearIncome();
        if (yearIncome != null) {
            stmt.bindString(10, yearIncome);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(11, userId);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(12, userNickName);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(13, name);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(14, companyId);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(15, head);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(16, industry);
        }
        stmt.bindLong(17, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(18, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyDeleteClientInfo entity) {
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
 
        String industryName = entity.getIndustryName();
        if (industryName != null) {
            stmt.bindString(4, industryName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(5, remark);
        }
 
        String workerNum = entity.getWorkerNum();
        if (workerNum != null) {
            stmt.bindString(6, workerNum);
        }
 
        String tel = entity.getTel();
        if (tel != null) {
            stmt.bindString(7, tel);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(8, companyName);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(9, address);
        }
 
        String yearIncome = entity.getYearIncome();
        if (yearIncome != null) {
            stmt.bindString(10, yearIncome);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(11, userId);
        }
 
        String userNickName = entity.getUserNickName();
        if (userNickName != null) {
            stmt.bindString(12, userNickName);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(13, name);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(14, companyId);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(15, head);
        }
 
        String industry = entity.getIndustry();
        if (industry != null) {
            stmt.bindString(16, industry);
        }
        stmt.bindLong(17, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(18, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyDeleteClientInfo readEntity(Cursor cursor, int offset) {
        CompanyDeleteClientInfo entity = new CompanyDeleteClientInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // industryName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // remark
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // workerNum
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // tel
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // companyName
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // address
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // yearIncome
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // userId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // userNickName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // name
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // companyId
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // head
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // industry
            cursor.getShort(offset + 16) != 0, // chosen
            cursor.getShort(offset + 17) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyDeleteClientInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIndustryName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRemark(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setWorkerNum(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTel(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCompanyName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setAddress(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setYearIncome(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUserId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserNickName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setCompanyId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setHead(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setIndustry(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setChosen(cursor.getShort(offset + 16) != 0);
        entity.setIsLocal(cursor.getShort(offset + 17) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyDeleteClientInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyDeleteClientInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyDeleteClientInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
