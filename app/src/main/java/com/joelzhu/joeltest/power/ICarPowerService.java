package com.joelzhu.joeltest.power;

import android.car.hardware.power.CarPowerManager;

import com.joelzhu.joeltest.base.ICarService;

public interface ICarPowerService extends ICarService {
    interface IOnScreen {
        void onScreenStateChanged(final int screenType, final boolean isScreenOn);
    }

    void screenOn(final @CarPowerManager.CarPowerScreenType int type);

    void screenOff(final @CarPowerManager.CarPowerScreenType int type);

    boolean isScreenOn(final @CarPowerManager.CarPowerScreenType int type);

    void registerScreenStateCallback();

    void unregisterScreenStateCallback();

    void switchToHost();

    void switchToDevice();
}