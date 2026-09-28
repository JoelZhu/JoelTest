package com.joelzhu.joeltest.info;

import com.joelzhu.joeltest.base.ICarService;

public interface ICarInfoService extends ICarService {
    String TAG = "JoelInfo";

    String getHWVersion();

    String getSOCVersion();

    String getMCUVersion();

    String getVINCode();
}