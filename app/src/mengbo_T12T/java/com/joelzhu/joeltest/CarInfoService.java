package com.joelzhu.joeltest;

import android.car.Car;
import android.car.CarInfoManager;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.info.AbstractCarInfoService;

public class CarInfoService extends AbstractCarInfoService<CarInfoManager> {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.INFO_SERVICE;
    }

    @Override
    public String getHWVersion() {
        return mManager.getMBHWVersion();
    }

    @Override
    public String getSOCVersion() {
        return mManager.getMBSOCVersion();
    }

    @Override
    public String getMCUVersion() {
        return mManager.getMBMCUVersion();
    }

    @Override
    public String getVINCode() {
        return mManager.getMBVINCode();
    }
}