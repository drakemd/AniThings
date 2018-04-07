@file:Suppress("NAME_SHADOWING")

package edu.upi.cs.drake.anithings.common.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * this extension method to simplify the inlate method of a [ViewGroup]
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot:Boolean = false): View{
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}