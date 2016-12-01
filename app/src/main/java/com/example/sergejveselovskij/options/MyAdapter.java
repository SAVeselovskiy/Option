package com.example.sergejveselovskij.options;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by sergejveselovskij on 01.12.16.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LinkedList<String> items = new LinkedList<>();
    class ViewHolder0 extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder0(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.step_text);
        }
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.first_view_holder, parent, false);
        return new ViewHolder0(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder0 viewHolder0 = (ViewHolder0) holder;
        viewHolder0.textView.setText(items.get(position));
    }

    public void addItem(String item){
        items.add(item);
        notifyDataSetChanged();
    }
}
