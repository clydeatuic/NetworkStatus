package com.uic.demo.networkstatus.networkstatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    TextView textView_status;
    ImageView imageView_status;
    NetworkStatusDetector networkStatusDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_status = (TextView) findViewById(R.id.textView_Status);
        imageView_status = (ImageView) findViewById(R.id.imageView_status);
        networkStatusDetector = new NetworkStatusDetector(this);

        checkConnection();
        uicThread();
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
    }

    // Showing the status
    private void showToast(boolean isConnected) {
        String message;
        if (isConnected) {
            message = "Good! Connected to Internet";
            imageView_status.setBackgroundResource(R.drawable.ic_cloud_queue_black_24dp);
        } else {
            message = "Sorry! Not connected to internet";
            imageView_status.setBackgroundResource(R.drawable.ic_cloud_off_black_24dp);
        }
        textView_status.setText(message);
    }

    public void uicThread(){
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //do something here...
                                //textView_status.setText("#" + i);
                                checkConnection();
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
