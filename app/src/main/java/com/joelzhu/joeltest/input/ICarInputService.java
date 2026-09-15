package com.joelzhu.joeltest.input;

import android.car.input.RotaryEvent;
import android.view.KeyEvent;

import com.joelzhu.joeltest.base.ICarService;

import java.util.List;

public interface ICarInputService extends ICarService {
    interface IOnEvent {
        void onKeyEvents(final List<KeyEvent> events);

        void onRotaryEvents(final List<RotaryEvent> events);
    }

    void registerInputListener();

    void unregisterInputListener();
}