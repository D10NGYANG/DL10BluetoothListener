package com.dlong.bluetoothlistener.bluetooth.impl

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.dlong.bluetoothlistener.bluetooth.annotation.DLBluetoothObserver
import com.dlong.bluetoothlistener.bluetooth.constant.DLBluetoothStatus
import com.dlong.bluetoothlistener.utils.AnnotationUtils
import java.lang.reflect.Method

/**
 * 蓝牙状态监听
 *
 * @author D10NG
 * @date on 2019-11-12 09:31
 */
class BluetoothCallBack constructor(
    application: Application
) : IBluetoothCallBack {

    // 观察者，key=类、value=方法
    private val checkManMap = HashMap<Any, Method>()

    @Volatile
    private var bluetoothStatus: DLBluetoothStatus

    private val bluetoothReceiver = BluetoothReceiver()

    init {
        // 初始化广播
        val intentFilter = IntentFilter()
        // 监视蓝牙关闭和打开的状态
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        // 开始监听
        application.registerReceiver(bluetoothReceiver, intentFilter)

        // 初始化蓝牙状态
        val bleManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val adapter = bleManager.adapter
        bluetoothStatus = when {
            adapter == null -> DLBluetoothStatus.NONE
            adapter.isEnabled -> DLBluetoothStatus.STATE_ON
            else -> DLBluetoothStatus.STATE_OFF
        }
    }

    override fun register(obj: Any) {
        val clz = obj.javaClass
        if (!checkManMap.containsKey(clz)) {
            val method = AnnotationUtils.findAnnotationMethod(
                clz, DLBluetoothObserver::class.java,
                DLBluetoothStatus::class.java) ?: return
            checkManMap[obj] = method
        }
        post(bluetoothStatus)
    }

    override fun unRegister(obj: Any) {
        checkManMap.remove(obj)
    }

    override fun unRegisterAll() {
        checkManMap.clear()
    }

    override fun openBluetooth() {
        val ble = BluetoothAdapter.getDefaultAdapter()
        ble?.enable()
    }

    override fun closeBluetooth() {
        val ble = BluetoothAdapter.getDefaultAdapter()
        ble?.disable()
    }

    override fun getBluetoothStatus(): DLBluetoothStatus = bluetoothStatus

    // 执行
    private fun post(status: DLBluetoothStatus) {
        bluetoothStatus = status
        val set: Set<Any> = checkManMap.keys
        for (obj in set) {
            val method = checkManMap[obj] ?: continue
            method.invoke(obj, status)
        }
    }

    inner class BluetoothReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action ?: return
            when (action) {
                BluetoothAdapter.ACTION_STATE_CHANGED -> {
                    when(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)) {
                        BluetoothAdapter.STATE_TURNING_ON -> post(DLBluetoothStatus.STATE_TURNING_ON)
                        BluetoothAdapter.STATE_ON -> post(DLBluetoothStatus.STATE_ON)
                        BluetoothAdapter.STATE_TURNING_OFF -> post(DLBluetoothStatus.STATE_TURNING_OFF)
                        BluetoothAdapter.STATE_OFF -> post(DLBluetoothStatus.STATE_OFF)
                    }
                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    post(DLBluetoothStatus.CONNECTED)
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    post(DLBluetoothStatus.DISCONNECTED)
                }
            }
        }
    }
}


