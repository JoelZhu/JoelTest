package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.power.CarPowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.power.AbstractCarPowerService;
import com.joelzhu.joeltest.power.ICarPowerService;

import java.util.concurrent.CompletableFuture;

public class CarPowerService extends AbstractCarPowerService<CarPowerManager> implements
        CarPowerManager.CarScreenOnListener, CarPowerManager.CarPowerStateListenerWithCompletion {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.POWER_SERVICE;
    }

    @Override
    public void screenOn(final @CarPowerManager.CarPowerScreenType int type) {
        if (mManager == null) {
            return;
        }

        mManager.screenOn(type);
    }

    @Override
    public void screenOff(final @CarPowerManager.CarPowerScreenType int type) {
        if (mManager == null) {
            return;
        }

        mManager.screenOff(type);
    }

    @Override
    public boolean isScreenOn(final @CarPowerManager.CarPowerScreenType int type) {
        if (mManager == null) {
            return false;
        }

        return mManager.isScreenOn(type);
    }

    @Override
    public void setListenerWithCompletion() {
        if (mManager == null) {
            return;
        }

        mManager.setListenerWithCompletion(this);
    }

    @Override
    public void registerScreenStateCallback() {
        mManager.registerScreenStateCallback(this);
    }

    @Override
    public void unregisterScreenStateCallback() {
        mManager.unregisterScreenStateCallback(this);
    }

    @Override
    public void onScreenStateChanged(final int screenType, final boolean isScreenOn) {
        if (mOnScreenChange != null) {
            mOnScreenChange.onScreenStateChanged(screenType, isScreenOn);
        }
    }

    @Override
    public void onStateChanged(final int state, final CompletableFuture<Void> future) {
        if (mOnState == null || future == null) {
            return;
        }

        if (state == CarPowerManager.CarPowerStateListener.SHUTDOWN_PREPARE) {
            mOnState.onReleaseResource(new CompletableFuture<>() {
                @Override
                public boolean complete(Void value) {
                    future.complete(null);
                    return true;
                }
            });
            mOnState.onShutdownPrepare();
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
}