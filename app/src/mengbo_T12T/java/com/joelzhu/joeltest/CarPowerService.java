package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.power.CarPowerManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.power.AbstractCarPowerService;

import java.util.concurrent.CompletableFuture;

public class CarPowerService extends AbstractCarPowerService<CarPowerManager>
        implements CarPowerManager.CarScreenOnListener,
        CarPowerManager.CarPowerStateListenerWithCompletion {
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
        if (mExecutor == null) {
            Log.e(TAG, "Executor is null, call setupExecutor first please.");
            return;
        }

        mManager.setListenerWithCompletion(mExecutor, this);
    }

    @Override
    public void registerScreenStateCallback() {
        if (mManager == null) {
            return;
        }

        mManager.registerScreenStateCallback(this);
    }

    @Override
    public void unregisterScreenStateCallback() {
        if (mManager == null) {
            return;
        }

        mManager.unregisterScreenStateCallback(this);
    }

    @Override
    public void switchToHost() {
        if (mManager == null) {
            return;
        }

        mManager.switchToHostMode();
    }

    @Override
    public void switchToDevice() {
        if (mManager == null) {
            return;
        }

        mManager.switchToDeviceMode();
    }

    @Override
    public void onScreenStateChanged(final int screenType, final boolean isScreenOn) {
        if (mOnScreenChange != null) {
            mOnScreenChange.onScreenStateChanged(screenType, isScreenOn);
        }
    }

    @Override
    public void onStateChanged(final int state,
            final @Nullable CarPowerManager.CompletablePowerStateChangeFuture future) {
        if (mOnState == null || future == null) {
            return;
        }

        if (state == CarPowerManager.STATE_PRE_SHUTDOWN_PREPARE) {
            Log.d(TAG, "Received STATE_PRE_SHUTDOWN_PREPARE.");
            mOnState.onReleaseResource(new CompletableFuture<>() {
                @Override
                public boolean complete(Void value) {
                    future.complete();
                    Log.d(TAG, "Release resource completed.");
                    return true;
                }
            });
        } else if (state == CarPowerManager.STATE_SHUTDOWN_PREPARE) {
            Log.d(TAG, "Received STATE_SHUTDOWN_PREPARE.");
            mOnState.onShutdownPrepare();
        }
    }
}