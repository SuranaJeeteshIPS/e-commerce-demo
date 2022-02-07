package com.android.basicstructure.core.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.*
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt


/**
 * File holding all the methods of general interest.
 * Create by JeeteshSurana
 */


/**
 * This method converts any unit to pixels.
 *
 * @param unit      the wanted unit.
 * @param value     the wanted value units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
private fun convertToPx(unit: Int, value: Float, resources: Resources): Int {
    val px = TypedValue.applyDimension(unit, value, resources.displayMetrics)
    return px.toInt()
}


/**
 * This method converts dp units to pixels.
 *
 * @param dp        the wanted dp units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
fun dpToPx(dp: Float, resources: Resources): Int {
    return convertToPx(TypedValue.COMPLEX_UNIT_DIP, dp, resources)
}

/**
 * This method converts dp units to pixels.
 *
 * @param sp        the wanted dp units.
 * @param resources the application's `Resources`
 * @return The corresponding pixel value.
 */
fun spToPx(sp: Float, resources: Resources): Int {
    return convertToPx(TypedValue.COMPLEX_UNIT_SP, sp, resources)
}


/**
 * Add replace fragment
 *
 * @param container
 * @param fragment
 * @param addFragment
 * @param addToBackStack
 */
fun FragmentActivity.addReplaceFragment(
    @IdRes container: Int,
    fragment: Fragment,
    addFragment: Boolean,
    addToBackStack: Boolean
) {
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    if (addFragment) {
        transaction.add(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    } else {
        transaction.replace(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    }
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }
    hideKeyboard()
    if (!supportFragmentManager.isDestroyed) {
        transaction.commit()
    }
}

/**
 * Add replace fragment
 *
 * @param container
 * @param fragment
 * @param addFragment
 * @param addToBackStack
 */
fun FragmentActivity.addReplaceFragmentWithSharedElement(
    @IdRes container: Int,
    fragment: Fragment,
    addFragment: Boolean,
    addToBackStack: Boolean,
    sharedView: View? = null,
    sharedName: String? = null
) {
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    if (addFragment) {
        transaction.add(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    } else {
        transaction.replace(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    }
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }

    if (sharedView != null && !sharedName.isNullOrEmpty()) {
        transaction.setReorderingAllowed(true)
        transaction.addSharedElement(sharedView, sharedName)
    }
    hideKeyboard()
    if (!supportFragmentManager.isDestroyed) {
        transaction.commit()
    }
}

/**
 * Add replace fragment
 *
 * @param container
 * @param fragment
 * @param addFragment
 * @param addToBackStack
 */
fun FragmentActivity.addReplaceFragmentWithAnimation(
    @IdRes container: Int,
    fragment: Fragment,
    addFragment: Boolean,
    addToBackStack: Boolean,
    enterAnimation: Int,
    exitAnimation: Int
) {
    val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        enterAnimation,
        exitAnimation
    )
    if (addFragment) {
        transaction.add(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    } else {
        transaction.replace(
            container,
            fragment,
            fragment.javaClass.simpleName
        )
    }
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }
    hideKeyboard()
    if (!supportFragmentManager.isDestroyed) {
        transaction.commit()
    }
}


/**Get current active Fragment in a container of a activity
 * @param container -> Container holder id  of fragment of a activity
 * **/
fun AppCompatActivity.getCurrentFragment(@IdRes container: Int): Fragment? =
    supportFragmentManager.findFragmentById(container)


//hide the keyboard
fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = currentFocus
    if (view == null) view = View(this)
    imm.hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}

//get the screen height and give the margin to top
fun getScreenHeight(activity: Activity, dip: Float): Int {
//    val dip = 32f
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, activity.resources.displayMetrics)
    return (Resources.getSystem().displayMetrics.heightPixels - px).roundToInt()
}

//show error in snack bar
fun showError(activity: FragmentActivity, error: String) {
    Snackbar.make(activity.findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT).show()
}

//view expands
fun expand(v: View) {
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = v.measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.layoutParams.height = 1
    v.visibility = View.VISIBLE
    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            v.layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
            else (targetHeight * interpolatedTime).toInt()
            v.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}

//view Collpase
fun collapse(v: View) {
    val initialHeight = v.measuredHeight

    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                v.visibility = View.GONE
            } else {
                v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                v.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
}

fun Fragment.goBack() {
    activity?.supportFragmentManager?.popBackStack()
}





const val fullScreenFlag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
const val hideNavigationFlag =
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
const val hideStatusBar = View.STATUS_BAR_HIDDEN
const val drawStatusBarFlag = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
const val immersiveStickyFlag = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
const val noLimitFlag = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
const val keepScreenOn =
    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED


@RequiresApi(Build.VERSION_CODES.M)
val lightStatusFlag = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

enum class Theme { FULL_SCREEN, GLOBAL }

@SuppressLint("ObsoleteSdkInt")
fun Window.setTheme(theme: Theme = Theme.GLOBAL) {
    when (theme) {
        Theme.GLOBAL -> {
            val flags = hideNavigationFlag or immersiveStickyFlag or drawStatusBarFlag
            clearFlags(fullScreenFlag)
            addFlags(noLimitFlag)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    setDecorFitsSystemWindows(false)
                    val controller = decorView.windowInsetsController
                    if (controller != null) {
                        controller.hide(WindowInsets.Type.navigationBars())
                        controller.systemBarsBehavior =
                            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                    decorView.systemUiVisibility = flags or lightStatusFlag
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    decorView.systemUiVisibility = flags or lightStatusFlag
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    decorView.systemUiVisibility = flags
                }
                else -> {
                    decorView.systemUiVisibility = flags
                }
            }
            statusBarColor = Color.TRANSPARENT
        }

        Theme.FULL_SCREEN -> {
            val flags = noLimitFlag or hideNavigationFlag or hideStatusBar or immersiveStickyFlag
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    setDecorFitsSystemWindows(false)
                    val controller = decorView.windowInsetsController
                    if (controller != null) {
                        if (context.isEdgeToEdgeEnabled() >= 2) {
                            controller.show(WindowInsets.Type.navigationBars())
                            controller.systemBarsBehavior =
                                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        } else {
                            controller.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.navigationBars())
                            controller.systemBarsBehavior =
                                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        }
                    }
                    addFlags(flags or lightStatusFlag)
                    decorView.systemUiVisibility = flags or lightStatusFlag
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    addFlags(flags or lightStatusFlag or keepScreenOn)
                    decorView.systemUiVisibility = flags or keepScreenOn
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    decorView.systemUiVisibility = flags
                }
                else -> {
                    decorView.systemUiVisibility = flags
                }
            }
            statusBarColor = Color.TRANSPARENT
        }
        else -> {
        }
    }
}


fun FragmentActivity.keepScreenOn() {
    clearFlags()
    window.decorView.keepScreenOn = true
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setTurnScreenOn(true)
    }
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON )
}

/**
 * 0 : Navigation is displaying with 3 buttons
 * 1 : Navigation is displaying with 2 button(Android P navigation mode)
 * 2 : Full screen gesture(Gesture on android Q)
 */
fun Context.isEdgeToEdgeEnabled(): Int {
    val resourceId = resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
    return if (resourceId > 0) {
        resources.getInteger(resourceId)
    } else 0
}

private fun FragmentActivity.clearFlags() {
    window.decorView.keepScreenOn = false
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        setInheritShowWhenLocked(false)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        setShowWhenLocked(false)
        setTurnScreenOn(false)
    }
    window.clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
}
