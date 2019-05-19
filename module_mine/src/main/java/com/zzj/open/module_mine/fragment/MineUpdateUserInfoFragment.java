package com.zzj.open.module_mine.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zzj.open.base.bean.Result;
import com.zzj.open.base.bean.UsersVO;
import com.zzj.open.base.global.SPKeyGlobal;
import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.base.utils.ImageUtils;
import com.zzj.open.base.utils.ToolbarHelper;
import com.zzj.open.module_mine.BR;
import com.zzj.open.module_mine.R;
import com.zzj.open.module_mine.api.MineServiceApi;
import com.zzj.open.module_mine.bean.UsersBo;
import com.zzj.open.module_mine.databinding.MineFragmentUpdateuserBinding;

import net.qiujuer.genius.ui.compat.UiCompat;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * @author : zzj
 * @desc : 登录成功后设置用户信息界面
 * @version: 1.0
 */
public class MineUpdateUserInfoFragment extends BaseFragment<MineFragmentUpdateuserBinding,BaseViewModel> {

    private String imagePath;
    private boolean isMan = true;
    private int sex = 0;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.mine_fragment_updateuser;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        // 初始化背景
        Glide.with(this)
                .load(R.mipmap.bg_src_tianjin)
                .apply(new RequestOptions().centerCrop())//居中剪切
                .into(new SimpleTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        // 使用适配类进行包装
                        resource = DrawableCompat.wrap(resource);
                        resource.setColorFilter(UiCompat.getColor(getResources(), R.color.colorAccent),
                                PorterDuff.Mode.SCREEN); // 设置着色的效果和颜色，蒙板模式
                        binding.llBg.setBackground(resource);
                    }
                });

        new ToolbarHelper(_mActivity, binding.toolbar,"仅差一步了！",false);

        //性别选择
        binding.imSex.setOnClickListener(view -> {
// 性别图片点击的时候触发
            isMan = !isMan; // 反向性别

            Drawable drawable = getResources().getDrawable(isMan ?
                    R.drawable.ic_sex_man : R.drawable.ic_sex_woman);
            binding.imSex.setImageDrawable(drawable);
            // 设置背景的层级，切换颜色
            binding.imSex.getBackground().setLevel(isMan ? 0 : 1);
        });
        //提交
        binding.btnSubmit.setOnClickListener(view -> {
            if(imagePath == null){
                ToastUtils.showShort("请选择上传头像");
                return;
            }
            //设置性别
            if(isMan){
                sex = 1;
            }else {
                sex = 2;
            }
            String nickName = binding.editNickname.getText().toString().trim();
            if(nickName.equals("")){
                ToastUtils.showShort("请输入昵称");
                return;
            }
            String desc = binding.editDesc.getText().toString().trim();
            if(desc.equals("")){
                ToastUtils.showShort("请填写个性描述");
                return;
            }
            UsersVO usersBo = new UsersVO();
            usersBo.setId(SPUtils.getInstance().getString("userId",""));
            usersBo.setChatSex(sex);
            usersBo.setDescription(desc);
            usersBo.setNickname(nickName);
            usersBo.setFaceImage(imagePath);
            RetrofitClient.getInstance().create(MineServiceApi.class)
                    .updateUserInfo(usersBo)
                    .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                    .compose(RxUtils.schedulersTransformer())
                    .compose(RxUtils.exceptionTransformer())
                    .doOnSubscribe(disposable -> {
                        showDialog("请稍后…");
                    })
                    .subscribe(new Consumer<Result<UsersVO>>() {
                        @Override
                        public void accept(Result<UsersVO> result) throws Exception {
                            if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                                ToastUtils.showShort("更新成功");
                                try {
                                    String className = "com.zzj.open.module_main.activity.MainActivity";
                                    ReflectUtils reflectUtils = ReflectUtils.reflect(className);
                                    reflectUtils.method("start", getActivity());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }else {
                                ToastUtils.showShort("更新失败");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            ToastUtils.showShort(throwable.getMessage());
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            dismissDialog();
                        }
                    });

        });
        //头像选择
        binding.imPortrait.setOnClickListener(view -> {
            // 进入相册 以下是例子：用不到的api可以不写
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    // .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(1)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false

                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .enableCrop(true)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                   // .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    //.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                    .isGif(true)// 是否显示gif图片 true or false
                    //.compressSavePath(getPath())//压缩图片保存地址
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(true)// 是否圆形裁剪 true or false
                    .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    //.openClickSound()// 是否开启点击声音 true or false
                    //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                    //.rotateEnabled() // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    //.videoQuality()// 视频录制质量 0 or 1 int
                    //.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                    //.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                    //.recordVideoSecond()//视频秒数录制 默认60s int
                    .isDragFrame(false)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if(selectList != null&& selectList.size()!=0){
                        UsersBo usersBo = new UsersBo();
                        usersBo.setUserId(SPUtils.getInstance().getString("userId",""));
                        String imageBase64 = ImageUtils.imageToBase64(selectList.get(0).getCutPath());
                        usersBo.setFaceData(imageBase64);
                        RetrofitClient.getInstance().create(MineServiceApi.class)
                                .uploadFace(usersBo)
                                .compose(RxUtils.bindToLifecycle(viewModel.getLifecycleProvider()))
                                .compose(RxUtils.schedulersTransformer())
                                .compose(RxUtils.exceptionTransformer())
                                .doOnSubscribe(disposable -> {
                                    showDialog("上传中…");
                                })
                                .subscribe(new Consumer<Result<UsersVO>>() {
                                    @Override
                                    public void accept(Result<UsersVO> result) throws Exception {
                                        if(result.getCode() == SPKeyGlobal.REQUEST_SUCCESS){
                                            ToastUtils.showShort("上传成功");
                                            SPUtils.getInstance().put("headerpic",result.getResult().getFaceImage());
                                            imagePath = result.getResult().getFaceImage();
                                            binding.imPortrait.setup(imagePath);
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        ToastUtils.showShort(throwable.getMessage());
                                    }
                                }, new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        dismissDialog();
                                    }
                                });
                    }

                    break;
            }
        }
    }
}
