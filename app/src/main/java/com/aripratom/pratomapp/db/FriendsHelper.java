package com.aripratom.pratomapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aripratom.pratomapp.entity.Friends;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.EMAIL;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.KELAS;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.NAMA;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.NIM;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.SOSMED;
import static com.aripratom.pratomapp.db.DatabaseContract.FriendsColumn.TELEPON;
import static com.aripratom.pratomapp.db.DatabaseContract.TABLE_FRIENDS;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */

public class FriendsHelper {

    public static final String DATABASE_TABLE = TABLE_FRIENDS;
    private static DatabaseHelper databaseHelper;
    private static FriendsHelper INSTANCE;

    private static SQLiteDatabase database;

    private FriendsHelper(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public static FriendsHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FriendsHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Friends> getAllFriends(){
        ArrayList<Friends> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,
                _ID + " ASC", null);
        cursor.moveToFirst();
        Friends friends;
        if (cursor.getCount() > 0){
            do {
                friends = new Friends();
                friends.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                friends.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                friends.setNama(cursor.getString(cursor.getColumnIndexOrThrow(NAMA)));
                friends.setKelas(cursor.getString(cursor.getColumnIndexOrThrow(KELAS)));
                friends.setTelepon(cursor.getString(cursor.getColumnIndexOrThrow(TELEPON)));
                friends.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(EMAIL)));
                friends.setSosmed(cursor.getString(cursor.getColumnIndexOrThrow(SOSMED)));

                arrayList.add(friends);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFriends(Friends friends){
        ContentValues args = new ContentValues();
        args.put(NIM, friends.getNim());
        args.put(NAMA, friends.getNama());
        args.put(KELAS, friends.getKelas());
        args.put(TELEPON, friends.getTelepon());
        args.put(EMAIL, friends.getEmail());
        args.put(SOSMED, friends.getSosmed());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateFriends(Friends friends){
        ContentValues args = new ContentValues();
        args.put(NIM, friends.getNim());
        args.put(NAMA, friends.getNama());
        args.put(KELAS, friends.getKelas());
        args.put(TELEPON, friends.getTelepon());
        args.put(EMAIL, friends.getEmail());
        args.put(SOSMED, friends.getSosmed());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + friends.getId() + "'", null);
    }

    public int deleteFriends(int id){
        return database.delete(TABLE_FRIENDS, _ID + " = '" + id + "'", null);
    }
}
