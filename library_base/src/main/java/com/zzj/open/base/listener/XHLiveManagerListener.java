package com.zzj.open.base.listener;

import com.starrtc.starrtcsdk.api.XHConstants;
import com.starrtc.starrtcsdk.apiInterface.IXHLiveManagerListener;
import com.starrtc.starrtcsdk.core.im.message.XHIMMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class XHLiveManagerListener implements IXHLiveManagerListener {

    @Override
    public void onActorJoined(String liveID, String actorID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("liveID",liveID);
            jsonObject.put("actorID",actorID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActorLeft(String liveID, String actorID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("liveID",liveID);
            jsonObject.put("actorID",actorID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplyBroadcast(String liveID, String applyUserID) {
    }

    @Override
    public void onApplyResponsed(String liveID, XHConstants.XHLiveJoinResult result) {
    }

    @Override
    public void onInviteBroadcast(String liveID, String inviteUserID) {
    }

    @Override
    public void onInviteResponsed(String liveID, XHConstants.XHLiveJoinResult result) {
    }

    @Override
    public void onLiveError(String liveID, String error) {
    }

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
    public void onCommandToStopPlay(String liveID) {
    }

    @Override
    public void onReceivedMessage(XHIMMessage message) {
    }

    @Override
    public void onReceivePrivateMessage(XHIMMessage message) {
    }

    @Override
    public void onReceiveRealtimeData(byte[] data, String upId) {




    }
}
