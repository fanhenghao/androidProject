package com.fhh.technology.module.discover.edit;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

public class EditTextActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.et_error)
    EditText mEtError;


    public static void start(Context context) {
        Intent intent = new Intent(context, EditTextActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int setContentLayout() {
        return R.layout.activity_edit_text;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText("EditText");
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_find_title;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mEtError.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())){
                    mEtError.setError("不能有内容");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onDestroyActivity() {

    }
}
