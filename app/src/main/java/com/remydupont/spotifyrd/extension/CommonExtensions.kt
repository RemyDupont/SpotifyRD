package com.remydupont.spotifyrd.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.remydupont.spotifyrd.helper.Constants

/**
 * CommonExtensions
 *
 * Created by remydupont on 09/06/2018.
 */


/**
 *      Layout Inflation
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}



/**
 *      View Visibility
 */
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.gone() { visibility = View.GONE }

fun View?.isVisible(): Boolean { return if (this == null) false else visibility == View.VISIBLE }

fun View.enable() {
    isEnabled = true
    isClickable = true
}

fun View.disable() {
    isEnabled = false
    isClickable = false
}



/**
 *      Strings
 */
fun Context.string(resId: Int): String = getString(resId) ?: Constants.EMPTY_STRING
fun Fragment.string(resId: Int): String = activity?.string(resId) ?: Constants.EMPTY_STRING



/**
 *      Colors
 */
fun Context?.color(resId: Int) : Int {
    return if (this != null) ContextCompat.getColor(this, resId) else Color.BLACK
}
fun Fragment.color(resId: Int): Int = activity.color(resId)



/**
 *      Drawables
 */
fun Context.drawable(resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)
fun Fragment.drawable(resId: Int): Drawable? = activity?.drawable(resId)



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
