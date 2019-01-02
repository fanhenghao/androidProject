package com.my.libdialog;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class LibDialogAdapter extends ArrayAdapter<LibBean> {

    private int mResourceId;            //资源ID，布局文件
    private List<Integer> mColorsList;  //颜色list
    private boolean mIsHaveColor;       //是否传入了颜色

    private Context mContext;           //上下文

    public LibDialogAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LibBean> objects) {
        super(context, resource, objects);
        mResourceId = resource;
        mContext = context;
    }

    public LibDialogAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LibBean> objects, List<Integer> colors) {
        super(context, resource, objects);
        mResourceId = resource;
        mColorsList = colors;
        mContext = context;
        mIsHaveColor = true;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //获取当前item的实例
        LibBean libBean = getItem(position);

        View view;
        ViewHolder viewHolder;

        if (convertView == null) {    //缓存为空
            //加载布局
            view = LayoutInflater.from(getContext()).inflate(mResourceId, parent, false);
            //只有在缓存为空的情况下，才会创建ViewHolder实例
            viewHolder = new ViewHolder();
            //关联控件
            viewHolder.item = (TextView) view.findViewById(R.id.item);
            //将ViewHolder存储到view中
            view.setTag(viewHolder);
        } else {                    //缓存不为空
            view = convertView;
            //重新获取ViewHolder
            viewHolder = (ViewHolder) view.getTag();
        }

        //设置显示内容
        if (mIsHaveColor){
            viewHolder.item.setText(libBean.getItemStr());
            viewHolder.item.setTextColor(mContext.getResources().getColor(mColorsList.get(position)));
        }else{
            viewHolder.item.setText(libBean.getItemStr());
        }

        return view;
    }

    //内部类ViewHolder
    private class ViewHolder {
        TextView item;
    }

}
