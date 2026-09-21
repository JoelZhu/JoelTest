package com.joelzhu.joeltest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.joelzhu.joeltest.info.CarInfoTestActivity;
import com.joelzhu.joeltest.input.CarInputTestActivity;
import com.joelzhu.joeltest.mbcheryota.CarMBCheryOTAActivity;
import com.joelzhu.joeltest.mbrestriction.CarMBRestrictionTestActivity;
import com.joelzhu.joeltest.power.CarPowerTestActivity;
import com.joelzhu.joeltest.property.CarPropertyTestActivity;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Map<Integer, Class<? extends AppCompatActivity>> ACTIVITIES = Map.of(
            R.id.carProperty, CarPropertyTestActivity.class,
            R.id.carInfo, CarInfoTestActivity.class,
            R.id.carInput, CarInputTestActivity.class,
            R.id.carPower, CarPowerTestActivity.class,
            R.id.carMBRestriction, CarMBRestrictionTestActivity.class,
            R.id.carMBCheryOTA, CarMBCheryOTAActivity.class
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (final Integer resId : ACTIVITIES.keySet()) {
            if (resId != null) {
                findViewById(resId).setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View view) {
        Class<? extends AppCompatActivity> activity = ACTIVITIES.get(view.getId());
        if (activity == null) {
            return;
        }
        final Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}