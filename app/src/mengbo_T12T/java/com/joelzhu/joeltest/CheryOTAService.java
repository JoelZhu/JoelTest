package com.joelzhu.joeltest;

import android.car.Car;
import android.car.mbotaproperty.MBOTAPropertyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.mbcheryota.AbstractCheryOTAService;
import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CheryOTAService extends AbstractCheryOTAService<MBOTAPropertyManager>
        implements MBOTAPropertyManager.MBOTAPropertyListener {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.CAR_MB_OTA_PROPERTY_SERVICE;
    }

    @Override
    public SecureInfo getSecureInfoSync() {
        if (mManager == null) {
            return null;
        }

        Log.d(TAG, "Start to request secure information.");
        final android.car.mbotaproperty.SecureInfo serviceInfo = mManager.getSecureInfoSync();
        Log.d(TAG, "Secure information query finished.");
        if (serviceInfo == null) {
            Log.e(TAG, "Secure information is null from service.");
            return null;
        }

        return convertSecureInfo(serviceInfo);
    }

    @Override
    public void getSecureInfoAsync() {
        if (mManager == null) {
            return;
        }

        mManager.getSecureInfoAsync();
    }

    @Override
    public AuthInfo getAuthInfoSync(final byte[] authBytes) {
        if (mManager == null) {
            return null;
        }

        Log.d(TAG, "Start to request auth information.");
        final android.car.mbotaproperty.AuthInfo serviceInfo = mManager.getAuthInfoSync(authBytes);
        Log.d(TAG, "Auth information query finished.");
        if (serviceInfo == null) {
            Log.e(TAG, "Auth information is null from service.");
            return null;
        }

        return convertAuthInfo(serviceInfo);
    }

    @Override
    public void getAuthInfoAsync(byte[] authBytes) {
        if (mManager == null) {
            return;
        }

        mManager.getAuthInfoAsync(authBytes);
    }

    @Override
    public void sendPowerOn() {
        if (mManager == null) {
            return;
        }

        mManager.powerManagement(1);
    }

    @Override
    public void sendPowerOff() {
        if (mManager == null) {
            return;
        }

        mManager.powerManagement(2);
    }

    @Override
    public void sendPowerContinuous() {
        if (mManager == null) {
            return;
        }

        mManager.powerManagement(3);
    }

    @Override
    public void registerListener() {
        if (mManager == null) {
            return;
        }

        mManager.registerCallback(this);
    }

    @Override
    public void unregisterListener() {
        if (mManager == null) {
            return;
        }

        mManager.unregisterCallback(this);
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