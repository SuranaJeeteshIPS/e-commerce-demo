package com.android.basicstructure.core.util

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
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