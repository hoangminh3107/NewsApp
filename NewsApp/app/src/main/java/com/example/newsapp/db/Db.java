package com.example.newsapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsapp.Models.NewsHeadLine;

public class Db extends SQLiteOpenHelper {

    public Db(@Nullable Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table data( name text, author text ,title text ,description text, url text,urlToImage text, publishedAt text, content text )";
        db.execSQL(sql);
        String sql2="create table data2( name text, author text ,title text ,description text, url text,urlToImage text, publishedAt text, content text )";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists data ");
        db.execSQL("drop table if exists data2 ");
        onCreate(db);
    }


}
