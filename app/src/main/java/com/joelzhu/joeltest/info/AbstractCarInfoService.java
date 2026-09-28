package com.joelzhu.joeltest.info;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

public abstract class AbstractCarInfoService<Manager> extends BaseCarService<Manager>
        implements ICarService, ICarInfoService {
    protected static final String TAG = ICarInfoService.TAG;
}