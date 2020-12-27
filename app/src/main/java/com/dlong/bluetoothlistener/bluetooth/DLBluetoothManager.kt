package com.dlong.bluetoothlistener.bluetooth

import android.app.Application
import com.dlong.bluetoothlistener.bluetooth.constant.DLBluetoothStatus
import com.dlong.bluetoothlistener.bluetooth.impl.BluetoothCallBack
import com.dlong.bluetoothlistener.bluetooth.impl.IBluetoothCallBack

/**
 * 蓝牙管理器
 *
 * @author D10NG
 * @date on 2019-11-12 09:24
 */
class DLBluetoothManager constructor(
    application: Application
) : IBluetoothCallBack {

    private val bluetoothCallBack : IBluetoothCallBack

    companion object {

        @Volatile
        private var INSTANCE : DLBluetoothManager? = null

        @JvmStatic
        fun getInstance(application: Application) : DLBluetoothManager {
            val temp = INSTANCE
            if (null != temp) {
                return temp
            }
            synchronized(this) {
                val instance = DLBluetoothManager(application)
                INSTANCE = instance
                return instance
            }
        }
    }

    init {
        bluetoothCallBack = BluetoothCallBack(application)
    }

    override fun register(obj: Any) {
        bluetoothCallBack.register(obj)
    }

    override fun unRegister(obj: Any) {
        bluetoothCallBack.unRegister(obj)
    }

    override fun unRegisterAll() {
        bluetoothCallBack.unRegisterAll()
    }

    override fun openBluetooth() {
        bluetoothCallBack.openBluetooth()
    }

    override fun closeBluetooth() {
        bluetoothCallBack.closeBluetooth()
    }

    override fun getBluetoothStatus(): DLBluetoothStatus = bluetoothCallBack.getBluetoothStatus()

}


