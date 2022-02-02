package com.artem.mi.spacexautenticom.ext

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.setVisible(visible: Boolean, invisible: Int = GONE) {
    visibility = if (visible) VISIBLE else invisible
}