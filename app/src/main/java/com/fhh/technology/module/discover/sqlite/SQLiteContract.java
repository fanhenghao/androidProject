package com.fhh.technology.module.discover.sqlite;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fhh.technology.base.IBasePresenter;
import com.fhh.technology.base.IBaseView;

public interface SQLiteContract {

    interface View extends IBaseView {
        void createSQLite(int type);

        void setSQLiteContent(String content);

    }

    interface Presenter extends IBasePresenter {

        void addData(String name, String age, boolean man, boolean woman, SQLiteDatabase db, ContentValues values);

        void deleteData(SQLiteDatabase db);

        void updateData(SQLiteDatabase db,ContentValues values);

        void queryData(SQLiteDatabase db);

    }
}
