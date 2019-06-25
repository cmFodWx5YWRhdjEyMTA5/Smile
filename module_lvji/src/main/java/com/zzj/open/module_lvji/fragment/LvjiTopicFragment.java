package com.zzj.open.module_lvji.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.module_lvji.BR;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.databinding.LvjiFragmentNearbyBinding;
import com.zzj.open.module_lvji.databinding.LvjiFragmentTopicBinding;
import com.zzj.open.module_lvji.impl.ScaleTransformer;
import com.zzj.open.module_lvji.model.LvjiTopicModel;
import com.zzj.open.module_lvji.model.LvjiTopicTypeModel;
import com.zzj.open.module_lvji.viewmodel.LvjiTopicViewModel;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;
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

    /**
     * 顶部话题列表
     */
    private RecyclerView topBannerRecycler;
    /**
     * 话题分类列表
     */
    private RecyclerView recycler_topic_type;

    private BaseQuickAdapter<LvjiTopicModel, BaseViewHolder> adapter;
    private BaseQuickAdapter<LvjiTopicModel, BaseViewHolder> topBannerAdapter;
    private BaseQuickAdapter<LvjiTopicTypeModel, BaseViewHolder> topTypeAdapter;

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
                helper.setText(R.id.tv_topic_title, item.getTopicTitle());
                helper.setText(R.id.tv_topic_content, item.getTopicContent());
                helper.setText(R.id.tv_topic_name_value, item.getUserName());
                Glide.with(mContext).load(item.getTopicPicture()).apply(new RequestOptions()
                        .placeholder(R.mipmap.bg_src_tianjin)).into((ImageView) helper.getView(R.id.iv_topic_picture));
            }
        });
        initTopBannerList();
        viewModel.getTopicList();
        viewModel.getTopicTypeList();

        viewModel.topicModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                adapter.setNewData(viewModel.topicModels.get());
                topBannerAdapter.setNewData(viewModel.topicModels.get());
            }
        });
        viewModel.topicTypeModels.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                topTypeAdapter.setNewData(viewModel.topicTypeModels.get());
            }
        });
    }

    /**
     * 加载顶部banner
     */
    private void initTopBannerList() {
        View view = LayoutInflater.from(_mActivity).inflate(R.layout.lvji_include_topic_top_recycler_view, binding.recyclerView,false);
        topBannerRecycler = view.findViewById(R.id.recycler_top_banner);
        GalleryLayoutManager layoutManager1 = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        layoutManager1.attach(topBannerRecycler, 0);
        layoutManager1.setItemTransformer(new ScaleTransformer());
        topBannerRecycler.setAdapter(topBannerAdapter = new BaseQuickAdapter<LvjiTopicModel, BaseViewHolder>(R.layout.lvji_item_topic_top_banner_layout) {
            @Override
            protected void convert(BaseViewHolder helper, LvjiTopicModel item) {
                //自定义view的宽度，控制一屏显示个数
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                int width = mContext.getResources().getDisplayMetrics().widthPixels;
                params.width = (int) (width / 1.38);
                helper.itemView.setLayoutParams(params);

                helper.setText(R.id.tv_topic_title, item.getTopicTitle());
                helper.setText(R.id.tv_topic_content, item.getTopicContent());
                helper.setText(R.id.tv_topic_name_value, item.getUserName());
                Glide.with(mContext).load(item.getTopicPicture()).apply(new RequestOptions()
                        .placeholder(R.mipmap.bg_src_tianjin)).into((ImageView) helper.getView(R.id.iv_topic_picture));
            }
        });

        //话题分类列表
        recycler_topic_type = view.findViewById(R.id.recycler_topic_type);
        recycler_topic_type.setLayoutManager(new LinearLayoutManager(_mActivity,LinearLayoutManager.HORIZONTAL,false));
        recycler_topic_type.setAdapter(topTypeAdapter = new BaseQuickAdapter<LvjiTopicTypeModel, BaseViewHolder>(R.layout.lvji_item_topic_top_type_layout) {
            @Override
            protected void convert(BaseViewHolder helper, LvjiTopicTypeModel item) {
                helper.setText(R.id.tv_topic_type_title,item.getTypeTitle());

            }
        } );

        adapter.addHeaderView(view);
    }


}
