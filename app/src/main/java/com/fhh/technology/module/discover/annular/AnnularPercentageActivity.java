package com.fhh.technology.module.discover.annular;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.network.bean.CustomViewBean;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnnularPercentageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.custom_view)
    CustomView mCustomView;
    @BindView(R.id.custom_view_2)
    CustomView2 mCustomView2;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AnnularPercentageActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_annular_percentage;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.bg_find_title), Constant.STATUS_BAR_ALPHA);
        mToolbarTitle.setText(R.string.text_annular_percentage);
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
        CustomViewBean bean = new CustomViewBean();
        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.bg_animation);
        colorList.add(R.color.colorAccent);
        colorList.add(R.color.btn_login_color);
        bean.setColorList(colorList);

        List<Integer> valueList = new ArrayList<>();
        valueList.add(20);
        valueList.add(40);
        valueList.add(40);
        bean.setValueList(valueList);


        List<Float> percentageList = new ArrayList<>();
        percentageList.add(20f);
        percentageList.add(40f);
        percentageList.add(40f);
        bean.setRateList(percentageList);

        List<String> descList = new ArrayList<>();
        descList.add("hahhh");
        descList.add("iixxix");
        descList.add("lallal");
        bean.setLabelList(descList);

        bean.setPercent("33");

        mCustomView.setShow(bean.getColorList(), bean.getValueList(), bean.getRateList(), bean.getLabelList(), bean.getPercent(), true, true);

        List<String> nameList = new ArrayList<>();
        nameList.add("jajjdfaasdfasf");
        nameList.add("kskss");
        nameList.add("jaasej");
        mCustomView2.setShow(bean.getColorList(), bean.getRateList(), nameList);
    }

    @Override
    public void onDestroyActivity() {

    }
}
