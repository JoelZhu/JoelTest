package com.joelzhu.joeltest.power;

import android.car.hardware.power.CarPowerManager;
import android.content.Context;

import com.joelzhu.joeltest.base.ICarService;

import java.util.concurrent.CompletableFuture;

public interface ICarPowerService extends ICarService {
    String TAG = "JoelPower";

    interface IOnScreen {
        void onScreenStateChanged(final int screenType, final boolean isScreenOn);
    }

    interface IOnState {
        default void onReleaseResource(final CompletableFuture<Void> future) {
            future.complete(null);
        }

        default void onShutdownPrepare() {
        }
    }

    default void setupExecutor(final Context context) {
    }

    void screenOn(final @CarPowerManager.CarPowerScreenType int type);

    void screenOff(final @CarPowerManager.CarPowerScreenType int type);

    boolean isScreenOn(final @CarPowerManager.CarPowerScreenType int type);

    void setListenerWithCompletion();

    void registerScreenStateCallback();

    void unregisterScreenStateCallback();

    void switchToHost();

    void switchToDevice();
}