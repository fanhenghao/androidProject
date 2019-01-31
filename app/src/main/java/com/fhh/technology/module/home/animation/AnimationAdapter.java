package com.fhh.technology.module.home.animation;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fhh.technology.R;

import java.util.List;

/**
 * desc:
 * Created by fhh on 2018/9/25
 */

public class AnimationAdapter extends RecyclerView.Adapter<AnimationAdapter.MyViewHolder> {

    private ButtonListener mListener;

    public void setListener(ButtonListener listener) {
        mListener = listener;
    }

    public interface ButtonListener {
        void onClick(int position);
    }

    private Activity mActivity;
    private List<String> mDatas;

    public AnimationAdapter(Activity activity, List<String> datas) {
        this.mActivity = activity;
        this.mDatas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_animation, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.mBtnAnimation.setText(mDatas.get(position));
        holder.mBtnAnimation.setOnClickListener(new View.OnClickListener() {
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
        return mDatas != null ? mDatas.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final Button mBtnAnimation;

        public MyViewHolder(View itemView) {
            super(itemView);
            mBtnAnimation = itemView.findViewById(R.id.btn_animation);
        }
    }
}
