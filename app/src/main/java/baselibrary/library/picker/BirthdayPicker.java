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


import com.africa.crm.businessmanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市选择器
 * Created by CrazyPumPkin on 2016/12/5.
 */

public class BirthdayPicker implements PopupWindow.OnDismissListener, View.OnClickListener {

    private OnCitySelectListener mOnCitySelectListener;

    private PopupWindow mPickerWindow;

    private View mParent;

    private WheelRecyclerView mProvinceWheel;

    private WheelRecyclerView mCityWheel;

    private WheelRecyclerView mEndTimeWheel;

    private Activity mContext;

//    private List<Province> mDatas;

    private List<TimeBean> yearListDatas;

    private List<TimeBean> conListDatas;

    private List<TimeBean> endTimeListDatas;

    public BirthdayPicker(Activity context, View parent) {
        mContext = context;
        mParent = parent;
        //初始化选择器
        View pickerView = LayoutInflater.from(context).inflate(R.layout.birthday_picker, null);
        mProvinceWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_province);
        mCityWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_city);
        mEndTimeWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_end_time);
//        mCountyWheel = (WheelRecyclerView) pickerView.findViewById(R.id.wheel_county);
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
     * 初始化城市数据
     */
    private void initData() {
        /*mDatas = new Gson().fromJson(Util.getTextFromAssets(mContext, "city.json"), new TypeToken<List<Province>>() {
        }.getType());*/

        mProvinceWheel.setData(getProvinceNames());
        mCityWheel.setData(getCityNames());
        mEndTimeWheel.setData(getEndTimeListDatas());
//        mCountyWheel.setData(getCountyNames(0, 0));

        mProvinceWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position, TimeBean data) {
                onProvinceWheelRoll(position);
            }
        });
        mCityWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position, TimeBean data) {
                onCityWheelRoll(position);
            }
        });

    }

    private void onProvinceWheelRoll(int position) {
        mCityWheel.setData(getCityNames());
//        mCountyWheel.setData(getCountyNames(position, 0));
    }

    private void onCityWheelRoll(int position) {
        mEndTimeWheel.setData(getEndTimeListDatas());
//        mCountyWheel.setData(getCountyNames(mProvinceWheel.getSelected(), position));
    }

    /**
     * 获取省份名称列表
     *
     * @return
     */
    private List<TimeBean> getProvinceNames() {

        yearListDatas = new ArrayList<>();
        TimeBean timeBean = new TimeBean();
        timeBean.time = "星期一";
        timeBean.position = 0;
        yearListDatas.add(timeBean);

        TimeBean timeBean2 = new TimeBean();
        timeBean2.time = "星期二";
        timeBean2.position = 1;
        yearListDatas.add(timeBean2);

        TimeBean timeBean3 = new TimeBean();
        timeBean3.time = "星期三";
        timeBean3.position = 2;
        yearListDatas.add(timeBean3);

        TimeBean timeBean4 = new TimeBean();
        timeBean4.time = "星期四";
        timeBean4.position = 3;
        yearListDatas.add(timeBean4);

        TimeBean timeBean5 = new TimeBean();
        timeBean5.time = "星期五";
        timeBean5.position = 4;
        yearListDatas.add(timeBean5);

        TimeBean timeBean6 = new TimeBean();
        timeBean6.time = "星期六";
        timeBean6.position = 5;
        yearListDatas.add(timeBean6);

        TimeBean timeBean7 = new TimeBean();
        timeBean7.time = "星期日";
        timeBean7.position = 6;
        yearListDatas.add(timeBean7);

        return yearListDatas;
    }

    /**
     * 获取某个省份的城市名称列表
     *
     * @return
     */
    private List<TimeBean> getCityNames() {
        conListDatas = new ArrayList<>();

        TimeBean timeBean = new TimeBean();
        timeBean.position = 0;
        timeBean.time = "上午 8:00";
        conListDatas.add(timeBean);

        TimeBean timeBean2 = new TimeBean();
        timeBean2.position = 1;
        timeBean2.time = "上午 9:00";
        conListDatas.add(timeBean2);

        TimeBean timeBean3 = new TimeBean();
        timeBean3.position = 2;
        timeBean3.time = "上午 10:00";
        conListDatas.add(timeBean3);

        TimeBean timeBean4 = new TimeBean();
        timeBean4.position = 3;
        timeBean4.time = "上午 11:00";
        conListDatas.add(timeBean4);

        TimeBean timeBean5 = new TimeBean();
        timeBean5.position = 4;
        timeBean5.time = "中午 12:00";
        conListDatas.add(timeBean5);

        TimeBean timeBean6 = new TimeBean();
        timeBean6.position = 5;
        timeBean6.time = "下午 1:00";
        conListDatas.add(timeBean6);

        TimeBean timeBean7 = new TimeBean();
        timeBean7.position = 6;
        timeBean7.time = "下午 2:00";
        conListDatas.add(timeBean7);

        TimeBean timeBean8 = new TimeBean();
        timeBean8.position = 7;
        timeBean8.time = "下午 3:00";
        conListDatas.add(timeBean8);

        TimeBean timeBean9 = new TimeBean();
        timeBean9.position = 8;
        timeBean9.time = "下午 4:00";
        conListDatas.add(timeBean9);

        TimeBean timeBean10 = new TimeBean();
        timeBean10.position = 9;
        timeBean10.time = "下午 5:00";
        conListDatas.add(timeBean10);

        TimeBean timeBean11 = new TimeBean();
        timeBean11.position = 10;
        timeBean11.time = "下午 6:00";
        conListDatas.add(timeBean11);

        TimeBean timeBean12 = new TimeBean();
        timeBean12.position = 11;
        timeBean12.time = "下午 7:00";
        conListDatas.add(timeBean12);

        TimeBean timeBean13 = new TimeBean();
        timeBean13.position = 12;
        timeBean13.time = "下午 8:00";
        conListDatas.add(timeBean13);

        return conListDatas;
    }

    private List<TimeBean> getEndTimeListDatas() {
        endTimeListDatas = new ArrayList<>();

        TimeBean timeBean = new TimeBean();
        timeBean.position = 0;
        timeBean.time = "上午 8:00";
        endTimeListDatas.add(timeBean);

        TimeBean timeBean2 = new TimeBean();
        timeBean2.position = 1;
        timeBean2.time = "上午 9:00";
        endTimeListDatas.add(timeBean2);

        TimeBean timeBean3 = new TimeBean();
        timeBean3.position = 2;
        timeBean3.time = "上午 10:00";
        endTimeListDatas.add(timeBean3);

        TimeBean timeBean4 = new TimeBean();
        timeBean4.position = 3;
        timeBean4.time = "上午 11:00";
        endTimeListDatas.add(timeBean4);

        TimeBean timeBean5 = new TimeBean();
        timeBean5.position = 4;
        timeBean5.time = "中午 12:00";
        endTimeListDatas.add(timeBean5);

        TimeBean timeBean6 = new TimeBean();
        timeBean6.position = 5;
        timeBean6.time = "下午 1:00";
        endTimeListDatas.add(timeBean6);

        TimeBean timeBean7 = new TimeBean();
        timeBean7.position = 6;
        timeBean7.time = "下午 2:00";
        endTimeListDatas.add(timeBean7);

        TimeBean timeBean8 = new TimeBean();
        timeBean8.position = 7;
        timeBean8.time = "下午 3:00";
        endTimeListDatas.add(timeBean8);

        TimeBean timeBean9 = new TimeBean();
        timeBean9.position = 8;
        timeBean9.time = "下午 4:00";
        endTimeListDatas.add(timeBean9);

        TimeBean timeBean10 = new TimeBean();
        timeBean10.position = 9;
        timeBean10.time = "下午 5:00";
        endTimeListDatas.add(timeBean10);

        TimeBean timeBean11 = new TimeBean();
        timeBean11.position = 10;
        timeBean11.time = "下午 6:00";
        endTimeListDatas.add(timeBean11);

        TimeBean timeBean12 = new TimeBean();
        timeBean12.position = 11;
        timeBean12.time = "下午 7:00";
        endTimeListDatas.add(timeBean12);

        TimeBean timeBean13 = new TimeBean();
        timeBean13.position = 12;
        timeBean13.time = "下午 8:00";
        endTimeListDatas.add(timeBean13);

        return endTimeListDatas;
    }

    /**
     * 获取某个城市的县级区域名称列表
     *
     * @param provincePos
     * @param cityPos
     * @return
     */
    private List<String> getCountyNames(int provincePos, int cityPos) {
        List<String> counties = new ArrayList<>();
      /*  if (mDatas.get(provincePos).getCities().size() > 0) {
            for (County county : mDatas.get(provincePos).getCities().get(cityPos).getCounties()) {
                counties.add(county.getAreaName());
            }
        }*/
        return counties;
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

    public BirthdayPicker setOnCitySelectListener(OnCitySelectListener listener) {
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
//                    Province province = mDatas.get(mProvinceWheel.getSelected());
                    TimeBean mAge_step = yearListDatas.get(mProvinceWheel.getSelected());
                    TimeBean mConstellation = conListDatas.get(mCityWheel.getSelected());
                    TimeBean mEndTime = endTimeListDatas.get(mEndTimeWheel.getSelected());
                /*    City city = province.getCities().size() > 0 ? province.getCities().get(mCityWheel.getSelected()) : null;
                    String provinceName = province.getAreaName();
                    String cityName = city == null ? "" : city.getAreaName();*/
//                    String countyName = city == null ? "" : city.getCounties().get(mCountyWheel.getSelected()).getAreaName();
                    mOnCitySelectListener.onCitySelect(mAge_step, mConstellation, mEndTime, mPickerWindow);
                }
                break;
            case R.id.tv_exit:
                mPickerWindow.dismiss();
                break;
        }
    }

    public interface OnCitySelectListener {
        void onCitySelect(TimeBean age_step, TimeBean constellation, TimeBean endTime, PopupWindow mPickerWindow);
    }
}
