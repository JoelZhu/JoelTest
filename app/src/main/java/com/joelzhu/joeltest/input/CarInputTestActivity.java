package com.joelzhu.joeltest.input;

import android.car.input.RotaryEvent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.textview.MaterialTextView;
import com.joelzhu.joeltest.CarInputService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;

import java.util.List;

public class CarInputTestActivity extends BaseCarActivity<CarInputService>
        implements View.OnClickListener, ICarInputService.IOnEvent {
    private MaterialTextView mEventChange;

    @Override
    protected CarConfiguration<CarInputService> buildCarLayout() {
        return new CarConfiguration.Builder<CarInputService>()
                .createServiceImpl(new CarInputService())
                .layoutResId(R.layout.activity_car_input_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.subscribeKeyEvent).setOnClickListener(this);
        addToChangeable(R.id.subscribeKeyEvent);
        findViewById(R.id.unsubscribeKeyEvent).setOnClickListener(this);
        addToChangeable(R.id.unsubscribeKeyEvent);

        mEventChange = findViewById(R.id.onEventChange);
        mEventChange.setMovementMethod(ScrollingMovementMethod.getInstance());

        getService().registerEventChanged(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.subscribeKeyEvent) {
            getService().registerInputListener();
        } else if (view.getId() == R.id.unsubscribeKeyEvent) {
            getService().unregisterInputListener();
        }
    }

    @Override
    public void onKeyEvents(final List<KeyEvent> events) {
        for (final KeyEvent event : events) {
            mEventChange.append(event.toString() + "\r\n");
        }
    }

    @Override
    public void onRotaryEvents(final List<RotaryEvent> events) {
        for (final RotaryEvent event : events) {
            mEventChange.append(event.toString() + "\r\n");
        }
    }
}