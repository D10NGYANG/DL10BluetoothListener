package com.dlong.bluetoothlistener

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dlong.bluetoothlistener.bluetooth.DLBluetoothManager
import com.dlong.bluetoothlistener.bluetooth.annotation.DLBluetoothObserver
import com.dlong.bluetoothlistener.bluetooth.constant.DLBluetoothStatus
import com.dlong.bluetoothlistener.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 开始监听
        DLBluetoothManager.getInstance(this.application).register(this)

        // 点击事件
        binding.btnOpen.setOnClickListener {
            DLBluetoothManager.getInstance(this.application).openBluetooth()
        }
        binding.btnClose.setOnClickListener {
            DLBluetoothManager.getInstance(this.application).closeBluetooth()
        }
    }

    override fun onDestroy() {
        // 取消监听
        DLBluetoothManager.getInstance(this.application).unRegister(this)
        super.onDestroy()
    }

    /**
     * 监听
     * @param status DLBluetoothStatus
     */
    @DLBluetoothObserver
    fun onBleStatusChange(status: DLBluetoothStatus) {
        binding.blueStatusText = status.msg
    }
}