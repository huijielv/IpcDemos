package com.lvhj.binder_pool_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class BinderPoolService extends Service {

    private class BinderPoolImpl extends IBinderPool.Stub {

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            switch (binderCode) {
                case 100: {
                    return new com.lvhj.binder_pool_server.IOperationImpl();
                }

            }
            return null;
        }
    }

    private Binder binderPool;

    public BinderPoolService() {
        binderPool = new BinderPoolImpl();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binderPool;
    }
}
