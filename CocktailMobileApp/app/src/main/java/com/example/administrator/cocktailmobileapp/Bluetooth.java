package com.example.administrator.cocktailmobileapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Bluetooth {
    private static final String TAG = "Bluetooth";

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    //UUID
    private static final String MAC_ADDRESS = "AB:03:67:78:2B:0B";  //아두이노 맥주소

    private static Bluetooth instance;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

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

    public Bluetooth() {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC_ADDRESS);
            mBluetoothSocket = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(MY_UUID);

            mBluetoothSocket.connect();

            mOutputStream = mBluetoothSocket.getOutputStream();
            mInputStream = mBluetoothSocket.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData(String data) {
        try {
            mOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(final Handler handler, final BluetoothCall bluetoothCall) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[1024];

                    while (true) {
                        Thread.sleep(100);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                bluetoothCall.onMaking();
                            }
                        });

                        if(mInputStream.read(buffer) == 14)
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    bluetoothCall.onComplete();
                                }
                            });

                        Log.d(TAG, "블루투스" + mInputStream.read(buffer) + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
