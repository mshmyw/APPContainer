package com.app.appcontainer

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast


// Kotlin与H5通讯的桥梁类
class JsMethods(context: Context) {
    // 上下文对象
    private var mContext = context

    @JavascriptInterface // 安卓4.2之后都要加
    fun shouToast(json:String) {
        // 第一种方式弹出
        Toast.makeText(mContext, json, Toast.LENGTH_SHORT).show()

    }
}