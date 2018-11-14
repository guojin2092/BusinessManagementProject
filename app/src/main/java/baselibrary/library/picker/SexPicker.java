package baselibrary.library.picker;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.africa.crm.businessmanagementproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 性别选择器
 */

public class SexPicker implements PopupWindow.OnDismissListener, View.OnClickListener {

    private OnCitySelectListener mOnCitySelectListener;

    private PopupWindow mPickerWindow;

    private View mParent;

    private WheelRecyclerView mProvinceWheel;

    private Activity mContext;

    private List<TimeBean> sexDatas;

    public SexPicker(Activity context, View parent) {
        mContext = context;
        mParent = parent;
        //初始化选择器
        View pickerView = LayoutInflater.from(context).inflate(R.layout.sex_picker, null);
        mProvinceWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_province);
        pickerView.findViewById(R.id.tv_exit).setOnClickListener(this);
        pickerView.findViewById(R.id.tv_ok).setOnClickListener(this);

        mPickerWindow = new PopupWindow(pickerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPickerWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        mPickerWindow.setFocusable(true);
        mPickerWindow.setAnimationStyle(R.style.CityPickerAnim);
        mPickerWindow.setOnDismissListener(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mProvinceWheel.setData(getProvinceNames());
    }

    /**
     * 获取性别名称列表
     *
     * @return
     */
    private List<TimeBean> getProvinceNames() {
        sexDatas = new ArrayList<>();
//        String[] data = mContext.getResources().getStringArray(R.array.sex_selector);
//        for (String str : data) {
//            sexDatas.add(str);
//        }
        TimeBean timeBean = new TimeBean();
        timeBean.time = "男";
        timeBean.position = 0;
        sexDatas.add(timeBean);

        TimeBean timeBean2 = new TimeBean();
        timeBean2.time = "女";
        timeBean2.position = 1;
        sexDatas.add(timeBean2);

        return sexDatas;
    }

    /**
     * 弹出Window时使背景变暗
     *
     * @param alpha
     */
    private void backgroundAlpha(float alpha) {
        Window window = mContext.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);
    }

    public SexPicker setOnCitySelectListener(OnCitySelectListener listener) {
        mOnCitySelectListener = listener;
        return this;
    }

    public void show() {
        backgroundAlpha(0.5f);
        mPickerWindow.showAtLocation(mParent, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                if (mOnCitySelectListener != null) {
                    String mSex = sexDatas.get(mProvinceWheel.getSelected()).time;
                    mOnCitySelectListener.onCitySelect(mSex);
                    mPickerWindow.dismiss();
                }
                break;
            case R.id.tv_exit:
                mPickerWindow.dismiss();
                break;
        }
    }

    public interface OnCitySelectListener {
        void onCitySelect(String sex);
    }
}
