package com.aripratom.pratomapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbfriends";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_FRIENDS = String.format("CREATE TABLE %s"
                    + "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_FRIENDS,
            DatabaseContract.FriendsColumn._ID,
            DatabaseContract.FriendsColumn.NIM,
            DatabaseContract.FriendsColumn.NAMA,
            DatabaseContract.FriendsColumn.KELAS,
            DatabaseContract.FriendsColumn.TELEPON,
            DatabaseContract.FriendsColumn.EMAIL,
            DatabaseContract.FriendsColumn.SOSMED
    );

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FRIENDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FRIENDS);
        onCreate(db);
    }
}


