package com.africa.crm.businessmanagement.main.print;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.UUID;

public class PrintDataService {
    private BluetoothDevice device = null;
    private static BluetoothSocket bluetoothSocket = null;
    private static OutputStream outputStream = null;
    private static PrintWriter writer = null;
    private static String encoding = "GBK";//此编码格式可适应中文、英文、法文3种语言
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public PrintDataService(BluetoothDevice device) {
        super();
        this.device = device;
    }

    public boolean connect(){
        try {
            if(null != device ){
                bluetoothSocket = this.device.createRfcommSocketToServiceRecord(uuid);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                //设置打印机复位指令
                outputStream.write(PrintUtils.RESET);
                outputStream.flush();
                writer = new PrintWriter(new OutputStreamWriter(outputStream,encoding));
                return true;
            }
        } catch (IOException e) {
            Log.e("CONNECT","发送失败！");
        }
        return false;
    }

    /**
     * 设置指令
     * @param commond
     */
    public void setCommond(byte[] commond) {
        writer.write(commond[0]);
        writer.write(commond[1]);
        if(commond.length>2){
            writer.write(commond[2]);
        }
        writer.flush();
    }

    /**
     * 打印一行
     * @param sendData
     */
    public void println(String sendData) {
        Log.e("PRINT",sendData);
        writer.println(sendData);
        writer.flush();
    }

    /**
     * 打印
     * @param sendData
     */
    public void print(String sendData) {
        Log.e("PRINT",sendData);
        writer.write(sendData);
        writer.flush();
    }

    /**
     * 关闭流
     */
    public void closeIOAndSocket(){
        if(null != writer){
            writer.close();
        }
        if(null != outputStream){
            try{
                outputStream.close();
            } catch (IOException e) {
                Log.e("CLOSE","关闭流发生错误！");
            }
        }
    }
}
