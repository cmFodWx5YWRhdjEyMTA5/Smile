package com.zzj.open.module_chat.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sj.emoji.EmojiBean;
import com.zzj.open.module_chat.BR;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.bean.ImageInfo;
import com.zzj.open.module_chat.databinding.ChatFragmentChatdetailsBinding;
import com.zzj.open.module_chat.utils.Cons;
import com.zzj.open.module_chat.utils.Factory;
import com.zzj.open.module_chat.utils.PicturesCompressor;
import com.zzj.open.module_chat.utils.QqUtils;
import com.zzj.open.module_chat.utils.SimpleCommonUtils;
import com.zzj.open.module_chat.utils.StreamUtil;
import com.zzj.open.module_chat.view.ImageRecyclerView;
import com.zzj.open.module_chat.view.QqEmoticonsKeyBoard;
import com.zzj.open.module_chat.view.SimpleQqGridView;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/3/31 10:14
 * @desc :  聊天 详情
 * @version: 1.0
 */
public class ChatFragment extends BaseFragment<ChatFragmentChatdetailsBinding,BaseViewModel> implements FuncLayout.OnFuncKeyBoardListener, AutoHeightLayout.OnMaxParentHeightChangeListener{


    private ImageRecyclerView imageRecyclerView;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.chat_fragment_chatdetails;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        initEmoticonsKeyBoardBar();
    }

    private void initEmoticonsKeyBoardBar() {

        QqUtils.initEmoticonsEditText(binding.kbLayout.getEtChat());

        imageRecyclerView = new ImageRecyclerView(_mActivity);
        binding.kbLayout.setAdapter(QqUtils.getCommonAdapter(_mActivity, emoticonClickListener));
        binding.kbLayout.addOnFuncKeyBoardListener(this);

        binding.kbLayout.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PTT, new SimpleQqGridView(_mActivity));
//        ek_bar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PTV, new SimpleQqGridView(mActivity));
//        ek_bar.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_PLUG, new SimpleQqGridView(mActivity));
        binding.kbLayout.addFuncView(QqEmoticonsKeyBoard.FUNC_TYPE_IMAGE,imageRecyclerView);
        imageRecyclerView.setOnItemListener(new ImageRecyclerView.OnItemListener() {
            @Override
            public void setOnItemListener(ImageInfo imageInfo, int position) {
//                ViseLog.e("setOnItemListener---->"+imageInfo.getmUri()+"---position-->"+position);
                final String imagePath = imageInfo.getmUri().getPath();
                // 进行压缩
                String cacheDir =_mActivity.getCacheDir().getAbsolutePath();
                final String tempFile = String.format("%s/image/Cache_%s.png", cacheDir, SystemClock.uptimeMillis());
                Factory.runOnAsync(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            // 压缩工具类
                            if (PicturesCompressor.compressImage(_mActivity,imagePath, tempFile,
                                    Cons.MAX_UPLOAD_IMAGE_LENGTH)) {
                                // 上传
                                // 清理缓存
                                StreamUtil.delete(tempFile);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

//                onSendImgMsg(imageBase64);
            }
        });
        binding.kbLayout.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {

            }
        });

        binding.kbLayout.getEmoticonsToolBarView().addFixedToolItemView(true, R.mipmap.qvip_emoji_tab_more_new_pressed, null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_mActivity, "ADD", Toast.LENGTH_SHORT).show();
            }
        });


    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick( binding.kbLayout.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Cons.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
//                        onSendTextMsg(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
//                        ViseLog.e("EmojiBean---->"+content);
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
//                        ViseLog.e("EmoticonEntity---->"+content);
                    }
//                    ViseLog.e("content---->"+content);
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index =  binding.kbLayout.getEtChat().getSelectionStart();
                    Editable editable =  binding.kbLayout.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public void onMaxParentHeightChange(int height) {

    }

    @Override
    public void OnFuncPop(int height) {

    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.kbLayout.reset();
    }
}
