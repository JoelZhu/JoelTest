package com.joelzhu.joeltest.mbcheryota.entity;

import androidx.annotation.NonNull;

import java.util.Arrays;

public final class AuthInfo {
    private byte[] authBytes;

    public byte[] getAuthBytes() {
        return authBytes;
    }

    public void setAuthBytes(final byte[] authBytes) {
        this.authBytes = authBytes;
    }

    @NonNull
    @Override
    public String toString() {
        return "Authentication: " + Arrays.toString(authBytes);
    }
}