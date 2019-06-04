package com.zzj.open.module_main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.zzj.open.base.router.RouterActivityPath;
import com.zzj.open.base.router.RouterFragmentPath;
import com.zzj.open.module_main.BR;
import com.zzj.open.module_main.R;
import com.zzj.open.module_main.databinding.ActivityMainBinding;
import com.zzj.open.module_main.fragment.MainFragment;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2018/12/28 14:47
 * @desc : 首页
 * @version: 1.0
 */
@Route(path = RouterActivityPath.Main.PAGER_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    String url = "ws://192.168.2.131:8088/ws";

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(starter);
    }
    @Override
    public void initData() {
        super.initData();
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimaryDark).autoDarkModeEnable(true).fitsSystemWindows(true).init();
//        keystore();
        if(!SPUtils.getInstance().getString("userId","").equals("")){
            loadRootFragment(R.id.fl_container,new MainFragment());
        }else {
            BaseFragment fragment = (BaseFragment) ARouter.getInstance().build(RouterFragmentPath.Mine.MINE_LOGIN).navigation();
            loadRootFragment(R.id.fl_container,fragment);
        }

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
    public static void keystore() {
        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(64);
            //生成对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyData = secretKey.getEncoded();
            System.out.println(keyData.length);
            //日常使用时，一般会把上面的二进制数组通过Base64编码转换成字符串，然后发给使用者
            String keyInBase64 = Base64.encodeToString(keyData, Base64.DEFAULT);
            System.out.println(keyInBase64);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
