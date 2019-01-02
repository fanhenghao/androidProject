package com.fhh.technology.module.discover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.http.bean.DiscoverDataBean;


/**
 * desc:
 * Created by fhh on 2018/10/14
 */

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverAdapter.DiscoverViewHolder> {

    private Context mContext;
    private DiscoverDataBean mDataBean;
    private OnListener mListener;

    public interface OnListener {
        void onClick(int position);
    }

    DiscoverAdapter(Context context, DiscoverDataBean bean) {
        mContext = context;
        mDataBean = bean;
    }

    public void setListener(OnListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.discover_item, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, final int position) {
        holder.mTvTitle.setText(mDataBean.getItemData().get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBean == null ? 0 : mDataBean.getItemData().size();
    }

    class DiscoverViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;

        public DiscoverViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
