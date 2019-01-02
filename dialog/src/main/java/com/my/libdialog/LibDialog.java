package com.my.libdialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class LibDialog {

    private Context mContext;                       //上下文

    private View mDialogLayout;                     //弹窗布局

    private boolean mCancelable = true;             //返回键、窗体外区域是否可点击取消，默认可以

    private View.OnClickListener positiveButton;    //positive区域监听器
    private View.OnClickListener negativeButton;    //negative区域监听器

    private AdapterView.OnItemClickListener mItemClickListener;  //item的点击事件

    private Dialog dialog;                          //构建的弹窗

    private int mCustomAnim;                        //自定义动画

    private boolean mIsShowTitle;                   //是否显示标题，默认不显示
    private boolean mIsShowNegativeButton;          //是否显示negative区域按钮，默认不显示
    private boolean mIsShowPositiveButton;          //是否显示positive区域按钮，默认不显示
    private boolean mIsShowListView;                //是否在内容区显示ListView，默认不显示
    private boolean mIsHaveCustomAnim;              //是否含有自定义的动画效果

    private boolean mIsShowBottomTitle;             //是否显示底部弹窗标题，默认不显示
    private boolean mIsShowBottomNegativeButton;    //是否显示底部弹窗的negative区域按钮，默认不显示
    private boolean mIsBottomDialog;                //是否是底部弹窗，默认中间弹窗

    private LibDialogAdapter mAdapter;                     //Adapter，设配自定义的数据
    private List<LibBean> mDataList;         //数据源，显示的文本

    public static final String BOTTOM = "BOTTOM";   //底部弹窗标志

    /**
     * 中间弹窗，构造函数
     *
     * @param context 上下文
     */
    public LibDialog(Context context) {
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogLayout = inflater.inflate(R.layout.my_custom_dialog_layout, null);
    }

    /**
     * 中间弹窗，构造函数，需要传入自定义动画的ID，若传入0，则代表无动画
     *
     * @param context    上下文
     * @param customAnim 自定义的动画效果ID
     */
    public LibDialog(Context context, int customAnim) {
        this(context);
        mCustomAnim = customAnim;
        mIsHaveCustomAnim = true;
    }

    /**
     * 底部弹窗，构造函数，需要传入String类型参数，BOTTOM，才会显示底部Dialog
     *
     * @param context 上下文
     * @param gravity 位置，String类型，必须是"BOTTOM"才会显示底部Dialog
     */
    public LibDialog(Context context, String gravity) {
        this.mContext = context;
        if (gravity.equals(BOTTOM)) {
            mIsBottomDialog = true;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogLayout = inflater.inflate(R.layout.my_custom_bottom_dialog_layout, null);
    }

    /**
     * 都不弹窗，构造函数，需要传入String类型参数，BOTTOM，才会显示底部Dialog；自定义动画效果
     *
     * @param context    上下文
     * @param customAnim 自定义的动画效果
     * @param gravity    位置，String类型，必须是"BOTTOM"才会显示底部Dialog
     */
    public LibDialog(Context context, int customAnim, String gravity) {
        this.mContext = context;
        if (gravity.equals(BOTTOM)) {
            mIsBottomDialog = true;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDialogLayout = inflater.inflate(R.layout.my_custom_bottom_dialog_layout, null);
        mCustomAnim = customAnim;
        mIsHaveCustomAnim = true;
    }

    /**
     * 能否返回键取消和点击弹窗外部区域取消
     * 中间、底部弹窗共用
     *
     * @param boolean1 true 代表可以取消，false 不可取消
     * @return this
     */
    public LibDialog setCancelable(Boolean boolean1) {
        this.mCancelable = boolean1;
        return this;
    }

    /**
     * 中间弹窗，设置标题 int型 不常用，适配更多类型可重载多个该方法，参数类型不同即可
     *
     * @param title int型参数
     * @return this
     */
    public LibDialog setTitle(int title) {
        mIsShowTitle = true;
        ((TextView) mDialogLayout.findViewById(R.id.title)).setText(title);
        return this;
    }

    /**
     * 中间弹窗，设置标题 String型 最常用
     *
     * @param title String型参数
     * @return this
     */
    public LibDialog setTitle(String title) {
        mIsShowTitle = true;
        ((TextView) mDialogLayout.findViewById(R.id.title)).setText(title);
        return this;
    }

    /**
     * 中间弹窗，设置标题文字大小
     *
     * @param size 大小值，int型
     * @return this
     */
    public LibDialog setTitleSize(int size) {
        ((TextView) mDialogLayout.findViewById(R.id.title)).setTextSize(size);
        return this;
    }

    /**
     * 中间弹窗，设置标题文字颜色
     *
     * @param color 颜色
     * @return this
     */
    public LibDialog setTitleColor(int color) {
        ((TextView) mDialogLayout.findViewById(R.id.title)).setTextColor(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置标题字体为粗体
     *
     * @return this
     */
    public LibDialog setTitleStyleBold(){
        TextView tv = (TextView)mDialogLayout.findViewById(R.id.title);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        return this;
    }

    /**
     * 中间弹窗，设置标题区域的背景颜色 不常用
     *
     * @param color 颜色
     * @return this
     */
    public LibDialog setTitleBackgroundColor(int color) {
        mDialogLayout.findViewById(R.id.title_background).setBackgroundColor(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置内容，int型，不常用，适配更多类型可重载多个该方法，参数类型不同即可
     *
     * @param message int型参数
     * @return this
     */
    public LibDialog setMessage(int message) {
        ((TextView) mDialogLayout.findViewById(R.id.message)).setText(message);
        return this;
    }

    /**
     * 中间弹窗，设置内容，String型，最常用
     *
     * @param message String型信息
     * @return this
     */
    public LibDialog setMessage(String message) {
        ((TextView) mDialogLayout.findViewById(R.id.message)).setText(message);
        return this;
    }

    /**
     * 中间弹窗，设置内容的文本颜色
     *
     * @param color 文本颜色
     * @return this
     */
    public LibDialog setMessageColor(int color){
        ((TextView) mDialogLayout.findViewById(R.id.message)).setTextColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置内容区域的背景色
     *
     * @param color 背景色
     * @return this
     */
    public LibDialog setMessageBackground(int color){
        mDialogLayout.findViewById(R.id.content).setBackgroundColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置negative区域的文本和点击事件，一般为"取消"
     * 同AlertDialog.Builder的设置名称相同
     *
     * @param negativeText 按钮文本
     * @param listener     监听器
     * @return this
     */
    public LibDialog setNegativeButton(String negativeText, View.OnClickListener listener) {
        mIsShowNegativeButton = true;
        ((TextView) mDialogLayout.findViewById(R.id.negative)).setText(negativeText);
        this.negativeButton = listener;
        return this;
    }

    /**
     * 中间弹窗，设置negative区域显示文本的颜色，如蓝色的"取消"文字
     * 多数APP如网购APP，在某个商品浏览页面，不希望用户退出又必须要给出退出提示时，多将negative设置为显眼的颜色
     * 而positive设置为暗色。所以该方法还是使用比较常见的
     *
     * @param color 颜色
     * @return this
     */
    public LibDialog setNegativeButtonColor(int color) {
        ((TextView) mDialogLayout.findViewById(R.id.negative)).setTextColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置negative文本的大小
     *
     * @param size 文本大小
     * @return this
     */
    public LibDialog setNegativeButtonTextSize(int size){
        ((TextView) mDialogLayout.findViewById(R.id.negative)).setTextSize(size);
        return this;
    }

    /**
     * 中间弹窗，设置negative文本字体为粗体
     *
     * @return this
     */
    public LibDialog setNegativeButtonStyleBold(){
        TextView tv = (TextView) mDialogLayout.findViewById(R.id.negative);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        return this;
    }

    /**
     * 中间弹窗，设置positive区域的文本和点击事件，一般为"确定"
     * 同AlertDialog.Builder的设置名称相同
     *
     * @param positiveText 按钮文本
     * @param listener     监听器
     * @return this
     */
    public LibDialog setPositiveButton(String positiveText, View.OnClickListener listener) {
        mIsShowPositiveButton = true;
        ((TextView) mDialogLayout.findViewById(R.id.positive)).setText(positiveText);
        this.positiveButton = listener;
        return this;
    }

    /**
     * 中间弹窗，设置positive区域显示文本的颜色，如蓝色的"确定"文字
     *
     * @param color 颜色
     * @return this
     */
    public LibDialog setPositiveButtonColor(int color) {
        ((TextView) mDialogLayout.findViewById(R.id.positive)).setTextColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 中间弹窗，设置positive区域显示文本的大小
     *
     * @param size 文本大小
     * @return this
     */
    public LibDialog setPositiveButtonSize(int size){
        ((TextView) mDialogLayout.findViewById(R.id.positive)).setTextSize(size);
        return this;
    }

    /**
     * 中间弹窗，设置positive文本字体为粗体
     *
     * @return this
     */
    public LibDialog setPositiveButtonStyleBold(){
        TextView tv = (TextView) mDialogLayout.findViewById(R.id.positive);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        return this;
    }

    /**
     * 中间弹窗，重新设置内容的显示控件
     * 默认的Dialog只有一个显示的TextView，替换显示控件可以实现显示更丰富的内容，如图片 + 文本。
     *
     * @param v 要显示的控件
     * @return this
     */
    public LibDialog setView(View v) {
        ((FrameLayout) mDialogLayout.findViewById(R.id.sv)).removeAllViews();
        //进行判断，否则第二次弹出Dialog时会报异常
        //异常：java.lang.IllegalStateException: The specified child already has a parent.
        //     You must call removeView() on the child's parent first.
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        ((FrameLayout) mDialogLayout.findViewById(R.id.sv)).addView(v);
        return this;
    }

    /**
     * 设置显示内容为ListView，传入要显示的数组和监听事件
     * 显示默认颜色
     * 中间弹窗和底部弹窗共用
     *
     * @param data     数据源，数组，String类型
     * @param listener item的监听事件，短按类型
     * @return this
     */
    public LibDialog setListView(String[] data, AdapterView.OnItemClickListener listener) {
        setListView(Arrays.asList(data), listener);
        return this;
    }

    /**
     * 设置显示内容为ListView，传入要显示的list和监听事件
     * 显示默认颜色
     * 中间弹窗和底部弹窗共用
     *
     * @param list     数据源，list，String类型
     * @param listener item的监听事件，短按类型
     * @return this
     */
    public LibDialog setListView(List<String> list, AdapterView.OnItemClickListener listener) {
        mItemClickListener = listener;
        mIsShowListView = true;
        mDataList = new ArrayList<>();
        for (String str : list) {
            mDataList.add(new LibBean(str));
        }
        mAdapter = new LibDialogAdapter(mContext, R.layout.string_item_layout, mDataList);
        return this;
    }

    /**
     * 设置显示内容为ListView，可以设置item的颜色，且可以分别设置
     * 数据源类型：数组，数组
     * 中间弹窗和底部弹窗共用
     *
     * @param data     数据源，数组，String类型
     * @param colors   颜色数据源，数组，Integer类型
     * @param listener item的监听事件，短按类型
     * @return this
     */
    public LibDialog setListView(String[] data, Integer[] colors, AdapterView.OnItemClickListener listener) {
        setListView(Arrays.asList(data), Arrays.asList(colors), listener);
        return this;
    }

    /**
     * 设置显示内容为ListView，可以设置item的颜色，且可以分别设置
     * 数据源类型：List，数组
     * 中间弹窗和底部弹窗共用
     *
     * @param list     数据源，List，String类型
     * @param colors   颜色数据源，数组，Integer类型
     * @param listener item的监听事件，短按类型
     * @return this
     */
    public LibDialog setListView(List<String> list, Integer[] colors, AdapterView.OnItemClickListener listener) {
        setListView(list, Arrays.asList(colors), listener);
        return this;
    }

    /**
     * 设置显示内容为ListView，可以设置item的颜色，且可以分别设置
     * 数据源类型：数组，List
     * 中间弹窗和底部弹窗共用
     *
     * @param data     数据源，数组，String类型
     * @param colors   颜色数据源，List，Integer类型
     * @param listener item的监听事件，短按类型
     * @return this
     */
    public LibDialog setListView(String[] data, List<Integer> colors, AdapterView.OnItemClickListener listener) {
        setListView(Arrays.asList(data), colors, listener);
        return this;
    }

    /**
     * 设置显示内容为ListView，可以设置item的颜色，且可以分别设置
     * 数据源类型：List，List，
     * 不管传入的数据源和颜色数据源的类型是数组还是List，最后都要使用这个方法进行设置
     * 中间弹窗和底部弹窗共用
     *
     * @param list     数据源，List，String类型
     * @param colors   颜色数据源，List，Integer类型
     * @param listener item的监听事件
     * @return this
     */
    public LibDialog setListView(List<String> list, List<Integer> colors, AdapterView.OnItemClickListener listener) {
        mIsShowListView = true;
        mItemClickListener = listener;
        mDataList = new ArrayList<>();
        for (String str : list) {
            mDataList.add(new LibBean(str));
        }
        mAdapter = new LibDialogAdapter(mContext, R.layout.string_item_layout, mDataList, colors);
        return this;
    }

    /**
     * 底部弹窗，重新设置内容的显示控件
     *
     * @param v 要显示的控件
     * @return this
     */
    public LibDialog setBottomView(View v) {
        ((LinearLayout) mDialogLayout.findViewById(R.id.list_content)).removeAllViews();
        ViewGroup parent = (ViewGroup) v.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        if (getMsgListViewHeight((ListView) v) > height / 5 * 3){
            //如果List的高度大于屏幕高度的4/5
            LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,height / 5 * 3);
            mDialogLayout.findViewById(R.id.list_content).setLayoutParams(LayoutParams);
        }
        ((LinearLayout) mDialogLayout.findViewById(R.id.list_content)).addView(v);
        return this;
    }

    /**
     * 底部弹窗，设置标题
     *
     * @param title 整型标题
     * @return this
     */
    public LibDialog setBottomTitle(int title) {
        mIsShowBottomTitle = true;
        ((TextView) mDialogLayout.findViewById(R.id.bottom_title)).setText(title);
        return this;
    }

    /**
     * 底部弹窗，设置标题
     *
     * @param title String型标题
     * @return this
     */
    public LibDialog setBottomTitle(String title) {
        mIsShowBottomTitle = true;
        ((TextView) mDialogLayout.findViewById(R.id.bottom_title)).setText(title);
        return this;
    }

    /**
     * 底部弹窗，设置标题文本的颜色
     *
     * @param color 文本颜色
     * @return this
     */
    public LibDialog setBottomTitleColor(int color) {
        ((TextView) mDialogLayout.findViewById(R.id.bottom_title)).setTextColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 设置底部弹窗标题文本的文字大小
     *
     * @param size 文字大小
     * @return this
     */
    public LibDialog setBottomTitleSize(int size){
        ((TextView) mDialogLayout.findViewById(R.id.bottom_title)).setTextSize(size);
        return this;
    }

    /**
     * 底部弹窗，设置标题区域的背景色
     *
     * @param color 背景色
     * @return this
     */
    public LibDialog setBottomTitleBackground(int color) {
        mIsShowBottomTitle = true;
        mDialogLayout.findViewById(R.id.bottom_title_content).setBackgroundColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 底部弹窗，设置Negative的文本和点击事件，点击事件可为null
     *
     * @param negativeText 文本，如"取消"
     * @param listener     negative区域的监听事件
     * @return this
     */
    public LibDialog setBottomNegativeButton(String negativeText, View.OnClickListener listener) {
        mIsShowBottomNegativeButton = true;
        ((TextView) mDialogLayout.findViewById(R.id.bottom_negative)).setText(negativeText);
        this.negativeButton = listener;
        return this;
    }

    /**
     * 底部弹窗，设置negative文本的颜色
     *
     * @param color 文本颜色
     * @return this
     */
    public LibDialog setBottomNegativeButtonColor(int color) {
        ((TextView) mDialogLayout.findViewById(R.id.bottom_negative)).setTextColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 底部弹窗，设置negative文本的字体大小
     *
     * @param size 文本大小
     * @return this
     */
    public LibDialog setBottomNegativeButtonSize(int size){
        ((TextView) mDialogLayout.findViewById(R.id.bottom_negative)).setTextSize(size);
        return this;
    }

    /**
     * 设置底部弹窗negative文本的字体为粗体
     *
     * @return this
     */
    public LibDialog setBottomNegativeButtomStyleBold(){
        TextView tv = (TextView) mDialogLayout.findViewById(R.id.bottom_negative);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        return this;
    }

    /**
     * 设置底部弹窗negative区域的背景色
     *
     * @param color 背景色
     * @return this
     */
    public LibDialog setBottomNegativeButtonBackground(int color) {
        mDialogLayout.findViewById(R.id.bottom_negative_content).setBackgroundColor(
                mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 获取List的总高度
     * @param mMessageCenterLv ListView
     * @return this
     */
    private int getMsgListViewHeight(ListView mMessageCenterLv) {
        //ListView总高度
        int totalHeight = 0;
        ListAdapter listAdapter = mMessageCenterLv.getAdapter();
        if (listAdapter == null) {
            return totalHeight;
        }
        int height = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mMessageCenterLv);
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mMessageCenterLv.getWidth(), View.MeasureSpec.AT_MOST);
            listItem.measure(desiredWidth, 0);
            height += (listItem.getMeasuredHeight());
            Log.d(TAG, "每项item的高度："+listItem.getMeasuredHeight());
        }
        totalHeight = height + (mMessageCenterLv.getDividerHeight() * (listAdapter.getCount() - 1));
        return totalHeight;
    }

    /**
     * 构建窗体，所有链式调用都在这里进行集中整理
     *
     * @return 构建完毕的窗体
     */
    public Dialog builder() {
        dialog = new Dialog(mContext, R.style.MyDialogTheme);
        dialog.setCancelable(mCancelable);
        dialog.addContentView(mDialogLayout, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        //如果是中间弹窗
        if (!mIsBottomDialog) {
            //如果没有设置Title
            if (!mIsShowTitle) {
                mDialogLayout.findViewById(R.id.title_background).setVisibility(View.GONE);
            }
            //如果设置显示了ListView
            if (mIsShowListView) {
                ListView listView = new ListView(mContext);
                LinearLayout.LayoutParams listLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                listView.setLayoutParams(listLayoutParams);
                listView.setAdapter(mAdapter);
                int list_height = getMsgListViewHeight(listView);   //获取ListView的高度

                Log.v(TAG, "List的总高度为："+list_height);
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                int height = wm.getDefaultDisplay().getHeight();
                Log.d(TAG, "屏幕高度：" + height);
                if (list_height > height*3/5) {
                    list_height = height*3/5;
                }
                LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, list_height);
                ((ScrollView) mDialogLayout.findViewById(R.id.sv)).setLayoutParams(LayoutParams);

                setView(listView);
                if (mItemClickListener != null) {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mItemClickListener.onItemClick(parent, view, position, id);
                            dialog.dismiss();
                        }
                    });
                } else {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                        }
                    });
                }
            }
            //如果设置了negative区域的按钮
            if (mIsShowNegativeButton) {
                if (negativeButton != null) {
                    mDialogLayout.findViewById(R.id.negative).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButton.onClick(v);
                            dialog.dismiss();
                        }
                    });
                } else {
                    mDialogLayout.findViewById(R.id.negative).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                mDialogLayout.findViewById(R.id.negative).setVisibility(View.GONE);
                mDialogLayout.findViewById(R.id.line3).setVisibility(View.GONE);
            }
            //如果设置了positive区域的按钮
            if (mIsShowPositiveButton) {
                if (positiveButton != null) {
                    mDialogLayout.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            positiveButton.onClick(v);
                            dialog.dismiss();
                        }
                    });
                } else {
                    mDialogLayout.findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                mDialogLayout.findViewById(R.id.positive).setVisibility(View.GONE);
                mDialogLayout.findViewById(R.id.line3).setVisibility(View.GONE);
            }
            mDialogLayout.findViewById(R.id.negative).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (negativeButton != null) {
                        negativeButton.onClick(v);
                    }
                    dialog.dismiss();
                }
            });
            //如果有自定义的动画效果传入，就显示传入的动画效果，否则显示默认效果，另外：传入0，无动画
            if (mIsHaveCustomAnim) {
                if (mCustomAnim != 0) { //设置显示dialog的显示动画
                    dialog.getWindow().setWindowAnimations(mCustomAnim);
                }
            } else {                    //设置默认dialog的显示动画
                dialog.getWindow().setWindowAnimations(R.style.DialogInAndOutAnim);
            }
        } else { //是底部弹窗
            //如果没有设置底部弹窗标题
            if (mIsShowBottomTitle) {
                mDialogLayout.findViewById(R.id.bottom_title_content).setVisibility(View.GONE);
            }
            //如果设置了显示ListView
            if (mIsShowListView) {
                ListView listView = new ListView(mContext);
                LinearLayout.LayoutParams listLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                listView.setLayoutParams(listLayoutParams);
                listView.setAdapter(mAdapter);
                setBottomView(listView);
                if (mItemClickListener != null) {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mItemClickListener.onItemClick(parent, view, position, id);
                            dialog.dismiss();
                        }
                    });
                } else {
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                        }
                    });
                }
            }
            //如果设置了显示底部Negative按钮
            if (mIsShowBottomNegativeButton) {
                if (negativeButton != null) {
                    mDialogLayout.findViewById(R.id.bottom_negative_content).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            negativeButton.onClick(v);
                            dialog.dismiss();
                        }
                    });
                } else {
                    mDialogLayout.findViewById(R.id.bottom_negative_content).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            } else {
                mDialogLayout.findViewById(R.id.bottom_negative_content).setVisibility(View.GONE);
            }
            //如果有自定义的动画效果传入，就显示传入的动画效果，否则显示默认效果，另外：传入0，无动画
            if (mIsHaveCustomAnim) {
                if (mCustomAnim != 0) { //设置显示底部dialog的显示动画
                    dialog.getWindow().setWindowAnimations(mCustomAnim);
                }
            } else {                    //设置默认底部dialog的显示动画
                dialog.getWindow().setWindowAnimations(R.style.BottomDialogInAndOutAnim);
            }
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
        }

        return dialog;
    }
}
