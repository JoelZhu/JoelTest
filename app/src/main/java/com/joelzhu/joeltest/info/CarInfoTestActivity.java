package com.joelzhu.joeltest.info;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.CarInfoService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;

public class CarInfoTestActivity extends BaseCarActivity<CarInfoService>
        implements View.OnClickListener {
    @Override
    protected CarConfiguration<CarInfoService> buildCarLayout() {
        return new CarConfiguration.Builder<CarInfoService>()
                .createServiceImpl(new CarInfoService())
                .layoutResId(R.layout.activity_car_info_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.getHWVersion).setOnClickListener(this);
        addToChangeable(R.id.getHWVersion);
        findViewById(R.id.getROMVersion).setOnClickListener(this);
        addToChangeable(R.id.getROMVersion);
        findViewById(R.id.getMCUVersion).setOnClickListener(this);
        addToChangeable(R.id.getMCUVersion);
        findViewById(R.id.getVINCode).setOnClickListener(this);
        addToChangeable(R.id.getVINCode);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == null) {
            return;
        }

        final int viewId = view.getId();
        if (viewId == R.id.getHWVersion) {
            final String version = getService().getHWVersion();
            Log.d(ICarInfoService.TAG, "Got HW version: " + version);
        } else if (viewId == R.id.getROMVersion) {
            final String version = getService().getSOCVersion();
            Log.d(ICarInfoService.TAG, "Got ROM version: " + version);
        } else if (viewId == R.id.getMCUVersion) {
            final String version = getService().getMCUVersion();
            Log.d(ICarInfoService.TAG, "Got MCU version: " + version);
        } else if (viewId == R.id.getVINCode) {
            final String version = getService().getVINCode();
            Log.d(ICarInfoService.TAG, "Got VIN code: " + version);
        }
    }
}