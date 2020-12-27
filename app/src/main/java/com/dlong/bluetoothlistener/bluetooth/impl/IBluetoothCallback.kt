package com.dlong.bluetoothlistener.bluetooth.impl

import com.dlong.bluetoothlistener.bluetooth.constant.DLBluetoothStatus

/**
 * 蓝牙状态接口
 *
 * @author D10NG
 * @date on 2019-11-12 09:30
 */
interface IBluetoothCallBack {

    /**
     * 注册
     * @param obj
     */
    fun register(obj: Any)

    /**
     * 取消注册
     * @param obj
     */
    fun unRegister(obj: Any)

    /**
     * 取消注册
     */
    fun unRegisterAll()

    /**
     * 打开蓝牙
     */
    fun openBluetooth()

    /**
     * 关闭蓝牙
     */
    fun closeBluetooth()

    /**
     * 获取蓝牙状态
     * @return
     */
    fun getBluetoothStatus() : DLBluetoothStatus
}


