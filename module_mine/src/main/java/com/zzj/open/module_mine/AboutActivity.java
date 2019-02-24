package com.zzj.open.module_mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zzj.open.base.http.RetrofitClient;
import com.zzj.open.module_mine.databinding.MineActivityAboutBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.BaseViewModel;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/24 21:46
 * @desc : 关于界面
 * @version: 1.0
 */
public class AboutActivity extends BaseActivity<MineActivityAboutBinding,BaseViewModel> {

    public static void start(Context context) {
        Intent starter = new Intent(context, AboutActivity.class);

        context.startActivity(starter);
    }
    @Override
    public int initContentView(Bundle bundle) {
        return R.layout.mine_activity_about;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.webView.loadUrl(RetrofitClient.web_baseUrl+"about.html");
    }
}
