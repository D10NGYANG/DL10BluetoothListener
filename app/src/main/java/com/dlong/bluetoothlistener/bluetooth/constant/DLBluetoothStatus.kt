package com.dlong.bluetoothlistener.bluetooth.constant

/**
 * 蓝牙开关状态
 *
 * @author D10NG
 * @date on 2019-11-12 09:27
 */
enum class DLBluetoothStatus(val msg: String) {

    NONE(msg = "当前设备不支持蓝牙BLE"),
    STATE_TURNING_ON(msg = "正在打开蓝牙"),
    STATE_ON(msg = "蓝牙已打开"),
    STATE_TURNING_OFF(msg = "正在关闭蓝牙"),
    STATE_OFF(msg = "蓝牙已关闭"),
    CONNECTED(msg = "蓝牙已连接"),
    DISCONNECTED(msg = "蓝牙已断开连接")

}

