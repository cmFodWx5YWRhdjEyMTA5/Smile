package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.zzj.open.module_chat.bean.SlideCardBean;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 10:00
 * @desc :  首页 viewModel
 * @version: 1.0
 */
public class ChatHomeViewModel extends BaseViewModel {

    private List<SlideCardBean> slideCardBeans = new ArrayList<>();
    public ObservableField<List<SlideCardBean>> listObservableField = new ObservableField<>();

    public ChatHomeViewModel(@NonNull Application application) {
        super(application);
    }

    public  List<SlideCardBean> initData() {
        slideCardBeans.add(new SlideCardBean());
        slideCardBeans.add(new SlideCardBean());
        slideCardBeans.add(new SlideCardBean());
        slideCardBeans.add(new SlideCardBean());
        slideCardBeans.add(new SlideCardBean());
        slideCardBeans.add(new SlideCardBean());

        return slideCardBeans;
    }
}
