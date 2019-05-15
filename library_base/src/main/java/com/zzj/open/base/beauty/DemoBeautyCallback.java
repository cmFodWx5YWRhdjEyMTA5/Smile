package com.zzj.open.base.beauty;

import com.blankj.utilcode.util.LogUtils;
import com.starrtc.starrtcsdk.core.beauty.StarVideoData;
import com.starrtc.starrtcsdk.core.beauty.XHBeautyDataCallback;

public class DemoBeautyCallback extends XHBeautyDataCallback {
    @Override
    public void onFrame(StarVideoData videoData){
//        LogUtils.d("DemoBeautyCallback","美颜数据已经接到了，不做处理，直接再丢回去"+videoData.getDataLength());
        super.backfillData(videoData);
    }
}
