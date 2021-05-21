// IBinderPool.aidl
package com.lvhj.binder_pool_server;


interface IBinderPool {

     IBinder queryBinder(int binderCode);
}
