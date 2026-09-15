package com.joelzhu.joeltest;

import android.car.Car;
import android.car.CarOccupantZoneManager;
import android.car.input.CarInputManager;
import android.car.input.RotaryEvent;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.joelzhu.joeltest.base.BaseCarService;
import com.joelzhu.joeltest.input.ICarInputService;

import java.util.List;

public class CarInputService extends BaseCarService implements ICarInputService,
        CarInputManager.CarInputCaptureCallback {
    private CarInputManager mInputManager = null;

    private ICarInputService.IOnEvent mEventChange = null;

    @Override
    protected void onCarConnected(final @NonNull Car car) {
        mInputManager = (CarInputManager) car.getCarManager(Car.CAR_INPUT_SERVICE);
    }

    @Override
    public void registerInputListener() {
        if (mInputManager == null) {
            return;
        }

        mInputManager.requestInputEventCapture(this, CarInputManager.TARGET_DISPLAY_TYPE_MAIN,
                new int[]{CarInputManager.INPUT_TYPE_ALL_INPUTS},
                CarInputManager.CAPTURE_REQ_FLAGS_ALLOW_DELAYED_GRANT);
    }

    @Override
    public void unregisterInputListener() {
        if (mInputManager == null) {
            return;
        }

        mInputManager.releaseInputEventCapture(CarOccupantZoneManager.DISPLAY_TYPE_MAIN);
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

//        mEventChange.onRotaryEvents(rotaryEvents);
    }

    @Override
    public final void onCaptureStateChanged(int targetDisplayType, @NonNull int[] inputTypes) {
    }

    public void registerEventChanged(final ICarInputService.IOnEvent event) {
        mEventChange = event;
    }
}