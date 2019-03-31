package com.zzj.open.module_chat.view;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzj.open.module_chat.R;
import com.zzj.open.module_chat.adapter.ChatImagesAdapter;
import com.zzj.open.module_chat.bean.ImageInfo;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.io.File;
import java.util.ArrayList;

import sj.keyboard.utils.EmoticonsKeyboardUtils;


public class ImageRecyclerView extends RelativeLayout {
    public static final int MAX_IMAGE = 100;
    protected View view;
    protected Context context;
    private OnItemListener onItemListener;

    public ImageRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ImageRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_images_list, this);
        setBackgroundColor(getResources().getColor(R.color.white));
        init(context);
    }

    private void init(final Context context) {
        RecyclerView images_recycler = (RecyclerView) view.findViewById(R.id.images_recycler);
        images_recycler.setPadding(0,0,0, EmoticonsKeyboardUtils.dip2px(context, 20));
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) images_recycler.getLayoutParams();
        params.bottomMargin = EmoticonsKeyboardUtils.dip2px(context, 20);
        images_recycler.setLayoutParams(params);
        images_recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        final ArrayList<ImageInfo> mImageList = new ArrayList<>();
        final ChatImagesAdapter adapter = new ChatImagesAdapter(R.layout.item_chat_images,mImageList);
        images_recycler.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor mCursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.WIDTH, MediaStore.Images.Media.HEIGHT},
                        MediaStore.Images.Media.MIME_TYPE + "=? OR " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media._ID + " DESC");

                if (mCursor == null) return;
                // Take 100 images
                while (mCursor.moveToNext() && mImageList.size() < MAX_IMAGE) {
                    long id = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
                    String thumbnails_path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    int width = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
                    int height = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                    ImageInfo image = new ImageInfo(Uri.fromFile(new File(path)), 200, 380);
                    image.setThumbnail_path(thumbnails_path);
                    mImageList.add(image);
//                    ViseLog.e("5555555555555--->"+mImageList.size()+image.getmUri());
                }
                mCursor.close();

                Run.onUiSync(new Action() {
                    @Override
                    public void call() {
                        adapter.setNewData(mImageList);
                        adapter.notifyDataSetChanged();
//                        ViseLog.e("6666666666666666666666666666"+mImageList.size());
                    }
                });
            }
        }).start();


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(onItemListener!=null){
                    onItemListener.setOnItemListener(mImageList.get(position),position);
                }

            }
        });

    }


    public interface OnItemListener{
        void setOnItemListener(ImageInfo imageInfo, int position);
    }

    public OnItemListener getOnItemListener() {
        return onItemListener;
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }
}
