package com.fhh.technology.module.discover.edit;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
    @BindView(R.id.et)
    EditText mEt;

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
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//初始化EditText光标放在文本框末尾（解决android低版本第一次进入光标在前面的问题）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mEtError.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            mEtError.setTextDirection(View.TEXT_DIRECTION_RTL);
            mEt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
            mEt.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        initEditText(mEt);
        initEditText(mEtError);
        mEtError.setText(getString(R.string.text_shadow_size));
    }
    private void initEditText(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (TextUtils.isEmpty(s.toString())) {
                        et.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                        et.setTextDirection(View.TEXT_DIRECTION_RTL);
                    } else {
                        et.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                        et.setTextDirection(View.TEXT_DIRECTION_LTR);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyActivity() {

    }
}
