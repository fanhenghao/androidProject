package com.fhh.technology.module.discover.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.fhh.technology.base.Constant;

import java.util.ArrayList;
import java.util.List;

public class SQLitePresenter implements SQLiteContract.Presenter {

    private Context mContext;
    private SQLiteContract.View mView;
    private List<String> queryList = new ArrayList<>();//记录从数据库查询到的数据
    private List<String> mIdList = new ArrayList<>();//记录从数据库里查询到的id

    public SQLitePresenter(Context context, SQLiteContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void addData(String name, String age, boolean man, boolean woman, SQLiteDatabase db, ContentValues values) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(age)) {
            mView.showToast("姓名和年龄不能为空");
            return;
        }
        values.put("name", name);
        values.put("age", age);
        if (man) {
            values.put("sex", "男");
        } else if (woman) {
            values.put("sex", "女");
        } else {
            values.put("sex", "未录入");
        }
        db.insert(Constant.SQLITE_CHART, null, values);
        mView.showToast("录入数据库成功");
        queryData(db);
    }

    @Override
    public void deleteData(SQLiteDatabase db) {
        Cursor cursorDelete = db.query(Constant.SQLITE_CHART, null, null, null, null, null, null, null);
        mIdList.clear();
        while (cursorDelete.moveToNext()) {
            String deleteId = (cursorDelete.getString(cursorDelete.getColumnIndex("id")));//id为自增长，默认从1开始
            mIdList.add(deleteId);
        }
        if (mIdList.size() > 0) {
            db.delete(Constant.SQLITE_CHART, "id = ?", new String[]{mIdList.get(0)});//删除集合的第一条数据
            mView.showToast("已删除当前数据库的第一条记录");
            queryData(db);
        } else {
            mView.showToast("当前数据库为空");
        }
    }

    @Override
    public void updateData(SQLiteDatabase db, ContentValues values) {
        Cursor cursorDelete = db.query(Constant.SQLITE_CHART, null, null, null, null, null, null, null);
        mIdList.clear();
        while (cursorDelete.moveToNext()) {
            String deleteId = (cursorDelete.getString(cursorDelete.getColumnIndex("id")));//id为自增长，默认从1开始
            mIdList.add(deleteId);
        }
        if (mIdList.size() > 0) {
            values.put("age", "12");
            db.update(Constant.SQLITE_CHART, values, null, null);
            mView.showToast("已修改所有数据年龄为'12'");
            queryData(db);
        } else {
            mView.showToast("数据库为空");
        }
    }

    @Override
    public void queryData(SQLiteDatabase db) {
        /**
         * table：指定查询的表名，对应 from table_name
         * columns：指定查询的列名，对应 select column1,column2 ...
         * selection：指定 where 的约束条件，where column = value
         * selectionArgs：为 where 中的占位符提供具体的值
         * groupBy：指定需要分组的列，group by column
         * having：对分组后的结果进一步约束，having column = value
         * orderBy：指定查询结果的排序方式，order by column
         */
        Cursor cursor = db.query(Constant.SQLITE_CHART, null, null, null, null, null, null, null);
        queryList.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String cursorName = cursor.getString(cursor.getColumnIndex("name"));
            String cursorAge = cursor.getString(cursor.getColumnIndex("age"));
            String cursorSex = cursor.getString(cursor.getColumnIndex("sex"));
            queryList.add("id:" + id + ",name:" + cursorName + ",age:" + cursorAge + ",sex:" + cursorSex + "\n");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < queryList.size(); i++) {
            sb.append(queryList.get(i));
        }
        String data = sb.toString().trim();
        if (TextUtils.isEmpty(data)) {
            mView.showToast("查询到数据库为空");
        }else {
            mView.setSQLiteContent(data);
        }

    }
}
