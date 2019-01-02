package com.fhh.technology.module.person.author;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;

import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.base.Constant;
import com.fhh.technology.utils.ToolBarOptions;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * desc:about author
 * Created by fhh on 2018/9/25
 */

public class AboutAuthorActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView mWebView;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AboutAuthorActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_about_author;
    }

    @Override
    public void initToolBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.theme_color), Constant.STATUS_BAR_ALPHA);
        ToolBarOptions options = new ToolBarOptions();
        options.isNeedNavigate = true;
        options.backgroundColor = R.color.theme_color;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {
        mWebView.loadUrl("file:///android_asset/author.html");
    }

    @Override
    public void onDestroyActivity() {
        releaseWebView();
    }

    private void releaseWebView() {
        // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebView);
        }

        mWebView.stopLoading();
        // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.clearView();
        mWebView.removeAllViews();
        try {
            mWebView.destroy();
        } catch (Throwable ex) {

        }
    }

}
