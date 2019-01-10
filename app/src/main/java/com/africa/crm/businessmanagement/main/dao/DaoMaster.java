package com.africa.crm.businessmanagement.main.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        CompanyAccountInfoDao.createTable(db, ifNotExists);
        CompanyClientInfoDao.createTable(db, ifNotExists);
        CompanyContactInfoDao.createTable(db, ifNotExists);
        CompanyDeliveryOrderInfoDao.createTable(db, ifNotExists);
        CompanyInfoDao.createTable(db, ifNotExists);
        CompanyPayOrderInfoDao.createTable(db, ifNotExists);
        CompanyProductInfoDao.createTable(db, ifNotExists);
        CompanyQuotationInfoDao.createTable(db, ifNotExists);
        CompanySalesOrderInfoDao.createTable(db, ifNotExists);
        CompanyServiceRecordInfoDao.createTable(db, ifNotExists);
        CompanySupplierInfoDao.createTable(db, ifNotExists);
        CompanyTradingOrderInfoDao.createTable(db, ifNotExists);
        CompanyDeleteAccountInfoDao.createTable(db, ifNotExists);
        CompanyDeleteClientInfoDao.createTable(db, ifNotExists);
        CompanyDeleteContactInfoDao.createTable(db, ifNotExists);
        CompanyDeleteDeliveryOrderInfoDao.createTable(db, ifNotExists);
        CompanyDeleteInfoDao.createTable(db, ifNotExists);
        CompanyDeletePayOrderInfoDao.createTable(db, ifNotExists);
        CompanyDeleteProductInfoDao.createTable(db, ifNotExists);
        CompanyDeleteQuotationInfoDao.createTable(db, ifNotExists);
        CompanyDeleteSalesOrderInfoDao.createTable(db, ifNotExists);
        CompanyDeleteServiceRecordInfoDao.createTable(db, ifNotExists);
        CompanyDeleteSupplierInfoDao.createTable(db, ifNotExists);
        CompanyDeleteTradingOrderInfoDao.createTable(db, ifNotExists);
        DicInfoDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        CompanyAccountInfoDao.dropTable(db, ifExists);
        CompanyClientInfoDao.dropTable(db, ifExists);
        CompanyContactInfoDao.dropTable(db, ifExists);
        CompanyDeliveryOrderInfoDao.dropTable(db, ifExists);
        CompanyInfoDao.dropTable(db, ifExists);
        CompanyPayOrderInfoDao.dropTable(db, ifExists);
        CompanyProductInfoDao.dropTable(db, ifExists);
        CompanyQuotationInfoDao.dropTable(db, ifExists);
        CompanySalesOrderInfoDao.dropTable(db, ifExists);
        CompanyServiceRecordInfoDao.dropTable(db, ifExists);
        CompanySupplierInfoDao.dropTable(db, ifExists);
        CompanyTradingOrderInfoDao.dropTable(db, ifExists);
        CompanyDeleteAccountInfoDao.dropTable(db, ifExists);
        CompanyDeleteClientInfoDao.dropTable(db, ifExists);
        CompanyDeleteContactInfoDao.dropTable(db, ifExists);
        CompanyDeleteDeliveryOrderInfoDao.dropTable(db, ifExists);
        CompanyDeleteInfoDao.dropTable(db, ifExists);
        CompanyDeletePayOrderInfoDao.dropTable(db, ifExists);
        CompanyDeleteProductInfoDao.dropTable(db, ifExists);
        CompanyDeleteQuotationInfoDao.dropTable(db, ifExists);
        CompanyDeleteSalesOrderInfoDao.dropTable(db, ifExists);
        CompanyDeleteServiceRecordInfoDao.dropTable(db, ifExists);
        CompanyDeleteSupplierInfoDao.dropTable(db, ifExists);
        CompanyDeleteTradingOrderInfoDao.dropTable(db, ifExists);
        DicInfoDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CompanyAccountInfoDao.class);
        registerDaoClass(CompanyClientInfoDao.class);
        registerDaoClass(CompanyContactInfoDao.class);
        registerDaoClass(CompanyDeliveryOrderInfoDao.class);
        registerDaoClass(CompanyInfoDao.class);
        registerDaoClass(CompanyPayOrderInfoDao.class);
        registerDaoClass(CompanyProductInfoDao.class);
        registerDaoClass(CompanyQuotationInfoDao.class);
        registerDaoClass(CompanySalesOrderInfoDao.class);
        registerDaoClass(CompanyServiceRecordInfoDao.class);
        registerDaoClass(CompanySupplierInfoDao.class);
        registerDaoClass(CompanyTradingOrderInfoDao.class);
        registerDaoClass(CompanyDeleteAccountInfoDao.class);
        registerDaoClass(CompanyDeleteClientInfoDao.class);
        registerDaoClass(CompanyDeleteContactInfoDao.class);
        registerDaoClass(CompanyDeleteDeliveryOrderInfoDao.class);
        registerDaoClass(CompanyDeleteInfoDao.class);
        registerDaoClass(CompanyDeletePayOrderInfoDao.class);
        registerDaoClass(CompanyDeleteProductInfoDao.class);
        registerDaoClass(CompanyDeleteQuotationInfoDao.class);
        registerDaoClass(CompanyDeleteSalesOrderInfoDao.class);
        registerDaoClass(CompanyDeleteServiceRecordInfoDao.class);
        registerDaoClass(CompanyDeleteSupplierInfoDao.class);
        registerDaoClass(CompanyDeleteTradingOrderInfoDao.class);
        registerDaoClass(DicInfoDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
