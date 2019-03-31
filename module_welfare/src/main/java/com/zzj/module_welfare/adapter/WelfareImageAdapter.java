package com.zzj.module_welfare.adapter;

import android.databinding.ViewDataBinding;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.zzj.module_welfare.databinding.WelfareItemImagesBinding;
import com.zzj.module_welfare.vm.WelfareImageItemViewModel;

import java.util.Random;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/16 0:35
 * @desc :
 * @version: 1.0
 */
public class WelfareImageAdapter extends BindingRecyclerViewAdapter<WelfareImageItemViewModel> {

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, WelfareImageItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        //需要转换
       WelfareItemImagesBinding welfareItemImagesBinding = (WelfareItemImagesBinding) binding;
       //设置瀑布流图片的大小  随机的  防止复用加载时没有大小，造成白屏
       FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) welfareItemImagesBinding.imageView.getLayoutParams();
       int itemWidth = (ScreenUtils.getScreenWidth()-2*3)/2;
        int height = new Random().nextInt(200) + itemWidth;//[100,300)的随机数
        layoutParams.height = height;

    }

}
