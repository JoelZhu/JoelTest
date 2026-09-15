package com.joelzhu.joeltest;

import android.car.Car;
import android.car.mbotaproperty.MBOTAPropertyManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.mbcheryota.ICarCheryOTAService;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CarCheryOTAService extends BaseCarService implements ICarCheryOTAService {
    private MBOTAPropertyManager mPropertyManager = null;

    @Override
    protected void onCarConnected(@NonNull Car car) {
        mPropertyManager = (MBOTAPropertyManager) car.getCarManager(
                Car.CAR_MB_OTA_PROPERTY_SERVICE);
    }

    @Override
    public SecureInfo getSecureInfo() {
        if (mPropertyManager == null) {
            return null;
        }

        Log.d(ICarCheryOTAService.TAG, "Start to request secure information.");
        final android.car.mbotaproperty.SecureInfo serviceInfo = mPropertyManager.querySecureInfo();
        Log.d(ICarCheryOTAService.TAG, "Secure information query finished.");
        if (serviceInfo == null) {
            Log.e(ICarCheryOTAService.TAG, "Secure information is null from service.");
            return null;
        }

        final SecureInfo secureInfo = new SecureInfo();
        secureInfo.setScBytes(serviceInfo.getSecurityCode());
        secureInfo.setSkBytes(serviceInfo.getSecurityKey());
        secureInfo.setCcBytes(serviceInfo.getConstantCode());
        return secureInfo;
    }
}