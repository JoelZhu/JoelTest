package com.joelzhu.joeltest.base;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public final class CarConfiguration<CarService extends BaseCarService> {
    private CarService mService;

    @LayoutRes
    private int mLayoutRes;

    @IdRes
    private int mConnectResId;

    @IdRes
    private int mDisconnectResId;

    private CarConfiguration() {
    }

    public static class Builder<CarService extends BaseCarService> {
        private final CarConfiguration<CarService> mConfiguration = new CarConfiguration<>();

        public Builder<CarService> createServiceImpl(final CarService service) {
            this.mConfiguration.mService = service;
            return this;
        }

        public Builder<CarService> layoutResId(final @LayoutRes int layoutRes) {
            this.mConfiguration.mLayoutRes = layoutRes;
            return this;
        }

        public Builder<CarService> connectResId(final @IdRes int connectResId) {
            this.mConfiguration.mConnectResId = connectResId;
            return this;
        }

        public Builder<CarService> disconnectResId(final @IdRes int disconnectResId) {
            this.mConfiguration.mDisconnectResId = disconnectResId;
            return this;
        }

        public CarConfiguration<CarService> build() {
            return mConfiguration;
        }
    }

    public CarService getService() {
        return mService;
    }

    public int getLayoutRes() {
        return mLayoutRes;
    }

    public int getConnectResId() {
        return mConnectResId;
    }

    public int getDisconnectResId() {
        return mDisconnectResId;
    }
}