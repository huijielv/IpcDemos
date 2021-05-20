package com.lvhj.aidl_service;

import com.lvhj.aidl_service.Parameter;
import com.lvhj.aidl_service.IOnOperationCompletedListener;

interface IOperationManager {

   void operation(in Parameter parameter1 , in Parameter parameter2);

   void registerListener(in IOnOperationCompletedListener listener);

   void unregisterListener(in IOnOperationCompletedListener listener);

}
