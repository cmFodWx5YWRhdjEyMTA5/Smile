package com.zzj.open.base.listener;

import com.starrtc.starrtcsdk.apiInterface.IXHVoipP2PManagerListener;
import com.starrtc.starrtcsdk.socket.StarErrorCode;

public class XHVoipP2PManagerListener implements IXHVoipP2PManagerListener {
    @Override
    public void onCalling(String fromID) {

//        HistoryBean historyBean = new HistoryBean();
//        historyBean.setType(CoreDB.HISTORY_TYPE_VOIP);
//        historyBean.setLastTime(new SimpleDateFormat("MM-dd HH:mm").format(new java.util.Date()));
//        historyBean.setConversationId(fromID);
//        historyBean.setNewMsgCount(1);
//        MLOC.setHistory(historyBean,false);

    }

    @Override
    public void onCancled(String fromID) {

    }

    @Override
    public void onRefused(String fromID) {
    }

    @Override
    public void onBusy(String fromID) {
    }

    @Override
    public void onConnected(String fromID) {
    }

    @Override
    public void onHangup(String fromID) {
    }

    @Override
    public void onError(String errorCode) {
    }

    @Override
    public void onReceiveRealtimeData(byte[] data) {

    }
}
