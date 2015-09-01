package com.chinazmob.unlockearn.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 晓宇 on 2015/3/19.
 */
public class DataBaseManager {
    private static DataBaseManager INSTANCE = null;

    public static synchronized DataBaseManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataBaseManager(context);
        }
        return INSTANCE;
    }

    static final String DATABASE_NAME = "unlockearn";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "user_info";
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_SEX = "sex";
    static final String KEY_BIRTH = "birth_day";
    static final String KEY_STATE = "state";
    static final String KEY_MONEY = "money";
    static final String KEY_TOTAL_MONEY = "total_money";
    static final String KEY_TODAY_MONEY = "today_money";
    static final String KEY_RIGHT_BASE_COUNT = "right_base_count";
    static final String KEY_REWARD = "right_reward";
    static final String KEY_RIGHT_COUNT = "right_count";
    static final String KEY_RIGHT_HOUR = "right_hour";


    SQLiteDatabase mdb;
    DatabaseHelper mdbHelper;
    static final String DATABASE_CREATE =
            String.format("create table %s( %s integer primary key autoincrement,%s text, %s text " +
                            ", %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text);", DATABASE_TABLE,KEY_ID, KEY_NAME, KEY_SEX,
                    KEY_BIRTH, KEY_STATE, KEY_MONEY, KEY_TOTAL_MONEY, KEY_TODAY_MONEY, KEY_RIGHT_BASE_COUNT, KEY_REWARD, KEY_RIGHT_COUNT,
                    KEY_RIGHT_HOUR);

    public DataBaseManager(Context context) {
        mdbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", DATABASE_TABLE));
            onCreate(db);
        }
    }

    public void open() throws SQLException {
        mdb = mdbHelper.getWritableDatabase();
    }

    public void close() {
        try {
            mdbHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mdb = null;
    }

    public boolean updateUserInfo(long rowId, UserInfo userInfo) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, userInfo.getName());
        args.put(KEY_SEX, userInfo.getSex());
        args.put(KEY_BIRTH, userInfo.getBirthday());
        args.put(KEY_STATE, userInfo.getState());
        args.put(KEY_MONEY, userInfo.getMoney());
        args.put(KEY_TOTAL_MONEY, userInfo.getTotalMoney());
        args.put(KEY_TODAY_MONEY, userInfo.getTodayMoney());
        args.put(KEY_RIGHT_BASE_COUNT, userInfo.getRightSwipBaseCount());
        args.put(KEY_REWARD, userInfo.getRightSwipReward());
        args.put(KEY_RIGHT_COUNT, userInfo.getRightSwipCount());
        args.put(KEY_RIGHT_HOUR, userInfo.getRightSwipBaseHour());
        return mdb.update(DATABASE_TABLE, args, KEY_ID + "=" + rowId, null) > 0;
    }
}
