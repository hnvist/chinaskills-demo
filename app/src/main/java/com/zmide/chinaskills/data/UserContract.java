package com.zmide.chinaskills.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class UserContract {

    protected String TAG = "用户数据管理类";

    protected Context mContext;
    protected static UserContract mInstance;
    protected UsersHelper dbHelper;

    public static UserContract getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new UserContract(mContext);
        }
        return mInstance;
    }

    private UserContract(Context mContext) {
        this.mContext = mContext;
        dbHelper = new UsersHelper(mContext);
    }

    // 提供注册用户接口
    public boolean apiLogIn(String name, String password, String phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.COLUMN_NAME_NAME, name);
        values.put(User.COLUMN_NAME_PASSWORD, password);
        values.put(User.COLUMN_NAME_PHONE, phone);

        long newRowId = db.insert(User.TABLE_NAME, null, values);

        if (newRowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean apiSingIn(String name, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = User.COLUMN_NAME_NAME + " = ? AND " + User.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {name, password};

        String sortOrder = User.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                User.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List users = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(User._ID));
            users.add(itemId);
        }
        cursor.close();

        if (users.size() < 1) {
            return false;
        } else {
            return true;
        }

    }

    public static class User implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_PHONE = "phone";
    }

}
