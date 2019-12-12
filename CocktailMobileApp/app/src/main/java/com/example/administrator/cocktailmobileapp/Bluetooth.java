package com.example.administrator.cocktailmobileapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Bluetooth extends Thread {
    private static final String TAG = "Bluetooth";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    //UUID
    private static final String MAC_ADDRESS = "AB:03:67:78:2B:0B";  //아두이노 맥주소

    private static Bluetooth instance;

    public static Bluetooth getInstance() {
        if (instance == null) {
            instance = new Bluetooth();
        }

        return instance;
    }

    public interface BluetoothCall {
        void onMaking();
        void onComplete();
    }

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    private BluetoothCall mBluetoothCall;

    public Bluetooth () {
//        try {
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS);
//            mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);
//
//            mBluetoothSocket.connect();
//
//            mOutputStream = mBluetoothSocket.getOutputStream();
//            mInputStream = mBluetoothSocket.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mBluetoothCall.onMaking();
        }

//        try {
//            byte[] buffer = new byte[1024];
//
//            while (true) {
//                    Thread.sleep(300);
//
//                    mBluetoothCall.onMaking();
//
//                    if (mInputStream.read(buffer) > 0) {
//                        mBluetoothCall.onComplete();
//                        break;
//                    }
//            }
//
//            wait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void writeData (String data) {
        try {
            mOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBluetoothCall(BluetoothCall bluetoothCall) {
        this.mBluetoothCall = bluetoothCall;
    }
}
