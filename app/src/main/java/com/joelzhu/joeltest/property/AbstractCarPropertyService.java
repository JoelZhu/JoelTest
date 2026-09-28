package com.joelzhu.joeltest.property;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

public abstract class AbstractCarPropertyService<Manager> extends BaseCarService<Manager>
        implements ICarService, ICarPropertyService {
    protected ICarPropertyService.IOnProperty mPropertyChange = null;

    public void registerPropertyChanged(final IOnProperty listener) {
        mPropertyChange = listener;
    }
}