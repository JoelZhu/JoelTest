package com.joelzhu.joeltest.mbcheryota.entity;

import androidx.annotation.NonNull;

import java.util.Arrays;

public final class SecureInfo {
    private byte[] scBytes;

    private byte[] skBytes;

    private byte[] ccBytes;

    public byte[] getScBytes() {
        return scBytes;
    }

    public void setScBytes(final byte[] scBytes) {
        this.scBytes = scBytes;
    }

    public byte[] getSkBytes() {
        return skBytes;
    }

    public void setSkBytes(final byte[] skBytes) {
        this.skBytes = skBytes;
    }

    public byte[] getCcBytes() {
        return ccBytes;
    }

    public void setCcBytes(final byte[] ccBytes) {
        this.ccBytes = ccBytes;
    }

    @NonNull
    @Override
    public String toString() {
        return "SC: " + Arrays.toString(scBytes) + ", SK: " + Arrays.toString(
                skBytes) + ", CC: " + Arrays.toString(ccBytes);
    }
}