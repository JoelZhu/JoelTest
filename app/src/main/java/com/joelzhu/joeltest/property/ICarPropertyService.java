package com.joelzhu.joeltest.property;

import com.joelzhu.joeltest.base.ICarService;

import java.util.List;

public interface ICarPropertyService extends ICarService {
    String TAG = "JoelCarProperty";

    interface IOnProperty {
        void onPropertyChanged(final int propertyId, final int areaId, final Object value);
    }

    final class Prop {
        private final int propertyId;
        private final int areaId;
        private final String valueString;

        public Prop(final int propertyId, final int areaId, final String valueString) {
            this.propertyId = propertyId;
            this.areaId = areaId;
            this.valueString = valueString;
        }

        public int getPropertyId() {
            return propertyId;
        }

        public int getAreaId() {
            return areaId;
        }

        public String getValueString() {
            return valueString;
        }
    }

    void setProperty(final Prop prop);

    void setProperties(final List<Prop> props);

    String getProperty(final int propertyId, final int areaId);

    void subscribeProperty(final int propertyId);

    void unsubscribeProperty(final int propertyId);
}