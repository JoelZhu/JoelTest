package com.joelzhu.joeltest.mbrestriction;

import android.car.mbrestriction.RestrictionType;

import com.joelzhu.joeltest.base.ICarService;

public interface IMBRestrictionService extends ICarService {
    void notifyActiveState(final @RestrictionType int restrictionType, final String packageName,
            final boolean isActive);
}