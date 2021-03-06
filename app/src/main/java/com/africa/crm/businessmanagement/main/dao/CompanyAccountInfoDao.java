package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_ACCOUNT_INFO".
*/
public class CompanyAccountInfoDao extends AbstractDao<CompanyAccountInfo, Long> {

    public static final String TABLENAME = "COMPANY_ACCOUNT_INFO";

    /**
     * Properties of entity CompanyAccountInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property CreateTime = new Property(2, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property UserName = new Property(4, String.class, "userName", false, "USER_NAME");
        public final static Property RoleId = new Property(5, String.class, "roleId", false, "ROLE_ID");
        public final static Property RoleName = new Property(6, String.class, "roleName", false, "ROLE_NAME");
        public final static Property CompanyId = new Property(7, String.class, "companyId", false, "COMPANY_ID");
        public final static Property Head = new Property(8, String.class, "head", false, "HEAD");
        public final static Property CompanyName = new Property(9, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property Type = new Property(10, String.class, "type", false, "TYPE");
        public final static Property TypeName = new Property(11, String.class, "typeName", false, "TYPE_NAME");
        public final static Property State = new Property(12, String.class, "state", false, "STATE");
        public final static Property StateName = new Property(13, String.class, "stateName", false, "STATE_NAME");
        public final static Property Address = new Property(14, String.class, "address", false, "ADDRESS");
        public final static Property RoleTypeName = new Property(15, String.class, "roleTypeName", false, "ROLE_TYPE_NAME");
        public final static Property Phone = new Property(16, String.class, "phone", false, "PHONE");
        public final static Property RoleCode = new Property(17, String.class, "roleCode", false, "ROLE_CODE");
        public final static Property Email = new Property(18, String.class, "email", false, "EMAIL");
        public final static Property Password = new Property(19, String.class, "password", false, "PASSWORD");
        public final static Property Chosen = new Property(20, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(21, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyAccountInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyAccountInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_ACCOUNT_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"CREATE_TIME\" TEXT," + // 2: createTime
                "\"NAME\" TEXT," + // 3: name
                "\"USER_NAME\" TEXT," + // 4: userName
                "\"ROLE_ID\" TEXT," + // 5: roleId
                "\"ROLE_NAME\" TEXT," + // 6: roleName
                "\"COMPANY_ID\" TEXT," + // 7: companyId
                "\"HEAD\" TEXT," + // 8: head
                "\"COMPANY_NAME\" TEXT," + // 9: companyName
                "\"TYPE\" TEXT," + // 10: type
                "\"TYPE_NAME\" TEXT," + // 11: typeName
                "\"STATE\" TEXT," + // 12: state
                "\"STATE_NAME\" TEXT," + // 13: stateName
                "\"ADDRESS\" TEXT," + // 14: address
                "\"ROLE_TYPE_NAME\" TEXT," + // 15: roleTypeName
                "\"PHONE\" TEXT," + // 16: phone
                "\"ROLE_CODE\" TEXT," + // 17: roleCode
                "\"EMAIL\" TEXT," + // 18: email
                "\"PASSWORD\" TEXT," + // 19: password
                "\"CHOSEN\" INTEGER NOT NULL ," + // 20: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 21: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_ACCOUNT_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyAccountInfo entity) {
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
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(5, userName);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(6, roleId);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(7, roleName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(8, companyId);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(9, head);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(10, companyName);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(11, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(12, typeName);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(13, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(14, stateName);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(15, address);
        }
 
        String roleTypeName = entity.getRoleTypeName();
        if (roleTypeName != null) {
            stmt.bindString(16, roleTypeName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(18, roleCode);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(19, email);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
        stmt.bindLong(21, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(22, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyAccountInfo entity) {
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
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(5, userName);
        }
 
        String roleId = entity.getRoleId();
        if (roleId != null) {
            stmt.bindString(6, roleId);
        }
 
        String roleName = entity.getRoleName();
        if (roleName != null) {
            stmt.bindString(7, roleName);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(8, companyId);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(9, head);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(10, companyName);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(11, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(12, typeName);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(13, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(14, stateName);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(15, address);
        }
 
        String roleTypeName = entity.getRoleTypeName();
        if (roleTypeName != null) {
            stmt.bindString(16, roleTypeName);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(17, phone);
        }
 
        String roleCode = entity.getRoleCode();
        if (roleCode != null) {
            stmt.bindString(18, roleCode);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(19, email);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(20, password);
        }
        stmt.bindLong(21, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(22, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyAccountInfo readEntity(Cursor cursor, int offset) {
        CompanyAccountInfo entity = new CompanyAccountInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // createTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // userName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // roleId
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // roleName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // companyId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // head
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // companyName
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // type
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // typeName
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // state
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // stateName
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // address
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // roleTypeName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // phone
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // roleCode
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // email
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // password
            cursor.getShort(offset + 20) != 0, // chosen
            cursor.getShort(offset + 21) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyAccountInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreateTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUserName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRoleId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setRoleName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setCompanyId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setHead(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCompanyName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setType(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setTypeName(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setState(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setStateName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setAddress(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setRoleTypeName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setPhone(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setRoleCode(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setEmail(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setPassword(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setChosen(cursor.getShort(offset + 20) != 0);
        entity.setIsLocal(cursor.getShort(offset + 21) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyAccountInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyAccountInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyAccountInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
