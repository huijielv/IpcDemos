package com.lvhj.aidl_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lvhj.aidl_service.IOnOperationCompletedListener;
import com.lvhj.aidl_service.IOperationManager;
import com.lvhj.aidl_service.Parameter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private IOperationManager iOperationManager;

    private IOnOperationCompletedListener completedListener = new IOnOperationCompletedListener.Stub() {
        @Override
        public void onOperationCompleted(final Parameter result) throws RemoteException {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, String.valueOf(result.getParam()));

                }
            });
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iOperationManager = IOperationManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iOperationManager = null;
        }
    };


    private Button button ,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button =findViewById(R.id.btn_registerListener);

        button2   =findViewById(R.id.btn_operation);

        bindService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iOperationManager != null) {
                    try {
                        iOperationManager.registerListener(completedListener);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        final int param1 = 10;
                        final int param2 = 10;

                        Parameter parameter1 = new Parameter(param1);
                        Parameter parameter2 = new Parameter(param2);
                        if (iOperationManager != null) {
                            try {
                                iOperationManager.operation(parameter1, parameter2);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                }).start();
            }
        });





    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("com.lvhj.aidl_service", "com.lvhj.aidl_service.AIDLService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iOperationManager != null) {
            try {
                iOperationManager.unregisterListener(completedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (serviceConnection != null) {
            unbindService(serviceConnection);
        }


    }
}
