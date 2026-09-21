package com.joelzhu.joeltest;

import android.car.Car;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.mbcheryota.ICarCheryOTAService;
import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CarCheryOTAService extends BaseCarService implements ICarCheryOTAService {
    private IMBOTACallback mListener = null;

    @Override
    protected void onCarConnected(@NonNull Car car) {
    }

    @Override
    public SecureInfo getSecureInfoSync() {
        return null;
    }

    @Override
    public void getSecureInfoAsync() {
    }

    @Override
    public AuthInfo getAuthInfoSync(final byte[] authBytes) {
        return null;
    }

    @Override
    public void getAuthInfoAsync(byte[] authBytes) {
    }

    @Override
    public void sendPowerOn() {
    }

    @Override
    public void sendPowerOff() {
    }

    @Override
    public void sendPowerContinuous() {
    }

    @Override
    public void registerListener() {
    }

    @Override
    public void unregisterListener() {
    }

    public void registerListenerInner(final IMBOTACallback listener) {
        mListener = listener;
    }
}