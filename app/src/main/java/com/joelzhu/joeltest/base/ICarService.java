package com.joelzhu.joeltest.base;

import android.content.Context;

public interface ICarService {
    String TAG = "JoelCar";

    interface IOnServiceConnectState {
        void onConnectStateChanged(final boolean isConnected);
    }

    void connectCar(final Context context);

    void disconnectCar();
}