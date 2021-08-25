package com.fhh.technology.module.discover.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fhh.technology.utils.ToastUtil;

public class DBOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    /**
     * integer：整形
     * real：浮点型
     * text：文本类型
     * blob：二进制类型
     * PRIMARY KEY将id列设置为主键
     * AutoIncrement关键字表示id列是自动增长的
     */
    public static final String CREATE_TECHNOLOGY = "CREATE TABLE technology (" + "id integer PRIMARY KEY Autoincrement, " + "name text, " + "age text, " + "sex text )";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TECHNOLOGY);
        ToastUtil.showToast(mContext, "首次创建数据库成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ToastUtil.showToast(mContext, "数据库更新版本为：" + newVersion);
        Log.e("TECHNOLOGY", mContext.getClass().getSimpleName() + "----oldVersion:" + oldVersion + ",newVersion:" + newVersion);
    }

    /**
     * 使用SQLite的语言格式如下：
     * 添加数据的方法如下：
     * SQL插入语句:
     * INSERT INTO Book(name,author,pages,price) VALUES
     * ("The Da Vinci Code" ,"Dan Brown",454,16.96);
     *db.execSQL("INSERT INTO Book(name,author,pages,price) VALUES(?,?,?,?",new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});
     *
     *删除数据的方法如下：
     * SQL删除语句:
     * DELETE FROM Book WHERE pages > 500;
     *
     *db.execSQL("DELETE FROM Book WHERE pages > ?",new String[]{"500"});
     *
     *更新数据的方法如下：
     * SQL更新语句:
     * UPDATE Book SET price = 10.99 WHERE name = "The Da Vinci Code" ;
     *db.execSQL("UPDATE Book SET price = ? WHERE name = ?",new String[]{"10.99", "The Da Vinci Code"});
     *
     *查询数据的方法如下：
     * SQL查询语句:
     * SELECT * FROM BOOK ;
     *db.rawQuery("SELECT * FROM BOOK",null);
     */


}
