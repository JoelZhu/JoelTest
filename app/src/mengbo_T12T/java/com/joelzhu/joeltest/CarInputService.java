package com.joelzhu.joeltest;

import android.car.Car;
import android.car.CarOccupantZoneManager;
import android.car.input.CarInputManager;
import android.car.input.RotaryEvent;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.joelzhu.joeltest.input.AbstractCarInputService;

import java.util.List;

public class CarInputService extends AbstractCarInputService<CarInputManager>
        implements CarInputManager.CarInputCaptureCallback {
    @Nullable
    @Override
    protected String serviceName() {
        return Car.CAR_INPUT_SERVICE;
    }

    @Override
    public void registerInputListener() {
        if (mManager == null) {
            return;
        }

        mManager.requestInputEventCapture(CarOccupantZoneManager.DISPLAY_TYPE_MAIN,
                new int[]{CarInputManager.INPUT_TYPE_ALL_INPUTS},
                CarInputManager.CAPTURE_REQ_FLAGS_ALLOW_DELAYED_GRANT, this);
    }

    @Override
    public void unregisterInputListener() {
        if (mManager == null) {
            return;
        }

        mManager.releaseInputEventCapture(CarOccupantZoneManager.DISPLAY_TYPE_MAIN);
    }

    @Override
    public final void onKeyEvents(int targetDisplayType, @NonNull List<KeyEvent> keyEvents) {
        if (mEventChange == null) {
            return;
        }

        mEventChange.onKeyEvents(keyEvents);
    }

    @Override
    public final void onRotaryEvents(int targetDisplayType,
            @NonNull List<RotaryEvent> rotaryEvents) {
        if (mEventChange == null) {
            return;
        }

        mEventChange.onRotaryEvents(rotaryEvents);
    }

    @Override
    public final void onCaptureStateChanged(int targetDisplayType, @NonNull int[] inputTypes) {
    }
}