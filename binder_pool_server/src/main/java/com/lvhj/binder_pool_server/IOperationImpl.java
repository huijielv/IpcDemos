package com.lvhj.binder_pool_server;

import android.os.RemoteException;
import android.util.Log;

public class IOperationImpl extends IOperation.Stub {
    @Override
    public int add(int parameter1, int parameter2) throws RemoteException {

        Log.e("IOperationImpl", String.valueOf(parameter1 + parameter2));
        return parameter1 + parameter2;
    }
}
