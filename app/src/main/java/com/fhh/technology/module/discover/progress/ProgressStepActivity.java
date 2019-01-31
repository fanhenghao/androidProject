package com.fhh.technology.module.discover.progress;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

public class ProgressStepActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.step_view)
    StepView mStepView;
    @BindView(R.id.btn)
    Button mBtn;

    private int mPosition = 0;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, ProgressStepActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_progress_step;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText(getString(R.string.progress_step_title));
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.bg_find_title;
        setToolBar(R.id.toolbar, options);
    }

    public void onClick(View view) {
        if (mPosition < 3) {
            mPosition++;
        } else {
            mPosition = 0;
        }
        mStepView.setCurrentStep(mPosition);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mStepView.setCurrentStep(mPosition);
        mStepView.setListener(new StepView.onClickListener() {
            @Override
            public void onClick(int position) {
                mPosition = position;
                mStepView.setCurrentStep(position);
            }
        });
    }

    @Override
    public void onDestroyActivity() {

    }
}
