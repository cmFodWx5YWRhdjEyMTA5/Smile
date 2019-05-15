package com.zzj.open.base.listener;

import com.starrtc.starrtcsdk.api.XHConstants;
import com.starrtc.starrtcsdk.apiInterface.IXHLoginManagerListener;

public class XHLoginManagerListener implements IXHLoginManagerListener {

    @Override
    public void onConnectionStateChanged(XHConstants.XHSDKConnectionState state) {
    }

    @Override
    public void onKickedByOtherDeviceLogin() {
    }

    @Override
    public void onLogout() {

    }
}
