package com.joelzhu.joeltest.mbcheryota;

import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public interface ICheryOTAService {
    String TAG = "JoelCheryOTA";

    interface IMBOTACallback {
        void onSecureInfoChanged(SecureInfo info);

        void onAuthenticationChanged(AuthInfo info);

        void onPowerOnState(int state);

        void onPowerOffState(int state);
    }

    SecureInfo getSecureInfoSync();

    void getSecureInfoAsync();

    AuthInfo getAuthInfoSync(final byte[] authBytes);

    void getAuthInfoAsync(final byte[] authBytes);

    void sendPowerOn();

    void sendPowerOff();

    void sendPowerContinuous();

    void registerListener();

    void unregisterListener();
}