package com.zzj.open.module_mine;

import android.content.DialogInterface;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.zzj.open.base.bean.UpdateVersion;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.UpdateVersionUtils;
import com.zzj.open.module_mine.databinding.MineFragmentMineBinding;
import com.zzj.open.module_mine.fragment.LoginFragment;
import com.zzj.open.module_mine.fragment.MineFeedbackFragment;
import com.zzj.open.module_mine.viewmodel.MineViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/14 14:18
 * @desc : 我的界面
 * @version: 1.0
 */
@Route(path = RouterFragmentPath.Mine.MINE_HOME)
public class MineFragment extends BaseFragment<MineFragmentMineBinding,MineViewModel> {
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;
    @Override
    public int initContentView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return R.layout.mine_fragment_mine;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        setSwipeBackEnable(false);

        if(!SPUtils.getInstance().getString("userName","").equals("")){
            binding.tvUsername.setText(SPUtils.getInstance().getString("userName",""));
        }
        binding.ivHeader.setOnClickListener(v -> {
            if(SPUtils.getInstance().getString("userName","").equals("")){
                _mActivity.start(new LoginFragment());
            }
        });

        binding.llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutActivity.start(getActivity());
            }
        });

        binding.llSwitchSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 后缀加载
//                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                // 恢复应用默认皮肤
//                SkinCompatManager.getInstance().restoreDefaultTheme();
            }
        });

        //反馈
        binding.llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.start(new MineFeedbackFragment());
//                Intent intent = new Intent(getActivity(), ContainerActivity.class);
//                intent.putExtra("fragment", MineFeedbackFragment.class.getCanonicalName());
//                startActivity(intent);
            }
        });

        //更改线路
        binding.llUpdateLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingleChoiceDialog();
            }
        });

        //版本更新
        binding.llUpdate.setOnClickListener(view -> {
//            viewModel.updateVersion();
        });

        //版本升级弹窗
        viewModel.updateVersionObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateVersion updateVersion = viewModel.updateVersionObservableField.get();

                UpdateVersionUtils.checkVersion(getActivity(),updateVersion);
            }
        });

    }


    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"线路一", "线路二"};
        int checkedIndex = 0;
        if(SPUtils.getInstance().getString("currentLine").equals("1")){
             checkedIndex = 0;
        }else if(SPUtils.getInstance().getString("currentLine").equals("2")){
             checkedIndex = 1;
        }
        new QMUIDialog.CheckableDialogBuilder(getActivity())
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        SPUtils.getInstance().put("currentLine",""+(which+1));
                        dialog.dismiss();
                        try {
                            String className = "com.zzj.open.module_main.activity.MainActivity";
                            ReflectUtils reflectUtils = ReflectUtils.reflect(className);
                            reflectUtils.method("start", getActivity());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }
}
