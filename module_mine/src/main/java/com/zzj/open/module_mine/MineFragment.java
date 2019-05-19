package com.zzj.open.module_mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UpdateVersion;
import com.zzj.open.base.http.HttpUrl;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.base.utils.Glide4Engine;
import com.zzj.open.base.utils.ImageUtils;
import com.zzj.open.base.utils.UpdateVersionUtils;
import com.zzj.open.base.bean.UsersVO;
import com.zzj.open.module_mine.databinding.MineFragmentMineBinding;
import com.zzj.open.module_mine.fragment.MineFeedbackFragment;
import com.zzj.open.module_mine.viewmodel.MineViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
    public int REQUEST_CODE_CHOOSE = 111;
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

        if(!SPUtils.getInstance().getString("username","").equals("")){
            binding.tvUsername.setText(SPUtils.getInstance().getString("username",""));
        }
        if(!SPUtils.getInstance().getString("headerpic","").equals("")){
            Glide.with(MineFragment.this)
                    .load(HttpUrl.IMAGE_URL+SPUtils.getInstance().getString("headerpic",""))
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_logo_512)).into(binding.ivHeader);
        }
        binding.ivHeader.setOnClickListener(v -> {
//            if(SPUtils.getInstance().getString("userName","").equals("")){
            Matisse.from(this)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(1)
                    
//                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .forResult(REQUEST_CODE_CHOOSE);
//            }
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

        binding.llExit.setOnClickListener(v -> {
            SPUtils.getInstance().clear();
            Result result = new Result();
            result.setCode(404);
            EventBus.getDefault().post(result);

        });
        //版本升级弹窗
        viewModel.updateVersionObservableField.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateVersion updateVersion = viewModel.updateVersionObservableField.get();

                UpdateVersionUtils.checkVersion(getActivity(),updateVersion);
            }
        });

        /**
         * 更换头像通知   刷新头像
         */
        viewModel.userInfo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UsersVO vo = viewModel.userInfo.get();
                Glide.with(MineFragment.this).load(HttpUrl.IMAGE_URL+vo.getFaceImage()).apply(new RequestOptions().placeholder(R.mipmap.ic_logo_512)).into(binding.ivHeader);
            }
        });
    }
    List<String> mSelected;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainPathResult(data);
            if(mSelected!=null&&mSelected.size()!=0){
                String imageBase64 = ImageUtils.imageToBase64(mSelected.get(0));
                viewModel.uploadFaceImage(imageBase64);
            }
            Log.e("OnActivityResult ", mSelected.toString());
        }
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
