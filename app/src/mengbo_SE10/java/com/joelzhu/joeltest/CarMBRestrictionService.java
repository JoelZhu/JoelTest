package com.joelzhu.joeltest;

import android.car.Car;
import android.car.mbrestriction.MBUxRestrictionManager;
import android.car.mbrestriction.RestrictionType;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.mbrestriction.ICarMBRestrictionService;

public class CarMBRestrictionService extends BaseCarService implements ICarMBRestrictionService {
    private MBUxRestrictionManager mMBRestrictionManager = null;

    @Override
    protected void onCarConnected(final @NonNull Car car) {
        mMBRestrictionManager = (MBUxRestrictionManager) car.getCarManager(
                Car.CAR_MB_UX_RESTRICTION_SERVICE);
    }

    @Override
    public void notifyActiveState(final @RestrictionType int restrictionType,
            final String packageName, final boolean isActive) {
        if (mMBRestrictionManager == null) {
            return;
        }

        mMBRestrictionManager.notifyActiveState(restrictionType, packageName, isActive);
    }
}