package com.duanc.swiperefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2018/03/06.
 */

public class RecyclerviewAdapter extends BaseRecyclerAdapter<String> {


    public RecyclerviewAdapter(Context context, List<String> mlist) {
        super(context, mlist);
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_lalyout, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        final tvViewHolder holder = new tvViewHolder(view);
        return holder;
    }


    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.recyclerview_lalyout;
    }

    @Override
    public void bindData(BaseRecyclerViewHolder holder, int position, String item) {
        tvViewHolder holder1 = (tvViewHolder) holder;
        holder1.tv.setText(item);
    }

    public class tvViewHolder extends BaseRecyclerViewHolder {

        TextView tv;

        public tvViewHolder(View itemView) {
            super(null, itemView);
            tv = itemView.findViewById(R.id.tv_1);
        }
    }
}
