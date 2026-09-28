package com.joelzhu.joeltest;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.mbcheryota.AbstractCheryOTAService;
import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CheryOTAService extends AbstractCheryOTAService<Void> {
    @Nullable
    @Override
    protected String serviceName() {
        return null;
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
}