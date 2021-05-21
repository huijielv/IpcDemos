package com.lvhj.binder_pool_client;

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

import com.lvhj.binder_pool_server.IBinderPool;
import com.lvhj.binder_pool_server.IOperation;

public class MainActivity extends AppCompatActivity {

    private IOperation operation;




    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                IBinderPool binderPool = IBinderPool.Stub.asInterface(service);
                //本客户端的唯一标识是 100
                //获取真实的 Binder 对象
                operation = IOperation.Stub.asInterface(binderPool.queryBinder(100));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            operation = null;
            bindService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation != null) {
                    try {
                        Log.e("test", "4+2 加法：" + operation.add(4, 2));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName("com.lvhj.binder_pool_server", "com.lvhj.binder_pool_server.BinderPoolService");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (operation != null) {
            unbindService(serviceConnection);
        }
    }
}
