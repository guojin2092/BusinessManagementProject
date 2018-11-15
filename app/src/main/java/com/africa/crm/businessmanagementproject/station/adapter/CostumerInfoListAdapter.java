package com.africa.crm.businessmanagementproject.station.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.station.bean.TableInfo;
import com.africa.crm.businessmanagementproject.station.bean.TableInfoBean;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import drawthink.expandablerecyclerview.adapter.BaseRecyclerViewAdapter;
import drawthink.expandablerecyclerview.bean.RecyclerViewData;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 13:56
 * Modification  History:
 * Why & What is modified:
 */
public class CostumerInfoListAdapter extends BaseRecyclerViewAdapter<String, TableInfoBean, TableViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RecyclerViewData> mDatas = new ArrayList<>();

    public CostumerInfoListAdapter(Context ctx, List<RecyclerViewData> datas) {
        super(ctx, datas);
        mContext = ctx;
        mInflater = LayoutInflater.from(ctx);
        mDatas = datas;
    }

    @Override
    public View getGroupView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_info_title, parent, false);
    }

    @Override
    public View getChildView(ViewGroup parent) {
        return mInflater.inflate(R.layout.item_info_list, parent, false);
    }

    @Override
    public TableViewHolder createRealViewHolder(Context ctx, View view, int viewType) {
        return new TableViewHolder(ctx, view, viewType);
    }

    /**
     * head View数据设置
     *
     * @param holder
     * @param groupPos
     * @param position
     * @param groupData
     */
    @Override
    public void onBindGroupHolder(TableViewHolder holder, int groupPos, int position, String groupData) {
        holder.tv_info_type.setText(groupData);
    }

    /**
     * child View数据设置
     *
     * @param holder
     * @param groupPos
     * @param childPos
     * @param position
     * @param childData
     */
    @Override
    public void onBindChildpHolder(TableViewHolder holder, int groupPos, int childPos, int position, TableInfoBean childData) {
        List<TableInfo> mTableInfoBeanList = childData.getContent();
        if (!ListUtils.isEmpty(mTableInfoBeanList)) {
            if (mTableInfoBeanList.size() == 1) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.GONE);
                holder.line2.setVisibility(View.GONE);
                holder.ll_type3.setVisibility(View.GONE);
                holder.line3.setVisibility(View.GONE);
                holder.ll_type4.setVisibility(View.GONE);
                holder.line4.setVisibility(View.GONE);
                holder.ll_type5.setVisibility(View.GONE);
                holder.line5.setVisibility(View.GONE);
                holder.ll_type6.setVisibility(View.GONE);
                holder.line6.setVisibility(View.GONE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
            } else if (mTableInfoBeanList.size() == 2) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ll_type3.setVisibility(View.GONE);
                holder.line3.setVisibility(View.GONE);
                holder.ll_type4.setVisibility(View.GONE);
                holder.line4.setVisibility(View.GONE);
                holder.ll_type5.setVisibility(View.GONE);
                holder.line5.setVisibility(View.GONE);
                holder.ll_type6.setVisibility(View.GONE);
                holder.line6.setVisibility(View.GONE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
                holder.tv_type2_title.setText(mTableInfoBeanList.get(1).getType());
                holder.tv_type2_content.setText(mTableInfoBeanList.get(1).getContent());
            } else if (mTableInfoBeanList.size() == 3) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ll_type3.setVisibility(View.VISIBLE);
                holder.line3.setVisibility(View.VISIBLE);
                holder.ll_type4.setVisibility(View.GONE);
                holder.line4.setVisibility(View.GONE);
                holder.ll_type5.setVisibility(View.GONE);
                holder.line5.setVisibility(View.GONE);
                holder.ll_type6.setVisibility(View.GONE);
                holder.line6.setVisibility(View.GONE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
                holder.tv_type2_title.setText(mTableInfoBeanList.get(1).getType());
                holder.tv_type2_content.setText(mTableInfoBeanList.get(1).getContent());
                holder.tv_type3_title.setText(mTableInfoBeanList.get(2).getType());
                holder.tv_type3_content.setText(mTableInfoBeanList.get(2).getContent());
            } else if (mTableInfoBeanList.size() == 4) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ll_type3.setVisibility(View.VISIBLE);
                holder.line3.setVisibility(View.VISIBLE);
                holder.ll_type4.setVisibility(View.VISIBLE);
                holder.line4.setVisibility(View.VISIBLE);
                holder.ll_type5.setVisibility(View.GONE);
                holder.line5.setVisibility(View.GONE);
                holder.ll_type6.setVisibility(View.GONE);
                holder.line6.setVisibility(View.GONE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
                holder.tv_type2_title.setText(mTableInfoBeanList.get(1).getType());
                holder.tv_type2_content.setText(mTableInfoBeanList.get(1).getContent());
                holder.tv_type3_title.setText(mTableInfoBeanList.get(2).getType());
                holder.tv_type3_content.setText(mTableInfoBeanList.get(2).getContent());
                holder.tv_type4_title.setText(mTableInfoBeanList.get(3).getType());
                holder.tv_type4_content.setText(mTableInfoBeanList.get(3).getContent());
            } else if (mTableInfoBeanList.size() == 5) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ll_type3.setVisibility(View.VISIBLE);
                holder.line3.setVisibility(View.VISIBLE);
                holder.ll_type4.setVisibility(View.VISIBLE);
                holder.line4.setVisibility(View.VISIBLE);
                holder.ll_type5.setVisibility(View.VISIBLE);
                holder.line5.setVisibility(View.VISIBLE);
                holder.ll_type6.setVisibility(View.GONE);
                holder.line6.setVisibility(View.GONE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
                holder.tv_type2_title.setText(mTableInfoBeanList.get(1).getType());
                holder.tv_type2_content.setText(mTableInfoBeanList.get(1).getContent());
                holder.tv_type3_title.setText(mTableInfoBeanList.get(2).getType());
                holder.tv_type3_content.setText(mTableInfoBeanList.get(2).getContent());
                holder.tv_type4_title.setText(mTableInfoBeanList.get(3).getType());
                holder.tv_type4_content.setText(mTableInfoBeanList.get(3).getContent());
                holder.tv_type5_title.setText(mTableInfoBeanList.get(4).getType());
                holder.tv_type5_content.setText(mTableInfoBeanList.get(4).getContent());
            } else if (mTableInfoBeanList.size() == 6) {
                holder.ll_type1.setVisibility(View.VISIBLE);
                holder.line1.setVisibility(View.VISIBLE);
                holder.ll_type2.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);
                holder.ll_type3.setVisibility(View.VISIBLE);
                holder.line3.setVisibility(View.VISIBLE);
                holder.ll_type4.setVisibility(View.VISIBLE);
                holder.line4.setVisibility(View.VISIBLE);
                holder.ll_type5.setVisibility(View.VISIBLE);
                holder.line5.setVisibility(View.VISIBLE);
                holder.ll_type6.setVisibility(View.VISIBLE);
                holder.line6.setVisibility(View.VISIBLE);
                holder.tv_type1_title.setText(mTableInfoBeanList.get(0).getType());
                holder.tv_type1_content.setText(mTableInfoBeanList.get(0).getContent());
                holder.tv_type2_title.setText(mTableInfoBeanList.get(1).getType());
                holder.tv_type2_content.setText(mTableInfoBeanList.get(1).getContent());
                holder.tv_type3_title.setText(mTableInfoBeanList.get(2).getType());
                holder.tv_type3_content.setText(mTableInfoBeanList.get(2).getContent());
                holder.tv_type4_title.setText(mTableInfoBeanList.get(3).getType());
                holder.tv_type4_content.setText(mTableInfoBeanList.get(3).getContent());
                holder.tv_type5_title.setText(mTableInfoBeanList.get(4).getType());
                holder.tv_type5_content.setText(mTableInfoBeanList.get(4).getContent());
                holder.tv_type6_title.setText(mTableInfoBeanList.get(5).getType());
                holder.tv_type6_content.setText(mTableInfoBeanList.get(5).getContent());
            }
        }
    }

    /**
     * true 全部可展开
     * fasle  同一时间只能展开一个
     */
    @Override
    public boolean canExpandAll() {
        return true;
    }
}
