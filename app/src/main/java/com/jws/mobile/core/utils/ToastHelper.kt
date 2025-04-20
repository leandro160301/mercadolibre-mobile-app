package com.jws.mobile.core.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.jws.mobile.R

object ToastHelper {
    private var currentToast: Toast? = null

    fun message(text: String?, layoutResId: Int, context: Context?) {
        if (currentToast != null) currentToast!!.cancel()

        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(layoutResId, null)

        val textView = layout.findViewById<TextView>(R.id.text)
        textView.text = text
        currentToast = Toast(context)
        currentToast!!.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        currentToast!!.duration = Toast.LENGTH_LONG
        currentToast!!.view = layout
        currentToast!!.show()
    }
}
