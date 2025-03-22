/**
 * AndroidExtensions
 * 
 * Useful Kotlin extension functions for Android development
 * Author: [Your Name]
 * GitHub: [Your GitHub Username]
 */

package com.yourusername.androidextensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

/**
 * CONTEXT EXTENSIONS
 */

// Toast extensions
fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

// Resource extensions
fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)

// DP-PX conversion extensions
fun Context.dpToPx(dp: Float): Int {
    return (dp * resources.displayMetrics.density).roundToInt()
}

fun Context.pxToDp(px: Int): Float {
    return px / resources.displayMetrics.density
}

// SharedPreferences quick access
fun Context.getDefaultSharedPreferences(): SharedPreferences {
    return this.getSharedPreferences(this.packageName + "_preferences", Context.MODE_PRIVATE)
}

// Check network connection
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else {
        @Suppress("DEPRECATION")
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}

// Start Activity extension with optional extras
inline fun <reified T : Activity> Context.startActivity(noinline extras: (Intent.() -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    extras?.invoke(intent)
    startActivity(intent)
}

/**
 * VIEW EXTENSIONS
 */

// Visibility extensions
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.toggleVisibility() {
    visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

// Margin extensions
fun View.updateMargins(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val params = layoutParams as? androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
        ?: return
    
    left?.let { params.leftMargin = it }
    top?.let { params.topMargin = it }
    right?.let { params.rightMargin = it }
    bottom?.let { params.bottomMargin = it }
    
    layoutParams = params
}

// Keyboard extensions
fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

// Click listener with debounce
fun View.setOnSingleClickListener(debounceTime: Long = 600L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceTime) {
                lastClickTime = currentTime
                action()
            }
        }
    })
}

/**
 * EDITTEXT EXTENSIONS
 */

// Get text as string
val EditText.textString: String
    get() = this.text.toString()

// Check if EditText is empty
val EditText.isEmpty: Boolean
    get() = this.text.toString().isEmpty()

/**
 * STRING EXTENSIONS
 */

// Email validation
fun String.isValidEmail(): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return this.matches(emailRegex.toRegex())
}

// Date formatting
fun String.toDate(pattern: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.format(pattern: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

/**
 * FRAGMENT EXTENSIONS
 */

// Arguments passing
fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
    return this.apply {
        arguments = Bundle().apply(argsBuilder)
    }
}

/**
 * RESOURCE EXTENSIONS
 */

val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.sp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

/**
 * SCREEN DIMENSION EXTENSIONS
 */

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

val Context.screenDensity: Float
    get() = resources.displayMetrics.density
