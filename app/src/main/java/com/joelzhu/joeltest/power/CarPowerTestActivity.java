package com.joelzhu.joeltest.power;

import static com.joelzhu.joeltest.power.ICarPowerService.TAG;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textview.MaterialTextView;
import com.joelzhu.joeltest.CarPowerService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;

import java.util.concurrent.CompletableFuture;

public class CarPowerTestActivity extends BaseCarActivity<CarPowerService>
        implements View.OnClickListener, ICarPowerService.IOnScreen, ICarPowerService.IOnState {
    private MaterialTextView mScreenChange;

    @Override
    protected CarConfiguration<CarPowerService> buildCarLayout() {
        return new CarConfiguration.Builder<CarPowerService>()
                .createServiceImpl(new CarPowerService())
                .layoutResId(R.layout.activity_car_power_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.subscribeCarPower).setOnClickListener(this);
        addToChangeable(R.id.subscribeCarPower);
        findViewById(R.id.unsubscribeCarPower).setOnClickListener(this);
        addToChangeable(R.id.unsubscribeCarPower);

        findViewById(R.id.screenOn).setOnClickListener(this);
        addToChangeable(R.id.screenOn);
        findViewById(R.id.screenOff).setOnClickListener(this);
        addToChangeable(R.id.screenOff);
        findViewById(R.id.screenState).setOnClickListener(this);
        addToChangeable(R.id.screenState);
        findViewById(R.id.switchToHost).setOnClickListener(this);
        addToChangeable(R.id.switchToHost);
        findViewById(R.id.switchToDevice).setOnClickListener(this);
        addToChangeable(R.id.switchToDevice);

        mScreenChange = findViewById(R.id.screenChange);
        mScreenChange.setMovementMethod(ScrollingMovementMethod.getInstance());

        getService().setupExecutor(this);
        getService().setListenerWithCompletion();
        getService().registerScreenStateChanged(this);
        getService().registerOnStateChanged(this);
    }

    @Override
    protected void onManagerReady() {
        getService().setListenerWithCompletion();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.subscribeCarPower) {
            getService().registerScreenStateCallback();
        } else if (view.getId() == R.id.unsubscribeCarPower) {
            getService().unregisterScreenStateCallback();
        } else if (view.getId() == R.id.screenOn) {
            getService().screenOn(parseScreenType());
        } else if (view.getId() == R.id.screenOff) {
            getService().screenOff(parseScreenType());
        } else if (view.getId() == R.id.screenState) {
            final boolean isScreenOn = getService().isScreenOn(parseScreenType());
            Log.d(TAG, "Is screen on: " + isScreenOn);
        } else if (view.getId() == R.id.switchToHost) {
            getService().switchToHost();
        } else if (view.getId() == R.id.switchToDevice) {
            getService().switchToDevice();
        }
    }

    @Override
    public void onScreenStateChanged(final int screenType, final boolean isScreenOn) {
        final String content = "Screen state changed, type: " + screenType + ", on: " + isScreenOn;
        Log.d(TAG, content);
        runOnUiThread(() -> mScreenChange.setText(content));
    }

    @Override
    public void onReleaseResource(final CompletableFuture<Void> future) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Log.d(TAG, "Sleep finished, to complete it.");
                future.complete(null);
            } catch (InterruptedException exception) {
                Log.e(TAG, "Sleep got exception, " + exception.getMessage());
            }
        }).start();
    }

    private int parseScreenType() {
        int type = -1;
        try {
            final String typeString = ((EditText) findViewById(R.id.type)).getText().toString();
            type = Integer.parseInt(typeString);
        } catch (Exception exception) {
            Log.e(TAG, "Parse screen type got exception, " + exception.getMessage());
        }

        return type;
    }
}