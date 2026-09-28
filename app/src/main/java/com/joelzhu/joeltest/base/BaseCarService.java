package com.joelzhu.joeltest.base;

import android.car.Car;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseCarService<Manager> implements ICarService,
        Car.CarServiceLifecycleListener {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private Car mCar = null;
    private IOnServiceConnectState mConnectStateListener = null;

    protected Manager mManager = null;

    @Nullable
    protected abstract String serviceName();

    protected void onCarConnected(final @NonNull Car car) {
        final String serviceName = serviceName();
        if (serviceName != null) {
            mManager = (Manager) car.getCarManager(serviceName());
        }
    }

    @Override
    public final void onLifecycleChanged(final @NonNull Car car, final boolean state) {
        this.mCar = car;
        onCarConnected(car);
        if (mConnectStateListener != null) {
            mConnectStateListener.onConnectStateChanged(state);
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
        mConnectStateListener = listener;
    }

    protected final void unregisterServiceState() {
        mConnectStateListener = null;
    }
}
