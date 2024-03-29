package com.zzj.open.module_chat.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zzj.open.module_chat.R;

import java.util.ArrayList;

import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.EmoticonsFuncView;
import sj.keyboard.widget.EmoticonsIndicatorView;
import sj.keyboard.widget.EmoticonsToolBarView;
import sj.keyboard.widget.FuncLayout;

public class QqEmoticonsKeyBoard extends AutoHeightLayout implements EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener,View.OnClickListener {

    public final int APPS_HEIGHT = 256;

    public static final int FUNC_TYPE_PTT = 1;
    public static final int FUNC_TYPE_PTV = 2;
    public static final int FUNC_TYPE_IMAGE = 3;
    public static final int FUNC_TYPE_CAMERA = 4;
    public static final int FUNC_TYPE_HONGBAO = 5;
    public static final int FUNC_TYPE_EMOTICON = 6;
    public static final int FUNC_TYPE_PLUG = 7;

    protected LayoutInflater mInflater;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected QqEmoticonsToolBarView mEmoticonsToolBarView;

    protected boolean mDispatchKeyEventPreImeLock = false;

    EmoticonsEditText etChat;
    Button btnSend;
    ImageView btnVoice;
    ImageView btnPtv;
    ImageView btnImage;
    ImageView btnCamera;
    ImageView btnHongbao;
    ImageView btnEmoticon;
    ImageView btnPlug;
    FuncLayout lyKvml;

    public QqEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.view_keyboard_qq, this);
        initView(view);
        initFuncView();
    }

    protected void initView(View view) {
        etChat = view.findViewById(R.id.et_chat);
        btnSend = view.findViewById(R.id.btn_send);
        btnVoice = view.findViewById(R.id.btn_voice);
        btnPtv = view.findViewById(R.id.btn_ptv);
        btnImage = view.findViewById(R.id.btn_image);
        btnCamera = view.findViewById(R.id.btn_camera);
        btnHongbao = view.findViewById(R.id.btn_hongbao);
        btnEmoticon = view.findViewById(R.id.btn_emoticon);
        btnPlug = view.findViewById(R.id.btn_plug);
        lyKvml = view.findViewById(R.id.ly_kvml);
        btnSend.setOnClickListener(this);
        btnVoice.setOnClickListener(this);
        btnPtv.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnHongbao.setOnClickListener(this);
        btnEmoticon.setOnClickListener(this);
        btnPlug.setOnClickListener(this);
        lyKvml.setOnClickListener(this);
        etChat.setOnBackKeyClickListener(this);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        View keyboardView = inflateFunc();
        lyKvml.addFuncView(FUNC_TYPE_EMOTICON, keyboardView);
        mEmoticonsFuncView = ((EmoticonsFuncView) findViewById(com.keyboard.view.R.id.view_epv));
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) findViewById(com.keyboard.view.R.id.view_eiv));
        mEmoticonsToolBarView = ((QqEmoticonsToolBarView) findViewById(com.keyboard.view.R.id.view_etv));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        lyKvml.setOnFuncChangeListener(this);
    }

    protected View inflateFunc() {
        return mInflater.inflate(R.layout.view_func_emoticon_qq, null);
    }

    protected void initEditView() {
        etChat.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!etChat.isFocused()) {
                    etChat.setFocusable(true);
                    etChat.setFocusableInTouchMode(true);
                }
                return false;
            }
        });

        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg);
                } else {
                    btnSend.setBackgroundResource(R.drawable.btn_send_bg_disable);
                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(int type, View view) {
        lyKvml.addFuncView(type, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
        lyKvml.hideAllFuncView();
        resetIcon();
    }

    public void resetIcon() {
        btnVoice.setImageResource(R.drawable.qq_skin_aio_panel_ptt);
        btnPtv.setImageResource(R.drawable.qq_skin_aio_panel_ptv);
        btnImage.setImageResource(R.drawable.qq_skin_aio_panel_image);
        btnCamera.setImageResource(R.drawable.qq_skin_aio_panel_camera);
        btnHongbao.setImageResource(R.drawable.qq_skin_aio_panel_hongbao);
        btnEmoticon.setImageResource(R.drawable.qq_skin_aio_panel_emotion);
        btnPlug.setImageResource(R.drawable.qq_skin_aio_panel_plus);
    }

    protected void toggleFuncView(int key) {
        lyKvml.toggleFuncView(key, isSoftKeyboardPop(), etChat);
    }

    @Override
    public void onFuncChange(int key) {
        resetIcon();
        switch (key) {
            case FUNC_TYPE_PTT:
                btnVoice.setImageResource(R.mipmap.qq_skin_aio_panel_ptt_press);
                break;
            case FUNC_TYPE_PTV:
                btnPtv.setImageResource(R.mipmap.qq_skin_aio_panel_ptv_press);
                break;
            case FUNC_TYPE_IMAGE:
                btnImage.setImageResource(R.mipmap.qq_skin_aio_panel_image_press);
                break;
            case FUNC_TYPE_CAMERA:
                btnCamera.setImageResource(R.mipmap.qq_skin_aio_panel_camera_press);
                break;
            case FUNC_TYPE_HONGBAO:
                btnHongbao.setImageResource(R.mipmap.qq_skin_aio_panel_hongbao_press);
                break;
            case FUNC_TYPE_EMOTICON:
                btnEmoticon.setImageResource(R.mipmap.qq_skin_aio_panel_emotion_press);
                break;
            case FUNC_TYPE_PLUG:
                btnPlug.setImageResource(R.mipmap.qq_skin_aio_panel_plus_press);
                break;
        }
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lyKvml.getLayoutParams();
        params.height = height;
        lyKvml.setLayoutParams(params);
        super.OnSoftPop(height);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        lyKvml.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        lyKvml.setVisibility(true);
        onFuncChange(lyKvml.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (lyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(lyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        lyKvml.addOnKeyBoardListener(l);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override
    public void onBackKeyClick() {
        if (lyKvml.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (lyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    public EmoticonsEditText getEtChat() {
        return etChat;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return mEmoticonsIndicatorView;
    }

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return mEmoticonsToolBarView;
    }



    public Button getBtnSend() {
        return btnSend;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_voice) {
            toggleFuncView(FUNC_TYPE_PTT);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_ptv){
            toggleFuncView(FUNC_TYPE_PTV);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_image){
            toggleFuncView(FUNC_TYPE_IMAGE);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_camera){
            toggleFuncView(FUNC_TYPE_CAMERA);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_hongbao){
            toggleFuncView(FUNC_TYPE_HONGBAO);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_emoticon){
            toggleFuncView(FUNC_TYPE_EMOTICON);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
        else if(i == R.id.btn_plug){
            toggleFuncView(FUNC_TYPE_PLUG);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }

    }
}
