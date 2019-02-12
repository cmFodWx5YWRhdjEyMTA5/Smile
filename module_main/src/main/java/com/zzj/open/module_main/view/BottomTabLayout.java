package com.zzj.open.module_main.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/5 16:46
 * @desc :  底部菜单栏 可配合 viewPager使用
 * @version: 1.0
 */
public class BottomTabLayout extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    /**
     * Item列表
     */
    private List<BottomItem> bottomItems;
    /**
     * button列表
     */
    private List<Button> buttons;
    /**
     * 点击改变图片颜色
     */
    private int imgColor = 0xff009AFF;
    /**
     * 默认图片颜色
     */
    private int imgDefaultColor = 0xff565656;
    /**
     * 字体大小
     */
    private float textSize = 12;
    /**
     * 内边距
     */
    private int imgPadding = 12;
    private int width;
    private int hight;
    /**
     * 点击事件回调
     */
    private BottomItemOnClickListener bottomItemOnClickListener;

    private ViewPager viewPager;

    public BottomTabLayout(Context context) {
        this(context, null);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        buttons = new ArrayList<>();

    }

    /**
     * 设置按钮
     *
     * @param bottomItemList
     * @throws Exception
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setBottomItems(List<BottomItem> bottomItemList) {
        if (bottomItemList == null || bottomItemList.size() == 0) {
            return;
        }
        bottomItems = bottomItemList;
        for (int i = 0; i < bottomItemList.size(); i++) {
            //创建button
            Button buttom = new Button(mContext);

            //设置宽和高为MATCH_PARENT
            LayoutParams layoutParams = new LayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            buttom.setLayoutParams(layoutParams);
            //字体居中
            buttom.setGravity(Gravity.CENTER);
            //设置文字
            buttom.setText(bottomItems.get(i).getName());
            //设置文字大小
            buttom.setTextSize(textSize);
            //设置内边距
            buttom.setPadding(imgPadding, imgPadding, imgPadding, imgPadding);
            //去掉button背景
            buttom.setBackground(null);
            //获取图标资源
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bottomItems.get(i).getIcon());
            //设置图标默认颜色
            Drawable drawable = new BitmapDrawable(getResources(), tintBitmap(bitmap, imgDefaultColor));
            //设置图标
            buttom.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            //将item设置到tag中下一步需要用到
            buttom.setTag(bottomItems.get(i));
            //设置监听
            buttom.setOnClickListener(this);
            //添加到当前布局
            addView(buttom);
            //添加到按钮组里
            buttons.add(buttom);
        }


    }

    /**
     * 计算每个item的大小  平均分配
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    private boolean isShow = false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //计算每一个子button的宽度 并且判断只计算一次
        if (!isShow) {
            for (int i = 0; i < getChildCount(); i++) {
                LayoutParams ll = (LayoutParams) getChildAt(i).getLayoutParams();
                ll.width = width / bottomItems.size();
                getChildAt(i).setLayoutParams(ll);
            }
            isShow = true;
        }
    }

    /**
     * 手动设置选中的button
     **/
    public void setShowIndex(int index) {
        if (buttons.size() != 0) {
            BottomItem bottomItem = (BottomItem) buttons.get(index).getTag();
            getBitmap(buttons.get(index), bottomItem.getIcon(), imgColor);
//            bottomItemOnClickListener.bottomItemOnClick(buttons.get(index),index,bottomItem);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        hight = MeasureSpec.getSize(heightMeasureSpec);
    }

    /**
     * 改变选中颜色
     *
     * @param btn
     * @param img
     * @param color
     */
    public void getBitmap(Button btn, int img, int color) {
        btn.setTextColor(color);
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), img);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), tintBitmap(bitmap, color));
        btn.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    /**
     * 对每一个BUTTON进行监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        for (int i = 0; i < buttons.size(); i++) {
            //从tag中获取BottomItem
            BottomItem bottomItem = (BottomItem) buttons.get(i).getTag();
            //重置按钮颜色
            getBitmap(buttons.get(i), bottomItem.getIcon(), imgDefaultColor);
            //判断点击的是哪个按钮
            if (buttons.get(i).getTag() == view.getTag()) {
                //更改被点击的按钮颜色
                getBitmap(buttons.get(i), bottomItem.getIcon(), imgColor);
                if (bottomItemOnClickListener != null) {
                    //通知回调
                    bottomItemOnClickListener.bottomItemOnClick(view, i, bottomItem);
                    //设置点击的ViewPager的Item
                    viewPager.setCurrentItem(i,false);
                }
            }
        }
    }

    public BottomItemOnClickListener getBottomItemOnClickListener() {
        return bottomItemOnClickListener;
    }

    public void setBottomItemOnClickListener(BottomItemOnClickListener bottomItemOnClickListener) {
        this.bottomItemOnClickListener = bottomItemOnClickListener;
    }

    public void bindViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        initViewPagerListener();
    }

    /**
     * 设置fragments
     *
     * @param fragments
     */
    public void setFragments(List<BaseFragment> fragments) {

    }

    /**
     * 点击监听回调接口
     */
    public interface BottomItemOnClickListener {
        void bottomItemOnClick(View view, int i, BottomItem item);
    }


    /**
     * 改变颜色
     *
     * @param inBitmap
     * @param tintColor
     * @return
     */
    public static Bitmap tintBitmap(Bitmap inBitmap, int tintColor) {
        if (inBitmap == null) {
            return null;
        }
        Bitmap outBitmap = Bitmap.createBitmap(inBitmap.getWidth(), inBitmap.getHeight(), inBitmap.getConfig());
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(inBitmap, 0, 0, paint);
        return outBitmap;
    }

    private void initViewPagerListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < buttons.size(); i++) {
                    //从tag中获取BottomItem
                    BottomItem bottomItem = (BottomItem) buttons.get(i).getTag();
                    if (i == position) {
                        //更改被点击的按钮颜色
                        getBitmap(buttons.get(position), ((BottomItem) buttons.get(position).getTag()).getIcon(), imgColor);
                    } else {
                        //重置按钮颜色
                        getBitmap(buttons.get(i), bottomItem.getIcon(), imgDefaultColor);

                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
