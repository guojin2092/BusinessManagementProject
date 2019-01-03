package com.africa.crm.businessmanagement.main.photo;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.africa.crm.businessmanagement.R;

import java.util.List;

/**
 * 作者: 51hs_android
 * 时间: 2017/3/21
 * 简介:
 */

public class SinglePopup extends BasePopup {

    private TextView tv_title;
    private List<String> content;
    private OnPopItemClickListener popItemClickListener;


    public SinglePopup(Context context, List<String> content, OnPopItemClickListener popItemClickListener) {
        super(context);
        this.content=content;
        this.popItemClickListener = popItemClickListener;
        View popView = LayoutInflater.from(context).inflate(R.layout.popup_select, null, false);
        setContentView(popView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        RecyclerView rv = (RecyclerView) contentView.findViewById(R.id.rv);

        Adapter adapter=new Adapter(content);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        rv.addItemDecoration(new DividerItemDecoration(contentView.getContext(),DividerItemDecoration.VERTICAL));
            adapter.settOnItemClickListener(new BaseRecyAdapter.OnItemClickListener<String>() {
                @Override
                public void itemClick(int position, String s) {
                    popItemClickListener.itemClick(position,s);
                    dismiss();
                }
            });
    }

    public void setTitle(int visibility,String title){
        tv_title.setVisibility(visibility);
        tv_title.setText(title);
    }




   public interface OnPopItemClickListener{
        void itemClick(int position, String s);
    }




    class Adapter extends BaseRecyAdapter<String> {

        public Adapter(List<String> list) {
            super(list);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyadapter_select_popop, parent, false));
        }

        @Override
        protected void onBind(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder)holder).tv.setText(list.get(position));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }


    }

}
