package com.dlong.bluetoothlistener.utils

import java.lang.reflect.Method

object AnnotationUtils {

    /**
     * 查找类中的方法
     * @param clz Class<*> 类
     * @param observerClz Class<Annotation> 注解
     * @param paramClzs Class<*> 参数
     * @return Method?
     */
    fun findAnnotationMethod(clz: Class<*>, observerClz: Class<out Annotation>, vararg paramClzs: Class<*>) : Method? {
        val method = clz.methods
        for (m in method) {
            // 看是否有注解
            m.getAnnotation(observerClz) ?: continue
            // 判断返回类型
            val genericReturnType = m.genericReturnType.toString()
            if ("void" != genericReturnType) {
                // 方法的返回类型必须为void
                throw RuntimeException("The return type of the method【${m.name}】 must be void!")
            }
            // 检查参数
            val parameterTypes = m.genericParameterTypes
            if (parameterTypes.size != paramClzs.size) {
                throw RuntimeException("The parameter types size of the method【${m.name}】must be ${paramClzs.size}!")
            }
            var isParamOk = true
            for (i in paramClzs.indices) {
                val typeName = parameterTypes[i].toString().replace("class", "").trim()
                if (typeName != paramClzs[i].name) {
                    isParamOk = false
                    break
                }
            }
            if (!isParamOk) {
                throw RuntimeException("The parameter types are wrong!")
            }
            return m
        }
        return null
    }
}