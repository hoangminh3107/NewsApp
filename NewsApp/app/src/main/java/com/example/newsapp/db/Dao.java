package com.example.newsapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.newsapp.Models.NewsHeadLine;

import java.util.ArrayList;
import java.util.List;

public class Dao {
    Db dbhp;

    public Dao(Context context) {
        dbhp = new Db(context);
    }

    public int Insert(NewsHeadLine newsHeadLine) {
        SQLiteDatabase sqLiteDatabase = dbhp.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", String.valueOf(newsHeadLine.getSource().getName()));
        values.put("author", newsHeadLine.getAuthor());
        values.put("title", newsHeadLine.getTitle());
        values.put("description", newsHeadLine.getDescription());
        values.put("url", newsHeadLine.getUrl());
        values.put("urlToImage", newsHeadLine.getUrlToImage());
        values.put("publishedAt", newsHeadLine.getPublishedAt());
        values.put("content", newsHeadLine.getContent());

        long kq = sqLiteDatabase.insert("data", null, values);
        if (kq == 0)
            return -1;
        return 1;

    }


    public List<NewsHeadLine> getAll() {
        List<NewsHeadLine> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhp.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from data", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
//                list.add(new NewsHeadLine(cursor.getString(0),cursor.getString(1),cursor.getString(2)
//                ,cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)));
                list.add(new NewsHeadLine(cursor.getString(0), cursor.getString(1), cursor.getString(2)
                        , cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int Del(String tt) {
        SQLiteDatabase sqLiteDatabase = dbhp.getWritableDatabase();
        long kq = sqLiteDatabase.delete("data", "title =? ", new String[]{tt});
        if (kq == 0)
            return -1;
        return 1;
    }

    public boolean Check() {

        return true;
    }
}
