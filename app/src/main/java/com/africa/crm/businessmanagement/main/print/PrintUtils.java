package com.africa.crm.businessmanagement.main.print;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.africa.crm.businessmanagement.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;

/**
 * 打印工具类
 */
public class PrintUtils {
    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};


    /**
     * 搜索已经配对的蓝牙设备
     *
     * @return
     */
    public static BluetoothDevice getBluetoothAdapter(Activity activity) {
        Log.e("INFO", "搜索已配对的蓝牙设备");
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null) {
            if (btAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivity(intent);
                Set<BluetoothDevice> devices = btAdapter.getBondedDevices();
                if (devices.size() > 0) {
                    for (Iterator iterator = devices.iterator(); iterator.hasNext(); ) {
                        BluetoothDevice device = (BluetoothDevice) iterator.next();
                        if ("DL581printer".equals(device.getName())) {
                            return device;//连接成功
                        }
                    }
                    Log.e("INFO", "蓝牙设备连接异常");
                    return null;//蓝牙设备连接异常
                } else {
                    Log.e("INFO", "没有绑定的蓝牙设备");
                    return null;//蓝牙设备连接异常
                }

            } else {
                Log.e("INFO", "蓝牙设备未正常启动");
                return null;//蓝牙设备未正常启动
            }
        } else {
            Log.e("INFO", "无蓝牙设备");
            return null;//无蓝牙设备
        }
    }

    /**
     * 打印付款单
     *
     * @param b
     * @param companyName
     * @param customerName
     * @param code
     * @param name
     * @param payTime
     * @param price
     * @param remark
     * @return
     */
    public static String printPaymentOrder(Context context, BluetoothDevice b, String companyName, String customerName, String code, String name, String payTime, String price, String remark) {
        PrintDataService pd = new PrintDataService(b);
        boolean connectSuccess = pd.connect();
        if (connectSuccess) {
            Log.e("SEND", "开始打印！");
            pd.setCommond(ALIGN_CENTER);
            pd.setCommond(BOLD);
            pd.setCommond(DOUBLE_HEIGHT_WIDTH);
            pd.println(companyName);
            pd.setCommond(RESET);
            pd.setCommond(NORMAL);
            pd.println("");
            pd.println("--------------------------------");
            pd.println("");
            pd.println(context.getString(R.string.Payer) + customerName);
            pd.println("");
            pd.println("");
            pd.println(context.getString(R.string.Order_number) + code);
            pd.println("");
            pd.println("");
            pd.println(context.getString(R.string.Name2) + name);
            pd.println("");
            pd.println("");
            pd.println(context.getString(R.string.Payment_time) + payTime);
            pd.println("");
            pd.println("");
            pd.println(context.getString(R.string.Payment_amount) + price);
            pd.println("");
            pd.println("--------------------------------");
            pd.println("");
            pd.println(context.getString(R.string.Remarks) + remark);
            pd.println("");
            pd.println("");
            pd.println("");
            pd.println("");
            pd.closeIOAndSocket();//关闭流
            return context.getString(R.string.Print_successfully);
        } else {
            return context.getString(R.string.Printer_connection_is_abnormal);
        }
    }

    /**
     * 打印销售单
     *
     * @param b
     * @param companyName
     * @param customerName
     * @param createTime
     * @param name
     * @param price
     * @param products
     * @param remark
     * @return
     */
    public static String printSalesOrder(Context context, BluetoothDevice b, String companyName, String customerName, String createTime, String name, String price, String products, String remark) {
        PrintDataService pd = new PrintDataService(b);
        boolean connectSuccess = pd.connect();
        if (connectSuccess) {
            //处理产品信息
            String productsInfo = "";
            JSONArray productsJA = null;
            try {
                productsJA = new JSONArray(products);
            } catch (Exception e) {
                return context.getString(R.string.Product_field_passed_error);
            }
            Log.e("SEND", "开始打印！");
            pd.setCommond(ALIGN_CENTER);
            pd.setCommond(BOLD);
            pd.setCommond(DOUBLE_HEIGHT_WIDTH);
            pd.println(companyName);
            pd.setCommond(RESET);
            pd.setCommond(NORMAL);
            pd.println("");
            pd.println("--------------------------------");
            pd.println("");
            pd.println(context.getString(R.string.Payer) + customerName);
            pd.println("");
            pd.println(context.getString(R.string.Time) + createTime);
            pd.println("");
            pd.println(context.getString(R.string.Amount) + price);
            pd.println("");
            pd.println("--------------------------------");
            pd.println(context.getString(R.string.Product_Amount));//以字节计算，产品2个字占4个字节，如果是product替换产品2字，需要把中间的空格删除3个。
            pd.println("--------------------------------");
            for (int i = 0; i < productsJA.length(); i++) {
                pd.println("");
                try {
                    JSONObject jo = productsJA.getJSONObject(i);
                    String pname = jo.getString("name");
                    String pnum = jo.getString("num");
                    String ztstr = " ";
                    int pnamebyteNum = 0;
                    try {
                        pnamebyteNum = pname.getBytes("GBK").length;
                    } catch (UnsupportedEncodingException e) {

                    }
                    if (pnamebyteNum <= 18) {
                        for (int xx = 0; xx < ((18 + 2) - pnamebyteNum); xx++) {
                            ztstr += "-";
                        }
                        ztstr += " ";
                        pd.println(pname + ztstr + pnum);
                    } else {
                        pd.println(pname);
                        pd.println(" -------------------- " + pnum);
                    }

                } catch (JSONException e) {

                }

            }
            pd.println("");
            pd.println("--------------------------------");
            pd.println("");
            pd.println(context.getString(R.string.Remarks) + remark);
            pd.println("");
            pd.println("");
            pd.println("");
            pd.println("");
            pd.closeIOAndSocket();//关闭流
            return context.getString(R.string.Print_successfully);
        } else {
            return context.getString(R.string.Printer_connection_is_abnormal);
        }
    }

}
