package com.joelzhu.joeltest;

import android.car.Car;
import android.car.mbotaproperty.MBOTAPropertyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.mbcheryota.ICarCheryOTAService;
import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CarCheryOTAService extends BaseCarService implements ICarCheryOTAService,
        MBOTAPropertyManager.MBOTAPropertyListener {
    private MBOTAPropertyManager mPropertyManager = null;

    private ICarCheryOTAService.IMBOTACallback mListener = null;

    @Override
    protected void onCarConnected(@NonNull Car car) {
        mPropertyManager = (MBOTAPropertyManager) car.getCarManager(
                Car.CAR_MB_OTA_PROPERTY_SERVICE);
    }

    @Override
    public SecureInfo getSecureInfoSync() {
        if (mPropertyManager == null) {
            return null;
        }

        Log.d(ICarCheryOTAService.TAG, "Start to request secure information.");
        final android.car.mbotaproperty.SecureInfo serviceInfo = mPropertyManager.getSecureInfoSync();
        Log.d(ICarCheryOTAService.TAG, "Secure information query finished.");
        if (serviceInfo == null) {
            Log.e(ICarCheryOTAService.TAG, "Secure information is null from service.");
            return null;
        }

        return convertSecureInfo(serviceInfo);
    }

    @Override
    public void getSecureInfoAsync() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.getSecureInfoAsync();
    }

    @Override
    public AuthInfo getAuthInfoSync(final byte[] authBytes) {
        if (mPropertyManager == null) {
            return null;
        }

        Log.d(ICarCheryOTAService.TAG, "Start to request auth information.");
        final android.car.mbotaproperty.AuthInfo serviceInfo = mPropertyManager
                .getAuthInfoSync(authBytes);
        Log.d(ICarCheryOTAService.TAG, "Auth information query finished.");
        if (serviceInfo == null) {
            Log.e(ICarCheryOTAService.TAG, "Auth information is null from service.");
            return null;
        }

        return convertAuthInfo(serviceInfo);
    }

    @Override
    public void getAuthInfoAsync(byte[] authBytes) {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.getAuthInfoAsync(authBytes);
    }

    @Override
    public void sendPowerOn() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.powerManagement(1);
    }

    @Override
    public void sendPowerOff() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.powerManagement(2);
    }

    @Override
    public void sendPowerContinuous() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.powerManagement(3);
    }

    @Override
    public void registerListener() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.registerCallback(this);
    }

    @Override
    public void unregisterListener() {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.unregisterCallback(this);
    }

    @Override
    public void onSecureInfoChanged(@Nullable android.car.mbotaproperty.SecureInfo secureInfo) {
        if (mListener != null) {
            mListener.onSecureInfoChanged(convertSecureInfo(secureInfo));
        }
    }

    @Override
    public void onAuthenticationChanged(@Nullable android.car.mbotaproperty.AuthInfo authInfo) {
        if (mListener != null) {
            mListener.onAuthenticationChanged(convertAuthInfo(authInfo));
        }
    }

    @Override
    public void onPowerOnState(int state) {
        if (mListener != null) {
            mListener.onPowerOnState(state);
        }
    }

    @Override
    public void onPowerOffState(int state) {
        if (mListener != null) {
            mListener.onPowerOffState(state);
        }
    }

    public void registerListenerInner(final ICarCheryOTAService.IMBOTACallback listener) {
        mListener = listener;
    }

    public SecureInfo convertSecureInfo(final android.car.mbotaproperty.SecureInfo serviceInfo) {
        final SecureInfo secureInfo = new SecureInfo();
        secureInfo.setScBytes(serviceInfo.getSecurityCode());
        secureInfo.setSkBytes(serviceInfo.getSecurityKey());
        secureInfo.setCcBytes(serviceInfo.getConstantCode());
        return secureInfo;
    }

    public AuthInfo convertAuthInfo(final android.car.mbotaproperty.AuthInfo serviceInfo) {
        final AuthInfo authInfo = new AuthInfo();
        authInfo.setAuthBytes(serviceInfo.getAuthenticationBytes());
        return authInfo;
    }
}