package com.example.sergejveselovskij.options;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sergejveselovskij on 01.12.16.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    class ViewHolder0 extends RecyclerView.ViewHolder {
        public ViewHolder0(View itemView) {
            super(itemView);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        public ViewHolder2(View itemView) {
            super(itemView);
        }
    }

        @Override
        public int getItemViewType(int position) {
            // Just as an example, return 0 or 2 depending on position
            // Note that unlike in ListView adapters, types don't have to be contiguous
            return position % 2 * 2;
        }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_view_holder, parent, false);
                return new ViewHolder0(view);
            case 2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.second_view_holder, parent, false);
                return new ViewHolder2(view2);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                break;
        }
    }
}
