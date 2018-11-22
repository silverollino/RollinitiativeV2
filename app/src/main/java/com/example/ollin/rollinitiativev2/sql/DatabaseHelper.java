package com.example.ollin.rollinitiativev2.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ollin.rollinitiativev2.model.Spacecraft;
import com.example.ollin.rollinitiativev2.model.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Usermanager.db";
    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID ="user_id";
    private static final String COLUMN_USER_NAME ="user_name";
    private static final String COLUMN_USER_EMAIL ="user_email";
    private static final String COLUMN_USER_PASS ="user_pass";

    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_PASS + " TEXT);";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS "+ TABLE_USER;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASS, user.getPassword());

        database.insert(TABLE_USER,null,values);
        database.close();
    }

    public  boolean chkUser(String email, String pass){
        String[] columns = { COLUMN_USER_ID};
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASS + " = ?";
        String[] selectionArgs = { email, pass };

        Cursor cursor = database.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        database.close();

        if(cursorCount > 0){
            return true;
        }
        return false;
    }
}
