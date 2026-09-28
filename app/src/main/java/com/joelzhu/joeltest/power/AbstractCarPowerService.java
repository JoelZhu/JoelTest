package com.joelzhu.joeltest.power;

import android.content.Context;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.base.ICarService;

import java.util.concurrent.Executor;

public abstract class AbstractCarPowerService<Manager> extends BaseCarService<Manager>
        implements ICarService, ICarPowerService {
    protected static final String TAG = ICarPowerService.TAG;

    protected ICarPowerService.IOnScreen mOnScreenChange = null;

    protected ICarPowerService.IOnState mOnState = null;

    protected Executor mExecutor = null;

    @Override
    public void setupExecutor(final Context context) {
        if (context != null) {
            mExecutor = context.getMainExecutor();
        }
    }

    public void registerScreenStateChanged(final ICarPowerService.IOnScreen listener) {
        mOnScreenChange = listener;
    }

    public void registerOnStateChanged(final IOnState listener) {
        mOnState = listener;
    }
}