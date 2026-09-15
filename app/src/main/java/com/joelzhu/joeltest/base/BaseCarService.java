package com.joelzhu.joeltest.base;

import android.car.Car;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

public abstract class BaseCarService implements ICarService, Car.CarServiceLifecycleListener {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private Car mCar = null;
    private IOnServiceConnectState mListener = null;

    protected abstract void onCarConnected(final @NonNull Car car);

    @Override
    public final void onLifecycleChanged(final @NonNull Car car, final boolean state) {
        this.mCar = car;
        onCarConnected(car);
        if (mListener != null) {
            mListener.onConnectStateChanged(state);
        }
    }

    @Override
    public final void connectCar(final Context context) {
        Car.createCar(context, mHandler, 1000, this);
    }

    @Override
    public final void disconnectCar() {
        if (mCar != null) {
            mCar.disconnect();
        }
    }

    protected final void registerServiceState(final IOnServiceConnectState listener) {
        mListener = listener;
    }

    protected final void unregisterServiceState() {
        mListener = null;
    }
}
