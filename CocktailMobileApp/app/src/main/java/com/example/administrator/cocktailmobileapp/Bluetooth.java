package com.example.administrator.cocktailmobileapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class Bluetooth extends AppCompatActivity {

    private static Bluetooth instance;

    public static Bluetooth getInstance() {
        if (instance == null) {
            instance = new Bluetooth();
        }
        return instance;
    }

    private Bluetooth() {

    }

    BluetoothAdapter btAdapter = null;
    BluetoothSocket btSocket = null;
    OutputStream outStream = null;
    InputStream inStream = null;

    // SPP UUID
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // 아두이노 맥주소
    private static String address = "AB:03:67:78:2B:0B";

    private boolean isExtracting = false;

    synchronized void setExtracting(boolean isExtracting) {
        this.isExtracting = isExtracting;
    }

    synchronized boolean getExtracting() {
        return this.isExtracting;
    }

    Thread thread;

    public void checkBTState() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        //블루투스 상태 확인
        if (btAdapter == null) {
            //기기에서 블루투스를 지원하지 않는 경우
        } else {
            //기기에서 블루투스를 지원하는 경우
            if (btAdapter.isEnabled()) {
                //블루투스가 활성화되어 있는 경우
            } else {
                //블루투스가 비활성화 되어 있는 경우
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }


    public BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //버전에 맞춰 Bluetooth 객체 생성

        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
            return (BluetoothSocket) m.invoke(device, MY_UUID);
        } catch (Exception e) {

        }

        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    public void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
        }
    }

    public void BTConnect() {
        //BluetoothDevice 객체를 아두이노 맥 주소를 이용해 아두이노와 연결
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        //BluetoothDevice 객체를 이용해 블루투스 소켓을 얻습니다.
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e1) {
        }

        //연결 시작!
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
            }
        }

        try {
            outStream = btSocket.getOutputStream();
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }

    }

    public void BTConnectCancle() {
        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) {
            }
        }

        try {
            btSocket.close();
        } catch (IOException e2) {
        }
    }
}
