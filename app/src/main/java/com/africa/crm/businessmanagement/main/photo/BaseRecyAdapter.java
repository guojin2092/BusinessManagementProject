package com.africa.crm.businessmanagement.main.photo;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * 作者: 51hs_android
 * 时间: 2017/3/7
 * 简介:
 */

public abstract class BaseRecyAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    protected List<T> list;

    private OnItemClickListener<T> tOnItemClickListener;

    public void settOnItemClickListener(OnItemClickListener<T> tOnItemClickListener) {
        this.tOnItemClickListener = tOnItemClickListener;
    }

    public BaseRecyAdapter(List<T> list) {
        this.list = list;
    }




    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }


    public void addOneData(int position, T t) {
        list.add(position, t);
        notifyItemInserted(position);
    }

    public void addAllData(List<T> tList) {
        list.addAll(tList);
        notifyDataSetChanged();
    }

    public void refreshAll(List<T> tList) {
        list.clear();
        list.addAll(tList);
        notifyDataSetChanged();
    }


    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (tOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tOnItemClickListener.itemClick(position, list.get(position));

                }
            });
        }
        onBind(holder, position);

    }

    protected abstract void onBind(RecyclerView.ViewHolder holder, int position);



    public interface OnItemClickListener<T> {

        void itemClick(int position, T t);

    }

}
