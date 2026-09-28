package com.joelzhu.joeltest.mbrestriction;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.joelzhu.joeltest.MBRestrictionService;
import com.joelzhu.joeltest.R;
import com.joelzhu.joeltest.base.BaseCarActivity;
import com.joelzhu.joeltest.base.CarConfiguration;

// FIXME: 待优化，紧急写的demo
public class MBRestrictionTestActivity extends BaseCarActivity<MBRestrictionService>
        implements View.OnClickListener {
    private MaterialTextView mScreenChange;

    @Override
    protected CarConfiguration<MBRestrictionService> buildCarLayout() {
        return new CarConfiguration.Builder<MBRestrictionService>()
                .createServiceImpl(new MBRestrictionService())
                .layoutResId(R.layout.activity_car_mb_restriction_test)
                .connectResId(R.id.connectCarService)
                .disconnectResId(R.id.disconnectCarService)
                .build();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.notifyCarService).setOnClickListener(this);
        addToChangeable(R.id.notifyCarService);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.notifyCarService) {
            final int restrictionType = Integer.parseInt(
                    ((TextInputEditText) findViewById(R.id.restrictionType)).getText().toString());
            final String packageName = ((TextInputEditText) findViewById(
                    R.id.packageName)).getText().toString();
            final boolean isActive = ((MaterialButtonToggleGroup) findViewById(
                    R.id.isActiveGroup)).getCheckedButtonId() == R.id.buttonActive;
            getService().notifyActiveState(restrictionType, packageName, isActive);
        }
    }
}