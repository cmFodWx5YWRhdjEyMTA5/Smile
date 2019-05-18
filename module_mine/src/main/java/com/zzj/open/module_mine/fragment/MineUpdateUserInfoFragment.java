package com.zzj.open.module_mine.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_mine.BR;
import com.zzj.open.module_mine.R;
import com.zzj.open.module_mine.databinding.MineFragmentUpdateuserBinding;

import net.qiujuer.genius.ui.compat.UiCompat;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @desc : 登录成功后设置用户信息界面
 * @version: 1.0
 */
public class MineUpdateUserInfoFragment extends BaseFragment<MineFragmentUpdateuserBinding,BaseViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.mine_fragment_updateuser;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 初始化背景
        Glide.with(this)
                .load(R.mipmap.bg_src_tianjin)
                .apply(new RequestOptions().centerCrop())//居中剪切
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        // 使用适配类进行包装
                        resource = DrawableCompat.wrap(resource);
                        resource.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN); // 设置着色的效果和颜色，蒙板模式
                        binding.llBg.setBackground(resource);
                    }
                });

        new ToolbarHelper(_mActivity, binding.toolbar,"仅差一步了！",false);
    }
}
