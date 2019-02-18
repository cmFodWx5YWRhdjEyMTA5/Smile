package com.zzj.open.library_news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.library_news.BR;
import com.zzj.open.library_news.R;
import com.zzj.open.library_news.databinding.NewsFragmentNewsBinding;
import com.zzj.open.library_news.viewmodel.NewsViewModel;

import cdc.sed.yff.nm.cm.ErrorCode;
import cdc.sed.yff.nm.sp.SpotListener;
import cdc.sed.yff.nm.sp.SpotManager;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/28 16:02
 * @desc :  新闻列表
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.News.NEWS_HOME)
public class NewsFragment extends BaseFragment<NewsFragmentNewsBinding,NewsViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.news_fragment_news;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initData() {
        super.initData();
        viewModel.requestNetWork();

        //	设置插屏图片类型，默认竖图
        //		//	横图
        		SpotManager.getInstance(getActivity()).setImageType(SpotManager
         .IMAGE_TYPE_HORIZONTAL);
        // 竖图
//        SpotManager.getInstance(getActivity()).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        //		// 无动画
        //		SpotManager.getInstance(mContext).setAnimationType(SpotManager
        //				.ANIMATION_TYPE_NONE);
        //		// 简单动画
        //		SpotManager.getInstance(mContext)
        //		                    .setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(getActivity())
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        // 获取原生插屏控件
        View nativeSpotView = SpotManager.getInstance(getActivity())
                .getNativeSpot(getActivity(), new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                        LogUtils.e("原生插屏展示成功");
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        LogUtils.e("原生插屏展示失败");
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                LogUtils.e("网络异常");
                                break;
                            case ErrorCode.NON_AD:
                                LogUtils.e("暂无原生插屏广告");
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                LogUtils.e("原生插屏资源还没准备好");
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                LogUtils.e("请勿频繁展示");
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                LogUtils.e("请设置插屏为可见状态");
                                break;
                            default:
                                LogUtils.e("请稍后再试");
                                break;
                        }
                    }

                    @Override
                    public void onSpotClosed() {
                        LogUtils.e("原生插屏被关闭");
                    }

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                        LogUtils.e("原生插屏被点击");
                        LogUtils.e("是否是网页广告？%s", isWebPage ? "是" : "不是");
                    }
                });
        if (nativeSpotView != null) {
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            if (binding.rlAd != null) {
                binding.rlAd.removeAllViews();
                // 添加原生插屏控件到容器中
                binding.rlAd.addView(nativeSpotView, layoutParams);
                if (binding.rlAd.getVisibility() != View.VISIBLE) {
                    binding.rlAd.setVisibility(View.VISIBLE);
                }
            }
        }
    }


}
