package com.lvhj.aidl_service;

import android.os.Parcel;
import android.os.Parcelable;

public class Parameter implements Parcelable {

    private int param;

    ClassLoader classLoader = new ClassLoader() {};

    public Parameter(int param) {
        this.param = param;
    }

    public int getParam() {
        return param;
    }

    public void setParam(int param) {
        this.param = param;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.param);
    }

    protected Parameter(Parcel in) {
        this.param = in.readInt();
    }

    public static final Creator<Parameter> CREATOR = new Creator<Parameter>() {
        @Override
        public Parameter createFromParcel(Parcel source) {
            return new Parameter(source);
        }

        @Override
        public Parameter[] newArray(int size) {
            return new Parameter[size];
        }
    };
}
