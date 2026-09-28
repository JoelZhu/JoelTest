package com.joelzhu.joeltest;

import android.car.Car;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.property.AbstractCarPropertyService;
import com.joelzhu.joeltest.property.util.CarPropertyUtil;

import java.util.List;

public class CarPropertyService extends AbstractCarPropertyService<CarPropertyManager>
        implements CarPropertyManager.CarPropertyEventCallback {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.PROPERTY_SERVICE;
    }

    @Override
    public void setProperty(final Prop prop) {
        if (mManager == null) {
            return;
        }

        final int propertyId = prop.getPropertyId();
        final int areaId = prop.getAreaId();
        final String valueString = prop.getValueString();
        mManager.setProperty(CarPropertyUtil.parseType(propertyId), propertyId, areaId,
                CarPropertyUtil.parseValue(propertyId, valueString));
    }

    @Override
    public void setProperties(final List<Prop> props) {
        if (mManager == null || props == null) {
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
        mManager.setProperties(requests);
    }

    @Override
    public String getProperty(final int propertyId, final int areaId) {
        if (mManager == null) {
            return "";
        }

        final Class clazz = CarPropertyUtil.parseType(propertyId);
        final CarPropertyValue value = mManager.getProperty(clazz, propertyId, areaId);
        if (value == null) {
            return "";
        }
        return CarPropertyUtil.propertyValueToString(clazz, value.getValue());
    }

    @Override
    public void subscribeProperty(final int propertyId) {
        if (mManager == null) {
            return;
        }

        mManager.registerCallback(this, propertyId, 0f);
    }

    @Override
    public void unsubscribeProperty(final int propertyId) {
        if (mManager == null) {
            return;
        }

        mManager.unregisterCallback(this, propertyId);
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
}