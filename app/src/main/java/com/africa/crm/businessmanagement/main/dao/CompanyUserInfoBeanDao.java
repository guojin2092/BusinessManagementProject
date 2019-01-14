package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyUserInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_USER_INFO_BEAN".
*/
public class CompanyUserInfoBeanDao extends AbstractDao<CompanyUserInfoBean, Long> {

    public static final String TABLENAME = "COMPANY_USER_INFO_BEAN";

    /**
     * Properties of entity CompanyUserInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property Head = new Property(2, String.class, "head", false, "HEAD");
        public final static Property CompanyId = new Property(3, String.class, "companyId", false, "COMPANY_ID");
        public final static Property CreateTime = new Property(4, String.class, "createTime", false, "CREATE_TIME");
        public final static Property CreateTimeDate = new Property(5, Long.class, "createTimeDate", false, "CREATE_TIME_DATE");
        public final static Property CompanyName = new Property(6, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property RoleName = new Property(8, String.class, "roleName", false, "ROLE_NAME");
        public final static Property State = new Property(9, String.class, "state", false, "STATE");
        public final static Property StateName = new Property(10, String.class, "stateName", false, "STATE_NAME");
        public final static Property UserName = new Property(11, String.class, "userName", false, "USER_NAME");
        public final static Property Type = new Property(12, String.class, "type", false, "TYPE");
        public final static Property TypeName = new Property(13, String.class, "typeName", false, "TYPE_NAME");
        public final static Property Password = new Property(14, String.class, "password", false, "PASSWORD");
        public final static Property Address = new Property(15, String.class, "address", false, "ADDRESS");
        public final static Property Phone = new Property(16, String.class, "phone", false, "PHONE");
        public final static Property Email = new Property(17, String.class, "email", false, "EMAIL");
        public final static Property RoleId = new Property(18, String.class, "roleId", false, "ROLE_ID");
        public final static Property RoleTypeName = new Property(19, String.class, "roleTypeName", false, "ROLE_TYPE_NAME");
        public final static Property RoleCode = new Property(20, String.class, "roleCode", false, "ROLE_CODE");
        public final static Property Chosen = new Property(21, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(22, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyUserInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyUserInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_USER_INFO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"HEAD\" TEXT," + // 2: head
                "\"COMPANY_ID\" TEXT," + // 3: companyId
                "\"CREATE_TIME\" TEXT," + // 4: createTime
                "\"CREATE_TIME_DATE\" INTEGER," + // 5: createTimeDate
                "\"COMPANY_NAME\" TEXT," + // 6: companyName
                "\"NAME\" TEXT," + // 7: name
                "\"ROLE_NAME\" TEXT," + // 8: roleName
                "\"STATE\" TEXT," + // 9: state
                "\"STATE_NAME\" TEXT," + // 10: stateName
                "\"USER_NAME\" TEXT," + // 11: userName
                "\"TYPE\" TEXT," + // 12: type
                "\"TYPE_NAME\" TEXT," + // 13: typeName
                "\"PASSWORD\" TEXT," + // 14: password
                "\"ADDRESS\" TEXT," + // 15: address
                "\"PHONE\" TEXT," + // 16: phone
                "\"EMAIL\" TEXT," + // 17: email
                "\"ROLE_ID\" TEXT," + // 18: roleId
                "\"ROLE_TYPE_NAME\" TEXT," + // 19: roleTypeName
                "\"ROLE_CODE\" TEXT," + // 20: roleCode
                "\"CHOSEN\" INTEGER NOT NULL ," + // 21: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 22: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_USER_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyUserInfoBean entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(3, head);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(4, companyId);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(5, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(6, createTimeDate);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(7, companyName);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(9, roleName);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(10, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(11, stateName);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(12, userName);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(13, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(14, typeName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(15, password);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(16, address);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(18, email);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(19, roleId);
        }
 
        String roleTypeName = entity.getRoleTypeName();
        if (roleTypeName != null) {
            stmt.bindString(20, roleTypeName);
        }
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(21, roleCode);
        }
        stmt.bindLong(22, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(23, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyUserInfoBean entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(3, head);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(4, companyId);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(5, createTime);
        }
 
        Long createTimeDate = entity.getCreateTimeDate();
        if (createTimeDate != null) {
            stmt.bindLong(6, createTimeDate);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(7, companyName);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(9, roleName);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(10, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(11, stateName);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(12, userName);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(13, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(14, typeName);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(15, password);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(16, address);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(18, email);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(19, roleId);
        }
 
        String roleTypeName = entity.getRoleTypeName();
        if (roleTypeName != null) {
            stmt.bindString(20, roleTypeName);
        }
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(21, roleCode);
        }
        stmt.bindLong(22, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(23, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyUserInfoBean readEntity(Cursor cursor, int offset) {
        CompanyUserInfoBean entity = new CompanyUserInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // head
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // companyId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // createTime
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // createTimeDate
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // companyName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // roleName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // state
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // stateName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // userName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // type
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // typeName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // password
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // address
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // phone
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // email
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // roleId
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // roleTypeName
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // roleCode
            cursor.getShort(offset + 21) != 0, // chosen
            cursor.getShort(offset + 22) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyUserInfoBean entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHead(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCompanyId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCreateTime(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCreateTimeDate(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setCompanyName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setRoleName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setState(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setStateName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setUserName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setType(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setTypeName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setPassword(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setAddress(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setPhone(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setEmail(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setRoleId(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setRoleTypeName(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setRoleCode(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setChosen(cursor.getShort(offset + 21) != 0);
        entity.setIsLocal(cursor.getShort(offset + 22) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyUserInfoBean entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyUserInfoBean entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyUserInfoBean entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}