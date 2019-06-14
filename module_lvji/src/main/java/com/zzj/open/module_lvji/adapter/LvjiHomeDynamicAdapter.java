package com.zzj.open.module_lvji.adapter;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zzj.open.base.utils.PortraitView;
import com.zzj.open.base.utils.WechatTimeUtils;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.model.LvjiPublishModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/12 12:33
 * @desc :
 * @version: 1.0
 */
public class LvjiHomeDynamicAdapter extends BaseMultiItemQuickAdapter<LvjiPublishModel, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public LvjiHomeDynamicAdapter(List<LvjiPublishModel> data) {
        super(data);
        addItemType(LvjiPublishModel.TEXT, R.layout.lvji_item_home_dynamic_text_list);
        addItemType(LvjiPublishModel.IMG_TEXT,R.layout.lvji_item_home_dynamic_img_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, LvjiPublishModel item) {
        ((PortraitView)helper.getView(R.id.pv_header)).setup(item.getFaceImage());
        helper.setText(R.id.tv_username,item.getUserName());
        helper.setText(R.id.tv_content,item.getPublishContent());
        helper.setText(R.id.tv_time, WechatTimeUtils.getTimeStringAutoShort2(item.getCreateAt(),true));
        helper.setText(R.id.tv_location, item.getPublishLocation());
        helper.addOnClickListener(R.id.iv_more);
        if(helper.getItemViewType() == LvjiPublishModel.IMG_TEXT){
            if(item.getPublishContent().isEmpty()){
                helper.setGone(R.id.tv_content,false);
            }else {
                helper.setGone(R.id.tv_content,true);
            }
            NineGridView nineGridView = helper.getView(R.id.nineGrid);
            String urlList = item.getPictureUrlList();
            String subUrlList = urlList.substring(1,urlList.length()-1);
            ArrayList<String> arrayList = new ArrayList<>();
            //处理图片
            if(subUrlList.contains(",")){
               String[] splitUrlList =  subUrlList.split(",");
               arrayList.addAll(Arrays.asList(splitUrlList));
            }else {
                arrayList.add(subUrlList);
            }
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for(String imageUrl : arrayList){
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageUrl.trim());
                info.setBigImageUrl(imageUrl.trim());
                imageInfo.add(info);
            }
            nineGridView.setAdapter(new NineGridViewClickAdapter(mContext,imageInfo));

        }
    }
}
