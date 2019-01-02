package com.fhh.technology.module.discover.sqlite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public interface SQLiteContract {

    interface View {
        void createSQLite(int type);

        void showToast(String remind);

    }

    interface Presenter {

        void addData(String name, String age, boolean man, boolean woman, SQLiteDatabase db, ContentValues values);

        void deleteData(SQLiteDatabase db);

        void updateData(SQLiteDatabase db,ContentValues values);

        String queryData(SQLiteDatabase db);

    }
}
