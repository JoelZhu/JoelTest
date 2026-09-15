package com.joelzhu.joeltest;

import android.car.Car;
import android.car.CarInfoManager;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.info.ICarInfoService;

public class CarInfoService extends BaseCarService implements ICarInfoService {
    private CarInfoManager mInfoManager = null;

    @Override
    protected void onCarConnected(final @NonNull Car car) {
        mInfoManager = (CarInfoManager) car.getCarManager(Car.INFO_SERVICE);
    }

    @Override
    public String getHWVersion() {
        return mInfoManager.getMBHWVersion();
    }

    @Override
    public String getSOCVersion() {
        return mInfoManager.getMBSOCVersion();
    }

    @Override
    public String getMCUVersion() {
        return mInfoManager.getMBMCUVersion();
    }

    @Override
    public String getVINCode() {
        return mInfoManager.getMBVINCode();
    }
}