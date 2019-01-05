package com.africa.crm.businessmanagement.main.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.africa.crm.businessmanagement.main.bean.CompanyInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "COMPANY_INFO".
*/
public class CompanyInfoDao extends AbstractDao<CompanyInfo, Long> {

    public static final String TABLENAME = "COMPANY_INFO";

    /**
     * Properties of entity CompanyInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property LocalId = new Property(0, Long.class, "localId", true, "_id");
        public final static Property Area = new Property(1, String.class, "area", false, "AREA");
        public final static Property Profession = new Property(2, String.class, "profession", false, "PROFESSION");
        public final static Property Code = new Property(3, String.class, "code", false, "CODE");
        public final static Property Address = new Property(4, String.class, "address", false, "ADDRESS");
        public final static Property NumA = new Property(5, String.class, "numA", false, "NUM_A");
        public final static Property Mid = new Property(6, String.class, "mid", false, "MID");
        public final static Property Type = new Property(7, String.class, "type", false, "TYPE");
        public final static Property TypeName = new Property(8, String.class, "typeName", false, "TYPE_NAME");
        public final static Property Head = new Property(9, String.class, "head", false, "HEAD");
        public final static Property CreateTime = new Property(10, String.class, "createTime", false, "CREATE_TIME");
        public final static Property Phone = new Property(11, String.class, "phone", false, "PHONE");
        public final static Property Name = new Property(12, String.class, "name", false, "NAME");
        public final static Property Id = new Property(13, String.class, "id", false, "ID");
        public final static Property State = new Property(14, String.class, "state", false, "STATE");
        public final static Property StateName = new Property(15, String.class, "stateName", false, "STATE_NAME");
        public final static Property Email = new Property(16, String.class, "email", false, "EMAIL");
        public final static Property Chosen = new Property(17, boolean.class, "chosen", false, "CHOSEN");
        public final static Property IsDeleted = new Property(18, boolean.class, "isDeleted", false, "IS_DELETED");
        public final static Property IsLocal = new Property(19, boolean.class, "isLocal", false, "IS_LOCAL");
    }


    public CompanyInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CompanyInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"COMPANY_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: localId
                "\"AREA\" TEXT," + // 1: area
                "\"PROFESSION\" TEXT," + // 2: profession
                "\"CODE\" TEXT," + // 3: code
                "\"ADDRESS\" TEXT," + // 4: address
                "\"NUM_A\" TEXT," + // 5: numA
                "\"MID\" TEXT," + // 6: mid
                "\"TYPE\" TEXT," + // 7: type
                "\"TYPE_NAME\" TEXT," + // 8: typeName
                "\"HEAD\" TEXT," + // 9: head
                "\"CREATE_TIME\" TEXT," + // 10: createTime
                "\"PHONE\" TEXT," + // 11: phone
                "\"NAME\" TEXT," + // 12: name
                "\"ID\" TEXT," + // 13: id
                "\"STATE\" TEXT," + // 14: state
                "\"STATE_NAME\" TEXT," + // 15: stateName
                "\"EMAIL\" TEXT," + // 16: email
                "\"CHOSEN\" INTEGER NOT NULL ," + // 17: chosen
                "\"IS_DELETED\" INTEGER NOT NULL ," + // 18: isDeleted
                "\"IS_LOCAL\" INTEGER NOT NULL );"); // 19: isLocal
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"COMPANY_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CompanyInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(2, area);
        }
 
        String profession = entity.getProfession();
        if (profession != null) {
            stmt.bindString(3, profession);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(4, code);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String numA = entity.getNumA();
        if (numA != null) {
            stmt.bindString(6, numA);
        }
 
        String mid = entity.getMid();
        if (mid != null) {
            stmt.bindString(7, mid);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(9, typeName);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(10, head);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(11, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(12, phone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(13, name);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(14, id);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(15, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(16, stateName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(17, email);
        }
        stmt.bindLong(18, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(19, entity.getIsDeleted() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CompanyInfo entity) {
        stmt.clearBindings();
 
        Long localId = entity.getLocalId();
        if (localId != null) {
            stmt.bindLong(1, localId);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(2, area);
        }
 
        String profession = entity.getProfession();
        if (profession != null) {
            stmt.bindString(3, profession);
        }
 
        String code = entity.getCode();
        if (code != null) {
            stmt.bindString(4, code);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(5, address);
        }
 
        String numA = entity.getNumA();
        if (numA != null) {
            stmt.bindString(6, numA);
        }
 
        String mid = entity.getMid();
        if (mid != null) {
            stmt.bindString(7, mid);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(8, type);
        }
 
        String typeName = entity.getTypeName();
        if (typeName != null) {
            stmt.bindString(9, typeName);
        }
 
        String head = entity.getHead();
        if (head != null) {
            stmt.bindString(10, head);
        }
 
        String createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindString(11, createTime);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(12, phone);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(13, name);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(14, id);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(15, state);
        }
 
        String stateName = entity.getStateName();
        if (stateName != null) {
            stmt.bindString(16, stateName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(17, email);
        }
        stmt.bindLong(18, entity.getChosen() ? 1L: 0L);
        stmt.bindLong(19, entity.getIsDeleted() ? 1L: 0L);
        stmt.bindLong(20, entity.getIsLocal() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CompanyInfo readEntity(Cursor cursor, int offset) {
        CompanyInfo entity = new CompanyInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // localId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // area
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // profession
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // code
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // address
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // numA
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mid
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // type
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // typeName
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // head
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // createTime
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // phone
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // name
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // id
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // state
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // stateName
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // email
            cursor.getShort(offset + 17) != 0, // chosen
            cursor.getShort(offset + 18) != 0, // isDeleted
            cursor.getShort(offset + 19) != 0 // isLocal
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CompanyInfo entity, int offset) {
        entity.setLocalId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setArea(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setProfession(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCode(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNumA(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMid(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setType(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTypeName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHead(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setCreateTime(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPhone(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setId(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setState(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setStateName(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setEmail(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setChosen(cursor.getShort(offset + 17) != 0);
        entity.setIsDeleted(cursor.getShort(offset + 18) != 0);
        entity.setIsLocal(cursor.getShort(offset + 19) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CompanyInfo entity, long rowId) {
        entity.setLocalId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CompanyInfo entity) {
        if(entity != null) {
            return entity.getLocalId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CompanyInfo entity) {
        return entity.getLocalId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
