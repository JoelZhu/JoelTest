package com.joelzhu.joeltest.mbcheryota;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.CarCheryOTAService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CarMBCheryOTAActivity extends BaseCarActivity<CarCheryOTAService>
        implements View.OnClickListener {
    @Override
    protected CarConfiguration<CarCheryOTAService> buildCarLayout() {
        return new CarConfiguration.Builder<CarCheryOTAService>()
                .createServiceImpl(new CarCheryOTAService())
                .layoutResId(R.layout.activity_car_chery_ota_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.getSecureInfo).setOnClickListener(this);
        addToChangeable(R.id.getSecureInfo);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.getSecureInfo) {
            new Thread(() -> {
                final SecureInfo info = getService().getSecureInfo();
                Log.d(ICarCheryOTAService.TAG,
                        "Got secure info: " + (info == null ? "null" : info));
            }).start();
        }
    }
}