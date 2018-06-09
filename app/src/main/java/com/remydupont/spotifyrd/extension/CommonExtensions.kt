package com.remydupont.spotifyrd.extension

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * CommonExtensions
 *
 * Created by remydupont on 09/06/2018.
 */


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}







/**
 *      Toasts
 */
fun Activity.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
fun Activity.toast(text: () -> CharSequence ) = Toast.makeText(this, text(), Toast.LENGTH_SHORT).show()
fun Activity.toast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

fun Activity.longToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
fun Activity.longToast(text :() -> CharSequence) = Toast.makeText(this, text(), Toast.LENGTH_LONG).show()
fun Activity.longToast(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_LONG).show()


fun Fragment.toast(text: CharSequence) = activity?.toast(text)
fun Fragment.toast(text: () -> CharSequence ) = activity?.toast(text)
fun Fragment.toast(@StringRes resId: Int) = activity?.toast(resId)

fun Fragment.longToast(text: CharSequence) = activity?.longToast(text)
fun Fragment.longToast(text :() -> CharSequence) = activity?.longToast(text)
fun Fragment.longToast(@StringRes resId: Int) = activity?.longToast(resId)
