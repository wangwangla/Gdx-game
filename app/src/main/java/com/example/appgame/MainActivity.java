package com.example.appgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
    private final int REQUEST_ENABLE_BT = 0xa01;
    private final String tag = "zhangphil";

    private ArrayAdapter<String> mArrayAdapter;
    private BluetoothAdapter mBluetoothAdapter;

    // 广播接收发现蓝牙设备
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // 添加到ListView的Adapter中。
                mArrayAdapter.add("设备名字:" + device.getName() + "\n设备地址:" + device.getAddress());
                mArrayAdapter.notifyDataSetChanged();
                System.out.println("设备名字:" + device.getName() + "\n设备地址:" + device.getAddress());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 先初始化蓝牙设备
        // initBluetoothAdapter();

        // 注册广播接收器。接收蓝牙发现讯息
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);


    }

    private void initBluetoothAdapter() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 检查设备上是否支持蓝牙设备
        if (mBluetoothAdapter == null) {
            Log.d(tag, "Device does not support Bluetooth");

            // 不支持蓝牙设备，木法，巧妇难为无米之炊，退出！
            return;
        }

        // 如果用户的设备没有开启蓝牙，则弹出开启蓝牙设备的对话框，让用户开启蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            // 接下去，在onActivityResult回调判断
        }
    }

    // 启动蓝牙发现...
    private void discoveringDevices() {
        if (mBluetoothAdapter == null)
            initBluetoothAdapter();

        if (mBluetoothAdapter.startDiscovery()) {
            Log.d(tag, "启动蓝牙扫描设备...");
        }
    }

    // 可选方法，非必需
    // 此方法使自身的蓝牙设备可以被其他蓝牙设备扫描到，
    // 注意时间阈值。0 - 3600 秒。0将一直保持可被发现、可被扫描状态，但会很消耗电力资源。
    // 通常设置时间为120秒。
    private void enablingDiscoverability() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // 0，自身设备始终可以被发现（意味着将十分消耗设备资源，如电源）
        // 第二个参数可设置的范围是0~3600秒，在此时间区间（窗口期）内可被发现
        // 任何不在此区间的值都将被自动设置成120秒。
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);

        startActivity(discoverableIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Log.d(tag, "打开蓝牙成功！");
            }

            if (resultCode == RESULT_CANCELED) {
                Log.d(tag, "放弃打开蓝牙！");
            }

        } else {
            Log.d(tag, "打开蓝牙异常！");
            return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


}
