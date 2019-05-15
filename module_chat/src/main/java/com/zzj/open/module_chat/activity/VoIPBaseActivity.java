package com.zzj.open.module_chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;


import com.starrtc.starrtcsdk.api.XHClient;
import com.zzj.open.module_chat.R;
import com.zzj.open.base.utils.AEvent;
import com.zzj.open.base.utils.IEventListener;
import com.zzj.open.module_chat.utils.MLOC;

import org.json.JSONException;
import org.json.JSONObject;

public class VoIPBaseActivity extends Activity implements IEventListener {

    @Override
    protected void onResume() {
        super.onResume();
        addListener();
    }
    @Override
    protected void onPause() {
        super.onPause();
        removeListener();
    }

    private void addListener(){
        AEvent.addListener(AEvent.AEVENT_VOIP_REV_CALLING,this);
        AEvent.addListener(AEvent.AEVENT_VOIP_P2P_REV_CALLING,this);
        AEvent.addListener(AEvent.AEVENT_C2C_REV_MSG,this);
        AEvent.addListener(AEvent.AEVENT_GROUP_REV_MSG,this);
        AEvent.addListener(AEvent.AEVENT_USER_ONLINE,this);
        AEvent.addListener(AEvent.AEVENT_USER_OFFLINE,this);
    }
    private void removeListener(){
        AEvent.removeListener(AEvent.AEVENT_VOIP_REV_CALLING,this);
        AEvent.removeListener(AEvent.AEVENT_VOIP_P2P_REV_CALLING,this);
        AEvent.removeListener(AEvent.AEVENT_C2C_REV_MSG,this);
        AEvent.removeListener(AEvent.AEVENT_GROUP_REV_MSG,this);
        AEvent.removeListener(AEvent.AEVENT_USER_ONLINE,this);
        AEvent.removeListener(AEvent.AEVENT_USER_OFFLINE,this);
    }

    @Override
    public void dispatchEvent(String aEventID, boolean success, final Object eventObj) {
        switch (aEventID){
            case AEvent.AEVENT_VOIP_REV_CALLING:
                if(!MLOC.canPickupVoip){
                    MLOC.hasNewVoipMsg = true;
                    try {
                        JSONObject alertData = new JSONObject();
                        alertData.put("type",2);
                        alertData.put("farId",eventObj.toString());
                        alertData.put("msg","收到视频通话请求");
                        MLOC.showDialog(VoIPBaseActivity.this,alertData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Intent intent = new Intent(VoIPBaseActivity.this,VoipRingingActivity.class);
                    intent.putExtra("targetId",eventObj.toString());
                    startActivity(intent);
                }
                break;
           /* case AEvent.AEVENT_VOIP_P2P_REV_CALLING:
                if(MLOC.canPickupVoip){
                    MLOC.hasNewVoipMsg = true;
                    Intent intent = new Intent(VoIPBaseActivity.this,VoipP2PRingingActivity.class);
                    intent.putExtra("targetId",eventObj.toString());
                    startActivity(intent);
                }
                break;*/
           /* case AEvent.AEVENT_C2C_REV_MSG:
                MLOC.hasNewC2CMsg = true;
                if(findViewById(R.id.c2c_new)!=null){
                    findViewById(R.id.c2c_new).setVisibility(MLOC.hasNewC2CMsg?View.VISIBLE:View.INVISIBLE);
                }
                if(findViewById(R.id.im_new)!=null) {
                    findViewById(R.id.im_new).setVisibility((MLOC.hasNewC2CMsg || MLOC.hasNewGroupMsg) ? View.VISIBLE : View.INVISIBLE);
                }
                try {
                    XHIMMessage revMsg = (XHIMMessage) eventObj;
                    JSONObject alertData = new JSONObject();
                    alertData.put("type",0);
                    alertData.put("farId",revMsg.fromId);
                    alertData.put("msg","收到一条新消息");
                    MLOC.showDialog(VoIPBaseActivity.this,alertData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case AEvent.AEVENT_GROUP_REV_MSG:
                MLOC.hasNewGroupMsg = true;
                if(findViewById(R.id.group_new)!=null){
                    findViewById(R.id.group_new).setVisibility(MLOC.hasNewGroupMsg?View.VISIBLE:View.INVISIBLE);
                }
                if(findViewById(R.id.im_new)!=null) {
                    findViewById(R.id.im_new).setVisibility((MLOC.hasNewC2CMsg || MLOC.hasNewGroupMsg) ? View.VISIBLE : View.INVISIBLE);
                }
                try {
                    XHIMMessage revMsg = (XHIMMessage) eventObj;
                    JSONObject alertData = new JSONObject();
                    alertData.put("type",1);
                    alertData.put("farId",revMsg.targetId);
                    alertData.put("msg","收到一条群消息");
                    MLOC.showDialog(VoIPBaseActivity.this,alertData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;*/
            case AEvent.AEVENT_USER_OFFLINE:
                MLOC.showMsg(VoIPBaseActivity.this,"服务已断开");
                if(findViewById(R.id.loading)!=null) {
                    if (XHClient.getInstance().getIsOnline()) {
                        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                    } else {
                        findViewById(R.id.loading).setVisibility(View.VISIBLE);
                    }
                }
                break;
            case AEvent.AEVENT_USER_ONLINE:
                if(findViewById(R.id.loading)!=null) {
                    if (XHClient.getInstance().getIsOnline()) {
                        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
                    } else {
                        findViewById(R.id.loading).setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }

}
