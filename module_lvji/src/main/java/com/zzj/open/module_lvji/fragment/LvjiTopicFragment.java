package com.zzj.open.module_lvji.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.databinding.LvjiFragmentNearbyBinding;
import com.zzj.open.module_lvji.databinding.LvjiFragmentTopicBinding;
import com.zzj.open.module_lvji.model.LvjiTopicModel;
import com.zzj.open.module_lvji.viewmodel.LvjiTopicViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/18
 * @desc : 附近
 * @version: 1.0
 */
public class LvjiTopicFragment extends BaseFragment<LvjiFragmentTopicBinding, LvjiTopicViewModel> {

    private BaseQuickAdapter<LvjiTopicModel, BaseViewHolder> adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_topic;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        binding.recyclerView.setAdapter(adapter = new BaseQuickAdapter<LvjiTopicModel, BaseViewHolder>(R.layout.lvji_item_topic_layout) {
            @Override
            protected void convert(BaseViewHolder helper, LvjiTopicModel item) {
                helper.setText(R.id.tv_topic_title,item.getTopicTitle());
                helper.setText(R.id.tv_topic_content,item.getTopicContent());
                helper.setText(R.id.tv_topic_name_value,item.getUserName());
                Glide.with(mContext).load(item.getTopicPicture()).apply(new RequestOptions()
                        .placeholder(R.mipmap.bg_src_tianjin)).into((ImageView) helper.getView(R.id.iv_topic_picture));
            }
        });

        viewModel.getTopicList();

        viewModel.topicModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                adapter.setNewData(viewModel.topicModels.get());
            }
        });

    }


}
