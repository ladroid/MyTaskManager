package com.example.lado.mytaskmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Task.db";
    public static final int VERSION = 1;
    public static final String CONTACTS_TABLE_NAME = "tasks";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_INFO = "info";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME + " ( " + CONTACTS_COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + CONTACTS_COLUMN_INFO + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertTask(String info) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("info", info);
        sqLiteDatabase.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateNote(String info) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("info", CONTACTS_COLUMN_INFO);

        // updating row
        db.update(CONTACTS_TABLE_NAME, values, CONTACTS_COLUMN_ID + " = ?",
                new String[]{String.valueOf(CONTACTS_COLUMN_ID)});
        return true;
    }

    public void deleteContact (Integer id) {
        final SQLiteDatabase db = getWritableDatabase();
        final String whereClause = CONTACTS_COLUMN_ID  + "=?";
        final String[] whereArgs = new String[] { String.valueOf(id) };
        db.delete(CONTACTS_TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + CONTACTS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_INFO)));
            res.moveToNext();
        }
        return array_list;
    }
}
