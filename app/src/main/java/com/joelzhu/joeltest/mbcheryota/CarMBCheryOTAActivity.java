package com.joelzhu.joeltest.mbcheryota;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.joelzhu.joeltest.CarCheryOTAService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;
import com.joelzhu.joeltest.mbcheryota.entity.AuthInfo;
import com.joelzhu.joeltest.mbcheryota.entity.SecureInfo;

public class CarMBCheryOTAActivity extends BaseCarActivity<CarCheryOTAService>
        implements View.OnClickListener, ICarCheryOTAService.IMBOTACallback {
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

        findViewById(R.id.subscribe).setOnClickListener(this);
        addToChangeable(R.id.subscribe);
        findViewById(R.id.unsubscribe).setOnClickListener(this);
        addToChangeable(R.id.unsubscribe);
        findViewById(R.id.getSecureInfoSync).setOnClickListener(this);
        addToChangeable(R.id.getSecureInfoSync);
        findViewById(R.id.getSecureInfoAsync).setOnClickListener(this);
        addToChangeable(R.id.getSecureInfoAsync);
        findViewById(R.id.getAuthInfoSync).setOnClickListener(this);
        addToChangeable(R.id.getAuthInfoSync);
        findViewById(R.id.getAuthInfoAsync).setOnClickListener(this);
        addToChangeable(R.id.getAuthInfoAsync);
        findViewById(R.id.sendPowerOn).setOnClickListener(this);
        addToChangeable(R.id.sendPowerOn);
        findViewById(R.id.sendPowerOff).setOnClickListener(this);
        addToChangeable(R.id.sendPowerOff);
        findViewById(R.id.sendPowerContinuous).setOnClickListener(this);
        addToChangeable(R.id.sendPowerContinuous);

        getService().registerListenerInner(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.subscribe) {
            getService().registerListener();
        } else if (view.getId() == R.id.unsubscribe) {
            getService().unregisterListener();
        } else if (view.getId() == R.id.getSecureInfoSync) {
            new Thread(() -> {
                final SecureInfo info = getService().getSecureInfoSync();
                Log.d(ICarCheryOTAService.TAG,
                        "Got secure info: " + (info == null ? "null" : info));
            }).start();
        } else if (view.getId() == R.id.getSecureInfoAsync) {
            getService().getSecureInfoAsync();
        } else if (view.getId() == R.id.getAuthInfoSync) {
            new Thread(() -> {
                final AuthInfo info = getService().getAuthInfoSync(
                        new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8});
                Log.d(ICarCheryOTAService.TAG,
                        "Got auth info: " + (info == null ? "null" : info));
            }).start();
        } else if (view.getId() == R.id.getAuthInfoAsync) {
            getService().getAuthInfoAsync(new byte[]{0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8});
        } else if (view.getId() == R.id.sendPowerOn) {
            getService().sendPowerOn();
        } else if (view.getId() == R.id.sendPowerOff) {
            getService().sendPowerOff();
        } else if (view.getId() == R.id.sendPowerContinuous) {
            getService().sendPowerContinuous();
        }
    }

    @Override
    public void onSecureInfoChanged(SecureInfo info) {
        Log.d(ICarCheryOTAService.TAG, "onSecureInfoChanged, " + info);
    }

    @Override
    public void onAuthenticationChanged(AuthInfo info) {
        Log.d(ICarCheryOTAService.TAG, "onAuthInfoChanged, " + info);
    }

    @Override
    public void onPowerOnState(int state) {
        Log.d(ICarCheryOTAService.TAG, "onPowerOnState, state: " + state);
    }

    @Override
    public void onPowerOffState(int state) {
        Log.d(ICarCheryOTAService.TAG, "onPowerOffState, state: " + state);
    }
}