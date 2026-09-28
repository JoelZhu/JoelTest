package com.joelzhu.joeltest.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.joelzhu.joeltest.property.ICarPropertyService;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCarActivity<CarService extends BaseCarService<?>>
        extends AppCompatActivity
        implements View.OnClickListener, ICarService.IOnServiceConnectState {
    private final CarConfiguration<CarService> mCarLayout = buildCarLayout();

    private MaterialButton mConnectButton;
    private MaterialButton mDisconnectButton;

    private List<View> mChangeableViews = new ArrayList<>();

    protected abstract CarConfiguration<CarService> buildCarLayout();

    protected void onManagerReady() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mCarLayout.getLayoutRes());

        mConnectButton = findViewById(mCarLayout.getConnectResId());
        mConnectButton.setOnClickListener(this);
        mDisconnectButton = findViewById(mCarLayout.getDisconnectResId());
        mDisconnectButton.setOnClickListener(this);

        mCarLayout.getService().registerServiceState(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableUI();
    }

    @CallSuper
    @Override
    public void onClick(View view) {
        if (view == null) {
            return;
        }

        final int viewId = view.getId();
        if (viewId == mCarLayout.getConnectResId()) {
            mConnectButton.setEnabled(false);
            mCarLayout.getService().connectCar(this);
        } else if (viewId == mCarLayout.getDisconnectResId()) {
            mCarLayout.getService().disconnectCar();
            disableUI();
        }
    }

    @Override
    public final void onConnectStateChanged(boolean isConnected) {
        Log.d(ICarPropertyService.TAG, "onLifecycleChanged, connected: " + isConnected);
        if (isConnected) {
            enableUI();
            onManagerReady();
        } else {
            disableUI();
        }
    }

    protected CarService getService() {
        return mCarLayout.getService();
    }

    protected void addToChangeable(final @IdRes int viewResId) {
        final View view = findViewById(viewResId);
        if (view != null) {
            mChangeableViews.add(view);
        }
    }

    private void disableUI() {
        mConnectButton.setEnabled(true);
        mDisconnectButton.setEnabled(false);
        for (final View view : mChangeableViews) {
            view.setEnabled(false);
        }
    }

    private void enableUI() {
        mConnectButton.setEnabled(false);
        mDisconnectButton.setEnabled(true);
        for (final View view : mChangeableViews) {
            view.setEnabled(true);
        }
    }
}