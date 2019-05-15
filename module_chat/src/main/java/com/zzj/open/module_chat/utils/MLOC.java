package com.zzj.open.module_chat.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.zzj.open.module_chat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhangjt on 2017/8/17.
 */

public class MLOC {
    public static Context appContext;
    public static String agentId = "stargWeHN8Y7";
    public static String authKey = "";
    public static String userId = "";

    public static String STAR_LOGIN_URL = "ips2.starrtc.com:9920";

    public static String IM_SCHEDULE_URL = "ips2.starrtc.com:9904";
    public static String LIVE_SRC_SCHEDULE_URL = "ips2.starrtc.com:9929";
    public static String LIVE_VDN_SCHEDULE_URL = "ips2.starrtc.com:9926";
    public static String CHAT_ROOM_SCHEDULE_URL = "ips2.starrtc.com:9907";
    public static String VOIP_SCHEDULE_URL = "voip2.starrtc.com:10086";

    public static String VOIP_SERVER_URL = "129.204.145.78:10086";
    public static String IM_SERVER_URL = "129.204.145.78:19903";
    public static String LIVE_SRC_SERVER_URL = "129.204.145.78:19931";
    public static String LIVE_VDN_SERVER_URL = "129.204.145.78:19925";
    public static String CHAT_ROOM_SERVER_URL = "129.204.145.78:19906";

    public static String SERVER_TYPE_PUBLIC = "PUBLIC";
    public static String SERVER_TYPE_CUSTOM = "CUSTOM";
    public static String SERVER_TYPE = SERVER_TYPE_PUBLIC;

    public static Boolean hasLogout = false;

    public static boolean hasNewC2CMsg = false;
    public static boolean hasNewGroupMsg = false;
    public static boolean hasNewVoipMsg = false;
    public static boolean canPickupVoip = true;




    private static Boolean debug = true;
    public static void setDebug(Boolean b){
        debug = b;
    }

    public static void d(String tag, String msg){
        if(debug){
            Log.d("starSDK_demo_"+tag,msg);
        }
    }

    public static void e(String tag, String msg){
        Log.e("starSDK_demo_"+tag,msg);
    }

    private static Toast mToast;
    public static void showMsg(String str){
        try {
            if (mToast != null) {
                mToast.setText(str);
                mToast.setDuration(Toast.LENGTH_SHORT);
            } else {
                mToast = Toast.makeText(appContext, str, Toast.LENGTH_SHORT);
            }
            mToast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void showMsg(Context context , String str){
        try {
            if (mToast != null) {
                mToast.setText(str);
                mToast.setDuration(Toast.LENGTH_SHORT);
            } else {
                mToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            }
            mToast.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    static Dialog[] dialogs = new Dialog[1];
    static Timer dialogTimer ;
    static TimerTask timerTask;
    public static void showDialog(final Context context, final JSONObject data){
        try {
            final int type = data.getInt("type");// 0:c2c,1:group,2:voip
            final String farId = data.getString("farId");// 对方ID
            String msg = data.getString("msg");// 提示消息

            if(dialogs[0]==null||dialogs[0].isShowing()==false){
                dialogs[0] = new Dialog(context, R.style.dialog_notify);
                dialogs[0].setContentView(R.layout.dialog_new_msg);
                Window win = dialogs[0].getWindow();
                win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                win.setWindowAnimations(R.style.dialog_notify_animation);
                win.setGravity(Gravity.TOP);
                dialogs[0].setCanceledOnTouchOutside(true);
            }
            ((TextView) dialogs[0].findViewById(R.id.msg_info)).setText(msg);
            dialogs[0].findViewById(R.id.yes_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialogTimer!=null){
                        dialogTimer.cancel();
                        timerTask.cancel();
                        dialogTimer = null;
                        timerTask = null;
                    }
                    dialogs[0].dismiss();
                    dialogs[0] = null;
//                    if(type==0){
//                        //C2C
//                        Intent intent = new Intent(context,C2CListActivity.class);
//                        context.startActivity(intent);
//                    }else if(type==1){
//                        //Group
//                        Intent intent = new Intent(context, MessageGroupListActivity.class);
//                        context.startActivity(intent);
//                    }else if(type==2){
//                        //VOIP
//                        Intent intent = new Intent(context, VoipListActivity.class);
//                        context.startActivity(intent);
//                    }
                }
            });
            dialogs[0].show();

            if(dialogTimer!=null){
                dialogTimer.cancel();
                timerTask.cancel();
                dialogTimer = null;
                timerTask = null;
            }
            dialogTimer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(dialogs[0]!=null&&dialogs[0].isShowing()){
                        dialogs[0].dismiss();
                        dialogs[0] = null;
                    }
                }
            };
            dialogTimer.schedule(timerTask,5000);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
