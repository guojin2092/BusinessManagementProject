package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_DELETE_SUPPLIER_INFO".
*/
public class CompanyDeleteSupplierInfoDao extends AbstractDao<CompanyDeleteSupplierInfo, Long> {

    public static final String TABLENAME = "COMPANY_DELETE_SUPPLIER_INFO";

    /**
     * Properties of entity CompanyDeleteSupplierInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property Area = new Property(2, String.class, "area", false, "AREA");
        public final static Property ZipCode = new Property(3, String.class, "zipCode", false, "ZIP_CODE");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property CompanyName = new Property(5, String.class, "companyName", false, "COMPANY_NAME");
        public final static Property TypeName = new Property(6, String.class, "typeName", false, "TYPE_NAME");
        public final static Property Remark = new Property(7, String.class, "remark", false, "REMARK");
        public final static Property Type = new Property(8, String.class, "type", false, "TYPE");
        public final static Property Head = new Property(9, String.class, "head", false, "HEAD");
        public final static Property CompanyId = new Property(10, String.class, "companyId", false, "COMPANY_ID");
        public final static Property CreateTime = new Property(11, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Phone = new Property(12, String.class, "phone", false, "PHONE");
        public final static Property Name = new Property(13, String.class, "name", false, "NAME");
        public final static Property Email = new Property(14, String.class, "email", false, "EMAIL");
        public final static Property Chosen = new Property(15, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsLocal = new Property(16, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyDeleteSupplierInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyDeleteSupplierInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_DELETE_SUPPLIER_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"ID\" TEXT," + // 1: id
                "\"AREA\" TEXT," + // 2: area
                "\"ZIP_CODE\" TEXT," + // 3: zipCode
                "\"ADDRESS\" TEXT," + // 4: address
                "\"COMPANY_NAME\" TEXT," + // 5: companyName
                "\"TYPE_NAME\" TEXT," + // 6: typeName
                "\"REMARK\" TEXT," + // 7: remark
                "\"TYPE\" TEXT," + // 8: type
                "\"HEAD\" TEXT," + // 9: head
                "\"COMPANY_ID\" TEXT," + // 10: companyId
                "\"CREATE_TIME\" TEXT," + // 11: createTime
                "\"PHONE\" TEXT," + // 12: phone
                "\"NAME\" TEXT," + // 13: name
                "\"EMAIL\" TEXT," + // 14: email
                "\"CHOSEN\" INTEGER NOT NULL ," + // 15: chosen
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 16: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_DELETE_SUPPLIER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyDeleteSupplierInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
 
        String zipCode = entity.getZipCode();
        if (zipCode != null) {
            stmt.bindString(4, zipCode);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(6, companyName);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(7, typeName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(10, head);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(11, companyId);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(12, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(13, phone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(14, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
        stmt.bindLong(16, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(17, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyDeleteSupplierInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(3, area);
        }
 
        String zipCode = entity.getZipCode();
        if (zipCode != null) {
            stmt.bindString(4, zipCode);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String companyName = entity.getCompanyName();
        if (companyName != null) {
            stmt.bindString(6, companyName);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(7, typeName);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(8, remark);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(10, head);
        }
 
        String companyId = entity.getCompanyId();
        if (companyId != null) {
            stmt.bindString(11, companyId);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(12, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(13, phone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(14, name);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(15, email);
        }
        stmt.bindLong(16, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(17, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyDeleteSupplierInfo readEntity(Cursor cursor, int offset) {
        CompanyDeleteSupplierInfo entity = new CompanyDeleteSupplierInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // area
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // zipCode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // companyName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // typeName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // remark
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // type
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // head
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // companyId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // createTime
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // phone
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // name
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // email
            cursor.getShort(offset + 15) != 0, // chosen
            cursor.getShort(offset + 16) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyDeleteSupplierInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setArea(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setZipCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCompanyName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setTypeName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setRemark(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHead(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCompanyId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setCreateTime(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setPhone(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setName(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setEmail(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setChosen(cursor.getShort(offset + 15) != 0);
        entity.setIsLocal(cursor.getShort(offset + 16) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyDeleteSupplierInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyDeleteSupplierInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyDeleteSupplierInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
