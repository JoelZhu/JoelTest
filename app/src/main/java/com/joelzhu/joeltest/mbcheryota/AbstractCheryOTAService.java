package com.joelzhu.joeltest.mbcheryota;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

public abstract class AbstractCheryOTAService<Manager> extends BaseCarService<Manager>
        implements ICarService, ICheryOTAService {
    protected static final String TAG = ICheryOTAService.TAG;

    protected ICheryOTAService.IMBOTACallback mListener = null;

    public void registerListenerInner(final ICheryOTAService.IMBOTACallback listener) {
        mListener = listener;
    }
}