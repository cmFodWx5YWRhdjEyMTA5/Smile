package com.zzj.open.module_mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_mine.BR;
import com.zzj.open.module_mine.R;
import com.zzj.open.module_mine.api.MineServiceApi;
import com.zzj.open.module_mine.bean.UsersVO;
import com.zzj.open.module_mine.databinding.MineFragmentLoginBinding;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/4/1 14:11
 * @desc :登录界面
 * @version: 1.0
 */
public class LoginFragment extends BaseFragment<MineFragmentLoginBinding,BaseViewModel> {
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.mine_fragment_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        new ToolbarHelper(_mActivity, (Toolbar) binding.toolbar,"登录");

        binding.btnLogin.setOnClickListener(v -> {
            UsersVO usersVO = new UsersVO();
            usersVO.setUsername(binding.etUsername.getText().toString().trim());
            usersVO.setPassword(binding.etPassword.getText().toString().trim());
            RetrofitClient.getInstance().create(MineServiceApi.class)
                   .login(usersVO)
                    //请求与View周期同步
                    .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                    //线程调度
                    .compose(RxUtils.schedulersTransformer())
                    // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            showDialog("正在登录...");
                        }
                    }).subscribe(new Consumer<Result<UsersVO>>() {
                @Override
                public void accept(Result<UsersVO> o) throws Exception {
                    if(o!=null&&o.getCode() == 200){
                        ToastUtils.showShort("登录成功");
                        UsersVO users = o.getResult();
                        SPUtils.getInstance().put("userId",users.getId());
                        SPUtils.getInstance().put("userName",users.getUsername());
                        SPUtils.getInstance().put("passWord",users.getPassword());
                        try {
                            String className = "com.zzj.open.module_main.activity.MainActivity";
                            ReflectUtils reflectUtils = ReflectUtils.reflect(className);
                            reflectUtils.method("start", getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        ToastUtils.showShort("登录失败");
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
        });
    }
}
