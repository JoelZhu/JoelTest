package com.joelzhu.joeltest;

import android.car.Car;
import android.car.mbrestriction.MBUxRestrictionManager;
import android.car.mbrestriction.RestrictionType;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.mbrestriction.AbstractMBRestrictionService;

public class MBRestrictionService extends AbstractMBRestrictionService<MBUxRestrictionManager> {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.CAR_MB_UX_RESTRICTION_SERVICE;
    }

    @Override
    public void notifyActiveState(final @RestrictionType int restrictionType,
            final String packageName, final boolean isActive) {
        if (mManager == null) {
            return;
        }

        mManager.notifyActiveState(restrictionType, packageName, isActive);
    }
}