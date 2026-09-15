package com.joelzhu.joeltest.property;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.joelzhu.joeltest.CarPropertyService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;
import com.joelzhu.joeltest.property.util.CarPropertyUtil;

import java.util.ArrayList;
import java.util.List;

public class CarPropertyTestActivity extends BaseCarActivity<CarPropertyService>
        implements View.OnClickListener, ICarPropertyService.IOnProperty {
    private TextInputEditText mPropertyIdText;
    private TextInputEditText mPropertyAreaText;

    private TextInputEditText mPropertySetText;
    private MaterialTextView mPropertyGetText;
    private MaterialTextView mChangeText;

    @Override
    protected CarConfiguration<CarPropertyService> buildCarLayout() {
        return new CarConfiguration.Builder<CarPropertyService>()
                .createServiceImpl(new CarPropertyService())
                .layoutResId(R.layout.activity_car_property_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPropertyIdText = findViewById(R.id.propertyId);
        mPropertyAreaText = findViewById(R.id.propertyArea);

        mPropertySetText = findViewById(R.id.setValue);
        findViewById(R.id.set).setOnClickListener(this);
        addToChangeable(R.id.set);
        findViewById(R.id.setMulti).setOnClickListener(this);
        addToChangeable(R.id.setMulti);

        mPropertyGetText = findViewById(R.id.getValue);
        findViewById(R.id.get).setOnClickListener(this);
        addToChangeable(R.id.get);

        findViewById(R.id.subscribe).setOnClickListener(this);
        addToChangeable(R.id.subscribe);
        findViewById(R.id.unsubscribe).setOnClickListener(this);
        addToChangeable(R.id.unsubscribe);
        mChangeText = findViewById(R.id.subscribeChangeEvent);

        findViewById(R.id.setVehicleSNWrite).setOnClickListener(this);
        addToChangeable(R.id.setVehicleSNWrite);
        findViewById(R.id.setVehicleSNRead).setOnClickListener(this);
        addToChangeable(R.id.setVehicleSNRead);
        findViewById(R.id.getHWVersion).setOnClickListener(this);
        addToChangeable(R.id.getHWVersion);
        findViewById(R.id.getROMVersion).setOnClickListener(this);
        addToChangeable(R.id.getROMVersion);
        findViewById(R.id.getMCUVersion).setOnClickListener(this);
        addToChangeable(R.id.getMCUVersion);
        findViewById(R.id.getVINCode).setOnClickListener(this);
        addToChangeable(R.id.getVINCode);

        getService().registerPropertyChanged(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == null) {
            return;
        }

        final int viewId = view.getId();
        if (viewId == R.id.set) {
            toSetProperty();
        } else if (viewId == R.id.setMulti) {
            testMultiProperties();
        } else if (viewId == R.id.get) {
            toGetProperty();
        } else if (viewId == R.id.subscribe) {
            toSubscribeProperty();
        } else if (viewId == R.id.unsubscribe) {
            toUnsubscribeProperty();
            mChangeText.setText("");
        }
    }

    @Override
    public void onPropertyChanged(int propertyId, int areaId, @NonNull Object value) {
        final String content = "Property[ id: 0x" + Integer.toHexString(propertyId).toUpperCase() +
                ", area: 0x" + Integer.toHexString(areaId).toUpperCase() +
                ", value: " + CarPropertyUtil.propertyValueToString(propertyId, value) + " ]";
        Log.e(ICarPropertyService.TAG, "onPropertyValueChanged, " + content);
        final int[] property = parseProperty();
        if (property == null || property.length < 2) {
            return;
        }

        if (propertyId == property[0]) {
            mChangeText.setText(content);
        }
    }

    private void toSetProperty() {
        final int[] property = parseProperty();
        if (property == null || property.length < 2) {
            return;
        }

        try {
            final String valueString = mPropertySetText.getText().toString();
            Log.e(ICarPropertyService.TAG, "Set property: 0x" + Integer.toHexString(
                    property[0]) + ", value: " + valueString);
            getService().setProperty(
                    new ICarPropertyService.Prop(property[0], property[1], valueString));
        } catch (Exception exception) {
            Log.e(ICarPropertyService.TAG, "Set property got exception, " + exception.getMessage());
        }
    }

    private void toGetProperty() {
        final int[] property = parseProperty();
        if (property == null || property.length < 2) {
            Log.e(ICarPropertyService.TAG, "Property illegal.");
            return;
        }

        try {
            final String valueString = getService().getProperty(property[0], property[1]);
            mPropertyGetText.setText(valueString);
        } catch (Exception exception) {
            Log.e(ICarPropertyService.TAG, "Get property got exception, " + exception.getMessage());
        }
    }

    private void testMultiProperties() {
        final List<ICarPropertyService.Prop> properties = new ArrayList<>();
        properties.add(new ICarPropertyService.Prop(0x21403001, 0, "2"));
        properties.add(new ICarPropertyService.Prop(0x21403003, 0, "1"));
        properties.add(new ICarPropertyService.Prop(0x21408008, 0, "10"));
        getService().setProperties(properties);
    }

    private void toSubscribeProperty() {
        final int[] property = parseProperty();
        if (property == null || property.length < 2) {
            return;
        }

        getService().subscribeProperty(property[0]);
    }

    private void toUnsubscribeProperty() {
        final int[] property = parseProperty();
        if (property == null || property.length < 2) {
            return;
        }

        getService().unsubscribeProperty(property[0]);
    }

    private int[] parseProperty() {
        int propertyId = -1;
        try {
            final String propertyIdString = mPropertyIdText.getText().toString();
            if (propertyIdString.startsWith("0x")) {
                propertyId = Integer.parseInt(propertyIdString.replace("0x", ""), 16);
            } else {
                propertyId = Integer.parseInt(propertyIdString);
            }
        } catch (Exception exception) {
            Log.e(ICarPropertyService.TAG,
                    "Parse property id got exception, " + exception.getMessage());
        }
        if (propertyId <= 0) {
            Log.w(ICarPropertyService.TAG, "Invalid property id: " + propertyId);
            return null;
        }

        int areaId = 0;
        try {
            final String areaIdString = mPropertyAreaText.getText().toString();
            if (areaIdString.startsWith("0x")) {
                areaId = Integer.parseInt(areaIdString.replace("0x", ""), 16);
            } else {
                areaId = Integer.parseInt(areaIdString);
            }
        } catch (Exception exception) {
            Log.e(ICarPropertyService.TAG,
                    "Parse area id got exception, " + exception.getMessage());
        }
        return new int[]{propertyId, areaId};
    }
}