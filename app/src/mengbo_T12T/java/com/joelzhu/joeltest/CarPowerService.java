package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.power.CarPowerManager;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.power.ICarPowerService;

public class CarPowerService extends BaseCarService implements ICarPowerService,
        CarPowerManager.CarScreenOnListener {
    private CarPowerManager mPowerManager = null;

    private IOnScreen mOnScreenChange = null;

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
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.registerScreenStateCallback(this);
    }

    @Override
    public void unregisterScreenStateCallback() {
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.unregisterScreenStateCallback(this);
    }

    @Override
    public void switchToHost() {
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.switchToHostMode();
    }

    @Override
    public void switchToDevice() {
        if (mPowerManager == null) {
            return;
        }

        mPowerManager.switchToDeviceMode();
    }

    @Override
    public void onScreenStateChanged(final int screenType, final boolean isScreenOn) {
        if (mOnScreenChange != null) {
            mOnScreenChange.onScreenStateChanged(screenType, isScreenOn);
        }
    }

    public void registerScreenStateChanged(final IOnScreen listener) {
        mOnScreenChange = listener;
    }
}