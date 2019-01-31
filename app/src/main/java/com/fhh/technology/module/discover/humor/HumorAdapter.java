package com.fhh.technology.module.discover.humor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhh.technology.R;
import com.fhh.technology.network.bean.HumorBean;

import java.util.List;

/**
 * desc:humor笑话的adapter
 * Created by fanhenghao
 */
public class HumorAdapter extends RecyclerView.Adapter<HumorAdapter.HumorViewHolder> {

    private static final int NORMAL_TYPE = 1;
    private static final int LOAD_MORE_TYPE = 2;
    private static final int ITEM_COUNT = 20;//刷新一次加载20条数据

    private Context mContext;
    private List<HumorBean.DataBean> mHumorBeans;
    private int view_type = NORMAL_TYPE;
    private boolean mLoadMoreError = false;
    private onClickListener mListener;

    public void setListener(onClickListener listener) {
        mListener = listener;
    }

    public interface onClickListener {
        void onClick(int position, boolean reLoad);
    }

    public HumorAdapter(Context context, List<HumorBean.DataBean> humorBeans) {
        this.mContext = context;
        this.mHumorBeans = humorBeans;
    }

    @NonNull
    @Override
    public HumorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMAL_TYPE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_humor_normal, parent, false);
            view_type = NORMAL_TYPE;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_humor_load_more, parent, false);
            view_type = LOAD_MORE_TYPE;
        }
        return new HumorViewHolder(view, view_type);
    }

    @Override
    public void onBindViewHolder(@NonNull final HumorViewHolder holder, final int position) {
        if (getItemViewType(position) == LOAD_MORE_TYPE) {
            //加载更多
            if (mLoadMoreError) {
                holder.mRlMore.setVisibility(View.GONE);
                holder.mRlFail.setVisibility(View.VISIBLE);
            } else {
                holder.mRlMore.setVisibility(View.VISIBLE);
                holder.mRlFail.setVisibility(View.GONE);
            }
        } else {
            //普通条目
            holder.mTvContent.setText(mHumorBeans.get(position).getText());
        }
        holder.itemView.setOnClickListener(v -> {
            if (mHumorBeans.size() == position) {
                if (mListener != null && holder.mRlFail.getVisibility() == View.VISIBLE) {
                    mListener.onClick(position, true);
                }
            } else {
                if (mListener != null) {
                    mListener.onClick(position, false);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mHumorBeans.size()) {
            view_type = LOAD_MORE_TYPE;
        } else {
            view_type = NORMAL_TYPE;
        }
        return view_type;
    }

    public void setNormalRefresh(int type, List<HumorBean.DataBean> beans) {
        mLoadMoreError = false;
        mHumorBeans.addAll(0, beans);
        view_type = type;
        notifyDataSetChanged();
    }

    public void setPullDown(int type, List<HumorBean.DataBean> beans) {
        mLoadMoreError = false;
        mHumorBeans.clear();
        mHumorBeans.addAll(beans);
        view_type = type;
        notifyDataSetChanged();
    }

    public void setLoadMore(int type, List<HumorBean.DataBean> beans) {
        mLoadMoreError = false;
        mHumorBeans.addAll(beans);
        view_type = type;
        notifyDataSetChanged();
    }

    public void loadMoreError(int type, boolean error) {
        mLoadMoreError = error;
        view_type = type;
        notifyDataSetChanged();
    }

    public void startLoad(){
        mLoadMoreError = false;
        notifyItemChanged(mHumorBeans.size());
    }

    @Override
    public int getItemCount() {
        if (mHumorBeans.size() < ITEM_COUNT && mHumorBeans.size() > 0) {
            return mHumorBeans.size();
        } else if (mHumorBeans.size() >= ITEM_COUNT) {
            return mHumorBeans.size() + 1;
        }
        return 0;
    }

    class HumorViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvContent;
        private RelativeLayout mRlMore;
        private RelativeLayout mRlFail;

        public HumorViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == NORMAL_TYPE) {
                //普通view布局
                mTvContent = itemView.findViewById(R.id.tv_content);
            } else {
                //加载更多布局
                mRlMore = itemView.findViewById(R.id.rl_more);
                mRlFail = itemView.findViewById(R.id.rl_fail);
            }
        }
    }
}
