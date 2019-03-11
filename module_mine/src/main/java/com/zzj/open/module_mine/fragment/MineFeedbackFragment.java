package com.zzj.open.module_mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_mine.R;
import com.zzj.open.module_mine.api.MineServiceApi;
import com.zzj.open.module_mine.databinding.MineFragmentFeedbackBinding;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/9 15:45
 * @desc :反馈建议页面
 * @version: 1.0
 */
public class MineFeedbackFragment extends BaseFragment<MineFragmentFeedbackBinding,BaseViewModel>{
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.mine_fragment_feedback;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(getActivity(), (Toolbar) binding.toolbar,"反馈建议");

        binding.btnSubmit.setOnClickListener(v->{
            RetrofitClient.getInstance().create(MineServiceApi.class)
                    .submitFeedback(binding.etContent.getText().toString().trim(),binding.etContact.getText().toString().trim())
                    //请求与View周期同步
                    .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                    //线程调度
                    .compose(RxUtils.schedulersTransformer())
                    // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("正在提交...");
                        }
                    }).subscribe(new Consumer<Result>() {
                @Override
                public void accept(Result o) throws Exception {
                    if(o!=null&&o.getCode() == 1){
                        ToastUtils.showShort("提交成功");
                        getActivity().finish();
                    }else {
                        ToastUtils.showShort("提交失败");
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ToastUtils.showShort("提交失败");
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    dismissDialog();
                }
            });
        });

    }
}
