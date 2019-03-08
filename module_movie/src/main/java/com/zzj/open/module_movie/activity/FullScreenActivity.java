package com.zzj.open.module_movie.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zzj.open.module_movie.R;
import com.zzj.open.base.utils.WebViewJavaScriptFunction;
import com.zzj.open.base.utils.X5WebView;

import net.qiujuer.genius.ui.widget.Loading;


public class FullScreenActivity extends Activity {

	/**
	 * 用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式
	 */

	X5WebView webView;
	Loading loading;
    private String url;

	public static void start(Context context,String url) {
	    Intent starter = new Intent(context, FullScreenActivity.class);
	    starter.putExtra("url",url);
	    context.startActivity(starter);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_filechooser_layout);
		url = getIntent().getStringExtra("url");
		webView = (X5WebView) findViewById(R.id.web_filechooser);
		loading = findViewById(R.id.loading);
		webView.loadUrl(url);

//		getWindow().setFormat(PixelFormat.TRANSLUCENT);
//
//		webView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
		webView.addJavascriptInterface(new WebViewJavaScriptFunction() {

			@Override
			public void onJsFunctionCalled(String tag) {
				// TODO Auto-generated method stub

			}

			@JavascriptInterface
			public void onX5ButtonClicked() {
				FullScreenActivity.this.enableX5FullscreenFunc();
			}

			@JavascriptInterface
			public void onCustomButtonClicked() {
				FullScreenActivity.this.disableX5FullscreenFunc();
			}

			@JavascriptInterface
			public void onLiteWndButtonClicked() {
				FullScreenActivity.this.enableLiteWndFunc();
			}

			@JavascriptInterface
			public void onPageVideoClicked() {
				FullScreenActivity.this.enablePageVideoFunc();
			}
		}, "Android");



		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
				super.onPageStarted(webView, s, bitmap);
				loading.start();
			}

			@Override
			public void onPageFinished(WebView webView, String s) {
				super.onPageFinished(webView, s);
				loading.stop();
			}
		});


	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		try {
			super.onConfigurationChanged(newConfig);
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

			} else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// /////////////////////////////////////////
	// 向webview发出信息
	private void enableX5FullscreenFunc() {

		if (webView.getX5WebViewExtension() != null) {
//			Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void disableX5FullscreenFunc() {
		if (webView.getX5WebViewExtension() != null) {
//			Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void enableLiteWndFunc() {
		if (webView.getX5WebViewExtension() != null) {
//			Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

	private void enablePageVideoFunc() {
		if (webView.getX5WebViewExtension() != null) {
//			Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
			Bundle data = new Bundle();

			data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

			data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

			data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

			webView.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
					data);
		}
	}

}
