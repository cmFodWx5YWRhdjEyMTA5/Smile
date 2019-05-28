package com.zzj.open.module_chat.vm;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UsersVO;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_chat.api.ApiService;
import com.zzj.open.module_chat.bean.SlideCardBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.StringUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/5/19 10:00
 * @desc :  首页 viewModel
 * @version: 1.0
 */
public class ChatHomeViewModel extends BaseViewModel {

//    private List<UsersVO> slideCardBeans = new ArrayList<>();
    public ObservableField<List<UsersVO>> listObservableField = new ObservableField<>();

    public ChatHomeViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取卡片列表
     * @param userId
     * @param location
     */
    public void listUserCard(String userId,String location){
        RetrofitClient.getInstance().create(ApiService.class)
                .listUserCard(userId,location)
                //请求与View周期同步
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider()))
                //线程调度
                .compose(RxUtils.schedulersTransformer())
                // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在加载...");
                    }
                }).subscribe(new Consumer<Result<List<UsersVO>>>() {
            @Override
            public void accept(Result<List<UsersVO>> o) throws Exception {
                if(o!=null&&o.getCode() == 200){
                    listObservableField.set(o.getResult());
                }else {
                    ToastUtils.showShort("获取失败");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("登录失败");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                dismissDialog();
            }
        });
    }
}
