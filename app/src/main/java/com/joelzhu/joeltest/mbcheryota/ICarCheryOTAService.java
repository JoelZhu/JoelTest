package com.joelzhu.joeltest.mbcheryota;

import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public interface ICarCheryOTAService {
    String TAG = "CheryOTA";

    SecureInfo getSecureInfo();
}