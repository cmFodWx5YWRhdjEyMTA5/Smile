package com.zzj.open.base.listener;

import com.starrtc.starrtcsdk.apiInterface.IXHMeetingManagerListener;
import com.starrtc.starrtcsdk.core.im.message.XHIMMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class XHMeetingManagerListener implements IXHMeetingManagerListener {
    @Override
    public void onJoined(String meetingID, String userID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("meetingID",meetingID);
            jsonObject.put("userID",userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLeft(String meetingID, String userID) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("meetingID",meetingID);
            jsonObject.put("userID",userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMeetingError(String meetingID, String error) {
    }

    @Override
    public void onMembersUpdated(int membersNumber) {
    }

    @Override
    public void onSelfKicked() {
    }

    @Override
    public void onSelfMuted(int seconds) {
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
