package com.zzj.open.module_main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.blankj.utilcode.util.FragmentUtils;
import com.zzj.open.base.router.RouterActivityPath;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_main.BR;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.adapter.ViewPagerAdapter;
import com.zzj.open.module_main.databinding.ActivityMainBinding;
import com.zzj.open.module_main.view.BottomItem;
import com.zzj.open.module_main.view.BottomTabLayout;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/28 14:47
 * @desc :
 * @version: 1.0
 */
@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding,BaseViewModel> {

    List<Fragment> items = new ArrayList<>();

    private int hidePosition = 0;
    @Override
    public void initData() {
        super.initData();
        keystore();
        getFragments();
        binding.tabBottom.setMode(BottomNavigationBar.MODE_FIXED);
        binding.tabBottom.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        binding.tabBottom.setBarBackgroundColor(R.color.white);
            binding.tabBottom.addItem(new BottomNavigationItem(R.mipmap.ic_news, "资讯").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_movie, "影视").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.ic_mine, "我的").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
        binding.tabBottom.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentUtils.showHide(items.get(position),items.get(hidePosition));
                hidePosition = position;
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
        FragmentUtils.add(getSupportFragmentManager(),items,R.id.fl_container,0);
    }

    private List<Fragment> getFragments(){

        items.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.News.NEWS_HOME).navigation());
        items.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Movie.MOVIE_HOME).navigation());
        items.add((Fragment) ARouter.getInstance().build(RouterFragmentPath.Mine.MINE_HOME).navigation());

        return items;
    }

    private List<BottomItem> getBottomItem(){
        List<BottomItem> items = new ArrayList<>();
        items.add(new BottomItem("新闻",R.mipmap.ic_launcher));
        items.add(new BottomItem("影视",R.mipmap.ic_launcher));
        items.add(new BottomItem("我的",R.mipmap.ic_launcher));

        return items;
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    /**
     * keyStore加密解密
     */
    public static void keystore(){
        try {

            KeyGenerator keyGenerator =  KeyGenerator.getInstance("DES");
            keyGenerator.init(64);
            //生成对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyData = secretKey.getEncoded();
            System.out.println(keyData.length);
            //日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
            String keyInBase64 =Base64.encodeToString(keyData,Base64.DEFAULT);
            System.out.println(keyInBase64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
