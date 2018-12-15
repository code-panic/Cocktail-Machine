package com.example.administrator.cocktailmobileapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class ListActivity extends AppCompatActivity {

    Button button;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "AB:03:67:78:2B:0B";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("5");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //BluetoothDevice 객체를 아두이노 맥 주소를 이용해 아두이노와 연결
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        //BluetoothDevice 객체를 이용해 블루투스 소켓을 얻습니다.
        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e1) { }

        //연결 시작!
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) { }
        }

        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) { }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (outStream != null) {
            try {
                outStream.flush();
            } catch (IOException e) { }
        }

        try {
            btSocket.close();
        } catch (IOException e2) { }
    }

    private void checkBTState() {
        //블루투스 상태 확인
        if(btAdapter==null) {
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

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //버전에 맞춰 Bluetooth 객체 생성
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) { }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    private void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) { }
    }
}
