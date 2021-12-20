package com.tunibest.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper {

    public static final String DBNAME="Tunibest4.db";

    public MyDataBase(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("drop table if exists Users");
        db.execSQL("create Table ActUser (id INTEGER primary key AUTOINCREMENT,Name Text, Email INTEGER,Password TEXT) ");
        db.execSQL("create Table MovieN (id INTEGER primary key AUTOINCREMENT,MovieName Text, url BLOB) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists ActUser");
        db.execSQL("drop table if exists MovieN");
    }

    public Boolean Createuser (String name,String mail,String pass  ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Email",mail);
        contentValues.put("Password",pass);

        long res = db.insert("ActUser",null,contentValues);
        if (res == -1)
            return false;
        else
            return true;
    }

    public void SaveMovieInfo (String name, byte[] url){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MovieName",name);

        contentValues.put("url",url);
        db.delete("MovieN", null, null);
        long res = db.insert("MovieN",null,contentValues);
        if (res == -1)
            Log.v("TAG","Not  Inserted");
        else
            Log.v("TAG","!!!!! Well Inserted");
    }
}
