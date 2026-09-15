package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.power.CarPowerManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.power.ICarPowerService;

public class CarPowerService extends BaseCarService implements ICarPowerService,
        CarPowerManager.CarScreenOnListener {
    private CarPowerManager mPowerManager = null;

    private ICarPowerService.IOnScreen mOnScreenChange = null;

    @Override
    protected void onCarConnected(final @NonNull Car car) {
        mPowerManager = (CarPowerManager) car.getCarManager(Car.POWER_SERVICE);
    }

    @Override
    public void screenOn(final @CarPowerManager.CarPowerScreenType int type) {
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.screenOn(type);
    }

    @Override
    public void screenOff(final @CarPowerManager.CarPowerScreenType int type) {
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.screenOff(type);
    }

    @Override
    public boolean isScreenOn(final @CarPowerManager.CarPowerScreenType int type) {
        if (mPowerManager == null) {
            return false;
        }

        return mPowerManager.isScreenOn(type);
    }

    @Override
    public void registerScreenStateCallback() {
        mPowerManager.registerScreenStateCallback(this);
    }

    @Override
    public void unregisterScreenStateCallback() {
        mPowerManager.unregisterScreenStateCallback(this);
    }

    @Override
    public void onScreenStateChanged(final int screenType, final boolean isScreenOn) {
        if (mOnScreenChange != null) {
            mOnScreenChange.onScreenStateChanged(screenType, isScreenOn);
        }
    }

    @Override
    public void switchToHost() {
        // Do nothing.
        Log.d(ICarPowerService.TAG, "Switch device host is not supported in Android11.");
    }

    @Override
    public void switchToDevice() {
        // Do nothing.
        Log.d(ICarPowerService.TAG, "Switch device host is not supported in Android11.");
    }

    public void registerScreenStateChanged(final ICarPowerService.IOnScreen listener) {
        mOnScreenChange = listener;
    }
}