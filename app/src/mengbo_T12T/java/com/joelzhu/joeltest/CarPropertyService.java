package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.property.ICarPropertyService;
import com.joelzhu.joeltest.property.util.CarPropertyUtil;

import java.util.List;

public class CarPropertyService extends BaseCarService implements ICarPropertyService,
        CarPropertyManager.CarPropertyEventCallback {
    private CarPropertyManager mPropertyManager = null;

    private ICarPropertyService.IOnProperty mPropertyChange = null;

    @Override
    protected void onCarConnected(final @NonNull Car car) {
        this.mPropertyManager = (CarPropertyManager) car.getCarManager(Car.PROPERTY_SERVICE);
    }

    @Override
    public void setProperty(final Prop prop) {
        if (mPropertyManager == null) {
            return;
        }

        final int propertyId = prop.getPropertyId();
        final int areaId = prop.getAreaId();
        final Class clazz = CarPropertyUtil.parseType(propertyId);
        final String valueString = prop.getValueString();
        final Object value = CarPropertyUtil.parseValue(propertyId, valueString);
        Log.d(ICarPropertyService.TAG, "To set property: " + Integer.toHexString(propertyId));
        mPropertyManager.setProperty(clazz, propertyId, areaId, value);
    }

    @Override
    public void setProperties(final List<Prop> props) {
        if (mPropertyManager == null || props == null) {
            return;
        }

        final CarPropertyManager.SetMultiProp<?>[] requests =
                new CarPropertyManager.SetMultiProp[props.size()];
        for (int index = 0; index < props.size(); index++) {
            final Prop prop = props.get(index);

            final int propertyId = prop.getPropertyId();
            final int areaId = prop.getAreaId();
            final String valueString = prop.getValueString();
            final CarPropertyManager.SetMultiProp<?> request = new
                    CarPropertyManager.SetMultiProp<>(CarPropertyUtil.parseType(propertyId),
                    propertyId, areaId, CarPropertyUtil.parseValue(propertyId, valueString));
            requests[index] = request;
        }
        mPropertyManager.setProperties(requests);
    }

    @Override
    public String getProperty(final int propertyId, final int areaId) {
        if (mPropertyManager == null) {
            return "";
        }

        final Class clazz = CarPropertyUtil.parseType(propertyId);
        final CarPropertyValue value = mPropertyManager.getProperty(clazz, propertyId, areaId);
        if (value == null) {
            return "";
        }
        return CarPropertyUtil.propertyValueToString(clazz, value.getValue());
    }

    @Override
    public void subscribeProperty(final int propertyId) {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.subscribePropertyEvents(propertyId, this);
    }

    @Override
    public void unsubscribeProperty(final int propertyId) {
        if (mPropertyManager == null) {
            return;
        }

        mPropertyManager.unsubscribePropertyEvents(propertyId, this);
    }

    @Override
    public void onChangeEvent(final CarPropertyValue carPropertyValue) {
        if (carPropertyValue == null) {
            return;
        }

        if (mPropertyChange != null) {
            mPropertyChange.onPropertyChanged(carPropertyValue.getPropertyId(),
                    carPropertyValue.getAreaId(), carPropertyValue.getValue());
        }
    }

    @Override
    public void onErrorEvent(int i, int i1) {

    }

    public void registerPropertyChanged(final IOnProperty listener) {
        mPropertyChange = listener;
    }
}