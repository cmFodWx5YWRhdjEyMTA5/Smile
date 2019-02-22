package com.zzj.open.module_main.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzj.open.module_main.R;

/**
 * @author : zzj
 * @e-mail : zhangzhijun@pansoft.com
 * @date : 2019/2/21 16:59
 * @desc : 蓝牙测试activity
 * @version: 1.0
 */
public class BluetoothTestActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1001;
    private BluetoothAdapter mBluetoothAdapter;
    private TextView tv_open_bluetooth,tv_scan_device;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_bluetooth);

        tv_open_bluetooth = findViewById(R.id.tv_open_bluetooth);
        tv_scan_device = findViewById(R.id.tv_scan_device);

        tv_scan_device.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(BluetoothTestActivity.this);
                rxPermissions
                        .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                        .subscribe(granted -> {
                            if (granted) {
                                scanLeDevice(true);
                                // All requested permissions are granted
                            } else {
                                // At least one permission is denied
                                ToastUtils.showShort("请打开蓝牙权限");
                            }
                        });

            }
        });
        //打开蓝牙
        tv_open_bluetooth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                checkBLEFeature();
            }
        });

    }

    /**
     * 检查BLE是否起作用
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void checkBLEFeature() {
        //判断是否支持蓝牙4.0
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持Ble", Toast.LENGTH_SHORT).show();
            finish();
        }
        //获取蓝牙适配器
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //判断是否支持蓝牙
        if (mBluetoothAdapter == null) {
            //不支持
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }else
            //打开蓝牙
            if (!mBluetoothAdapter.isEnabled()) {//判断是否已经打开
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }else {
                ToastUtils.showShort("蓝牙已打开");
            }

    }

    boolean mScanning;
    private Handler mHandler = new Handler();
    //扫描BLE设备
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            //获取蓝牙适配器
            final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
            if (mBluetoothAdapter.isEnabled()) {
                if (mScanning)
                    return;
                mScanning = true;
//                mLeDeviceListAdapter.clear();
                mHandler.postDelayed(mScanRunnable, 5000);//五秒后关闭扫描
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            } else {
                ToastUtils.showShort("没有扫描到设备");
            }
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mHandler.removeCallbacks(mScanRunnable);
            mScanning = false;
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        /**
         * 简单说一下这三个参数的含义：
         * @param device：识别的远程设备
         * @param rssi：  RSSI的值作为对远程蓝牙设备的报告; 0代表没有蓝牙设备;
         * @param scanRecord：远程设备提供的配对号(公告)
         */
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            BluetoothTestActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //保存到本地：用来展示扫描得到的内容
                    LogUtils.e("扫描的内容---》"+device.getName()+"----"+device.getAddress());
//                    mLeDeviceListAdapter
//                            .addDevice(new LeDevice(device.getName(), device.getAddress(), rssi, scanRecord));
//                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
    //关闭扫描
    private final Runnable mScanRunnable = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void run() {
            scanLeDevice(false);
        }
    };
}
