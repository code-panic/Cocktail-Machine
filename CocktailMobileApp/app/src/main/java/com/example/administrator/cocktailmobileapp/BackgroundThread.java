package com.example.administrator.cocktailmobileapp;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class BackgroundThread extends Thread {

    @Override
    public void run() {
        while (Bluetooth.getInstance().getExtracting()) {
            try {
                int byteAvailable = Bluetooth.getInstance().inStream.available();
                if (byteAvailable > 0) {
                    // 입력 스트림에서 바이트 단위로 읽어 옵니다.
                    byte[] bytes = new byte[byteAvailable];
                    Bluetooth.getInstance().inStream.mark(30);
                    Bluetooth.getInstance().inStream.read(bytes);

                    final String text = new String(bytes, "US-ASCII");
                    Log.d("msg",text);
                    if (text.equals("d")) {
                        if(Bluetooth.getInstance().getExtracting())
                            Log.d("msg", "호호호ㅗ호홓");
                        Bluetooth.getInstance().setExtracting(false);
                        Log.d("msg","가");
                        ListActivity.instance.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ListActivity.instance.getApplicationContext(), "추출을 완료했습니다!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    } else {
                        Bluetooth.getInstance().inStream.reset();
                    }
                }
            } catch (IOException e) {
            }
            try {
                // 0.3초마다 받아옴
                Thread.sleep(300);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

}
