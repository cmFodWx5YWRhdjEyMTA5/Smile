package com.zzj.open.module_lvji.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_lvji.R;
import com.zzj.open.module_lvji.api.ApiService;
import com.zzj.open.module_lvji.databinding.LvjiFragmentTopicselectBinding;
import com.zzj.open.module_lvji.model.EventBean;
import com.zzj.open.module_lvji.model.LvjiPublishModel;
import com.zzj.open.module_lvji.model.LvjiTopicModel;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.BR;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/6/17
 * @desc : 话题选择列表
 * @version: 1.0
 */
public class TopicSelectListFragment extends BaseFragment<LvjiFragmentTopicselectBinding, BaseViewModel> {

    private BaseQuickAdapter<LvjiTopicModel, BaseViewHolder> adapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.lvji_fragment_topicselect;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        binding.recyclerView.setAdapter(adapter = new BaseQuickAdapter<LvjiTopicModel, BaseViewHolder>(R.layout.lvji_item_topicselect_list) {
            @Override
            protected void convert(BaseViewHolder helper, LvjiTopicModel item) {
                helper.setText(R.id.tv_topic_title,item.getTopicTitle());
            }
        });

        getTopicList();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("topic", (Serializable) adapter.getData().get(position));
                setFragmentResult(RESULT_OK,bundle);
                pop();
            }
        });
    }


    private void getTopicList(){
        RetrofitClient.getInstance().create(ApiService.class)
                .getTopicList()
                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .subscribe(new Consumer<Result<List<LvjiTopicModel>>>() {
                    @Override
                    public void accept(Result<List<LvjiTopicModel>> result) throws Exception {
                        dismissDialog();
                        if(result.getCode() == 200){
                            adapter.setNewData(result.getResult());
                        }else {
                            ToastUtils.showShort(result.getMessage());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        ToastUtils.showShort("服务器错误");
                    }
                });
    }
}
