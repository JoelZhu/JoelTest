package com.joelzhu.joeltest.input;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

public abstract class AbstractCarInputService<Manager> extends BaseCarService<Manager>
        implements ICarService, ICarInputService {
    protected ICarInputService.IOnEvent mEventChange = null;

    public void registerEventChanged(final ICarInputService.IOnEvent event) {
        mEventChange = event;
    }
}