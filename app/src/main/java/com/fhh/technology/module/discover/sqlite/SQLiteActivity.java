package com.fhh.technology.module.discover.sqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;


import butterknife.BindView;

public class SQLiteActivity extends BaseActivity implements SQLiteContract.View, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_age)
    EditText mEtAge;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_woman)
    RadioButton mRbWoman;
    @BindView(R.id.btn_insert)
    Button mBtnInsert;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    private SQLitePresenter mPresenter;
    private int version = 2;
    private DBOpenHelper mDbOpenHelper;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, SQLiteActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_sqlite;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText(getString(R.string.sqlite_title));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_find_title;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {
        mPresenter = new SQLitePresenter(mActivity, this);
    }

    @Override
    public void initData() {
        mDbOpenHelper = new DBOpenHelper(mActivity, Constant.SQLITE_CREATE, null, version);
        mDbOpenHelper.getWritableDatabase();
        SQLiteOperation();
    }


    private void SQLiteOperation() {
        mBtnInsert.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mBtnUpdate.setOnClickListener(this);
        mBtnQuery.setOnClickListener(this);
    }


    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                createSQLite(Constant.ADD);
                break;
            case R.id.btn_delete:
                createSQLite(Constant.DELETE);
                break;
            case R.id.btn_update:
                createSQLite(Constant.UPDATE);
                break;
            case R.id.btn_query:
                createSQLite(Constant.QUERY);
                break;
        }
    }

    @Override
    public void createSQLite(int type) {
        if (mDbOpenHelper == null) {
//            ToastUtil.showToast(mActivity, "未创建数据库");
            showToast("未创建数据库");
            return;
        }
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = mEtName.getText().toString().trim();
        String age = mEtAge.getText().toString().trim();
        boolean man = mRbMan.isChecked();
        boolean woman = mRbWoman.isChecked();
        values.clear();
        switch (type) {
            case Constant.ADD:
                mPresenter.addData(name, age, man, woman, db, values);
                break;
            case Constant.DELETE:
                mPresenter.deleteData(db);
                break;
            case Constant.UPDATE:
                mPresenter.updateData(db, values);
                break;
            case Constant.QUERY:
                mPresenter.queryData(db);
                break;
        }
    }

    @Override
    public void setSQLiteContent(String content) {
        mTvContent.setText(content);
    }
}
