// IOnOperationCompletedListener.aidl
package com.lvhj.aidl_service;

// Declare any non-default types here with import statements
import com.lvhj.aidl_service.Parameter;

interface IOnOperationCompletedListener {
    void onOperationCompleted(in Parameter result);

}