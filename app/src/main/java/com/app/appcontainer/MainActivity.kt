package com.app.appcontainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    // 懒加载：使用的时候才初始化
    private val mWebView: WebView by lazy {
        val webView = findViewById<WebView>(R.id.webview)
        webView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 使用anko快速找到id，并设置里面的值
        // 开启kotlin与h5通讯的开关
        mWebView.settings.javaScriptEnabled = true

        // 设置两个webViewClient
        mWebView.webViewClient = MyWebViewClient()
        mWebView.webChromeClient = MyWebChromeClient()

        //H5与Kotlin桥梁类通讯的桥梁类：第一个参数是被调用方法的对象，第二个参数是对象别名
        mWebView.addJavascriptInterface(JsMethods(this), "jsInterface")

        // btn
        val h5Btn = findViewById<Button>(R.id.h5btn)

        h5Btn.setOnClickListener{
            val h5Url = "http://192.168.0.101:8082/web/h5-kotlin.html"
            mWebView.loadUrl(h5Url)
        }
    }

    // 创建两个WebViewClient
    private inner class MyWebViewClient : WebViewClient() {
        // 当页面加载完成调用的方法
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            // 调用H5的方法：Kotlin调用h5方法规范：mWebView.loadUrl("javascript:方法名(参数)")
            var json = JSONObject()
            json.put("name", "我是Kotlin传给H5的方法")
            val jsString = json.toString()
            mWebView.loadUrl("""javascript:showMessage($jsString)""")
        }
    }

    // lambda表达式


    // 创建一个ChromeClient
    private class MyWebChromeClient : WebChromeClient() {
        // 控制加载的进度条
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }
}
