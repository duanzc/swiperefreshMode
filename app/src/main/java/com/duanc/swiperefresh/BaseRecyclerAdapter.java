package com.duanc.swiperefresh;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * 作者: 司海林
 * 时间: 16/2/22
 * 邮箱: 371184359@qq.com
 * 描述:RecyclerView通用适配器
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    protected List<T> mData;
    protected Context mContext;
    protected boolean mUseAnimation;
    private RecyclerView.LayoutManager mLayoutManager;
    protected LayoutInflater mInflater;
//    protected OnItemClickListener mClickListener;


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;

    public static final String TAG = "duanc";

    protected boolean mShowFooter;

    public BaseRecyclerAdapter(Context context, List<T> data) {
        this(context, data, false);
    }

    public BaseRecyclerAdapter(Context context, List<T> data, boolean useAnimation) {
        this(context, data, useAnimation, null);
    }

    public BaseRecyclerAdapter(Context context, List<T> data, boolean useAnimation, RecyclerView.LayoutManager layoutManager) {
        mContext = context;
        mUseAnimation = useAnimation;
        mLayoutManager = layoutManager;
        mData = data == null ? new ArrayList<T>() : data;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(mContext,
                mInflater.inflate(getItemLayoutId(viewType), parent, false));
//        if (mClickListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mClickListener.onItemClick(v, holder.getLayoutPosition());
//                }
//            });
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (mLayoutManager != null) {
                if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    if (((StaggeredGridLayoutManager) mLayoutManager).getSpanCount() != 1) {
                        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView
                                .getLayoutParams();
                        params.setFullSpan(true);
                    }
                } else if (mLayoutManager instanceof GridLayoutManager) {
                    if (((GridLayoutManager) mLayoutManager)
                            .getSpanCount() != 1 && ((GridLayoutManager) mLayoutManager)
                            .getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                        throw new RuntimeException("网格布局列数大于1时应该继承SpanSizeLookup时处理底部加载时布局占满一行。");
                    }
                }
            }
        } else {
            bindData(holder, position, mData.get(position));
            if (mUseAnimation) {
                setAnimation(holder.itemView, position);
            }
        }
    }

    private int lastPosition = -1;

    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(viewToAnimate.getContext(), R.anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (mUseAnimation && holder.itemView.getAnimation() != null && holder.itemView
                .getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void addMoreData(List<T> data) {
        int startPos = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if (mShowFooter && getItemCount() - 1 == position) {
            return TYPE_FOOTER;
        }
        return bindViewType(position);
    }

    @Override
    public int getItemCount() {
        int i = mShowFooter ? 1 : 0;
        // KLog.e("插入: "+i);
        return mData != null ? mData.size() + i : 0;
    }

    public abstract int getItemLayoutId(int viewType);

    public abstract void bindData(BaseRecyclerViewHolder holder, int position, T item);

    protected int bindViewType(int position) {
        return 0;
    }

    public void showFooter() {
        // KLog.e("Adapter显示尾部: " + getItemCount());
        notifyItemInserted(getItemCount());
        mShowFooter = true;
    }

    public void hideFooter() {
        // KLog.e("Adapter隐藏尾部:" + (getItemCount() - 1));
        notifyItemRemoved(getItemCount() - 1);
        mShowFooter = false;
    }

}
