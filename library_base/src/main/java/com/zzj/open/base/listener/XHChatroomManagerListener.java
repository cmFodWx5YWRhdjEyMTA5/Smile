package com.zzj.open.base.listener;

import com.starrtc.starrtcsdk.apiInterface.IXHChatroomManagerListener;
import com.starrtc.starrtcsdk.core.im.message.XHIMMessage;

public class XHChatroomManagerListener implements IXHChatroomManagerListener {
    @Override
    public void onMembersUpdated(int number) {
    }

    @Override
    public void onSelfKicked() {
    }

    @Override
    public void onSelfMuted(int seconds) {
    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onReceivedMessage(XHIMMessage message) {
    }

    @Override
    public void onReceivePrivateMessage(XHIMMessage message) {
    }
}
