package com.africa.crm.businessmanagement.baseutil.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * adapter基类
 * 
 * @author yzx
 * 
 * @param <E>
 */
public abstract class BaseAdatperImpl<E> extends BaseAdapter {
	private ArrayList<E> list;

	public BaseAdatperImpl(ArrayList<E> paramArrayList) {
		this.list = paramArrayList;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int paramInt) {
		return this.list.get(paramInt);
	}

	public abstract View getItemView(int paramInt, View paramView,
			ViewGroup paramViewGroup);

	public ArrayList<E> getList() {
		return this.list;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		if (paramView == null) {
			paramView = ((LayoutInflater) paramViewGroup.getContext()
					.getSystemService("layout_inflater")).inflate(
					(int) getItemId(paramInt), null);
		}
		return getItemView(paramInt, paramView, paramViewGroup);
	}

	public void setList(ArrayList<E> paramArrayList) {
		this.list = paramArrayList;
	}
}