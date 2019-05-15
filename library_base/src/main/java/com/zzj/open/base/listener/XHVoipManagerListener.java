package com.zzj.open.base.listener;

import com.blankj.utilcode.util.LogUtils;
import com.starrtc.starrtcsdk.apiInterface.IXHVoipManagerListener;
import com.starrtc.starrtcsdk.socket.StarErrorCode;
import com.zzj.open.base.bean.NotifiyEventBean;
import com.zzj.open.base.utils.AEvent;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

public class XHVoipManagerListener implements IXHVoipManagerListener {
    @Override
    public void onCalling(String fromID) {
        EventBus.getDefault().post(new NotifiyEventBean(1,fromID));
        LogUtils.e("--------onCalling-----------"+fromID);
//        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_CALLING,true,fromID);
    }

    @Override
    public void onCancled(String fromID) {
//        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT,fromID));
        LogUtils.e("--------onCancled-----------"+fromID);
    }

    @Override
    public void onRefused(String fromID) {
        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT_VOIP_REV_REFUSED,fromID));
        LogUtils.e("-----onRefused--->1111111111111111111");
        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_REFUSED,true,fromID);
    }

    @Override
    public void onBusy(String fromID) {
        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT_VOIP_REV_BUSY,fromID));
        LogUtils.e("-----onBusy--->1111111111111111111");
//        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_BUSY,true,fromID);
    }

    @Override
    public void onConnected(String fromID) {
        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT_VOIP_REV_CONNECT,fromID));
        LogUtils.e("-----onConnected--->1111111111111111111");
//        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_CONNECT,true,fromID);
    }

    @Override
    public void onHangup(String fromID) {
        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT_VOIP_REV_HANGUP,fromID));
        LogUtils.e("-----onHangup--->1111111111111111111");
//        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_HANGUP,true,fromID);
    }

    @Override
    public void onError(String errorCode) {
        EventBus.getDefault().post(new NotifiyEventBean(AEvent.AEVENT_VOIP_REV_ERROR,errorCode));
        LogUtils.e("-----onError--->1111111111111111111");
//        AEvent.notifyListener(AEvent.AEVENT_VOIP_REV_ERROR,true, StarErrorCode.getErrorCode(errorCode));
    }

    @Override
    public void onReceiveRealtimeData(byte[] data) {

    }
}
