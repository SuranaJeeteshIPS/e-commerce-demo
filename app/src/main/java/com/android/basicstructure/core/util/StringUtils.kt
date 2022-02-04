package com.android.basicstructure.core.util

import android.content.Context
import android.text.TextUtils
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by JeeteshSurana.
 */

const val REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
const val REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@!%*?&])[A-Za-z\\d$@!%*?&]{8,}"
const val REGEX_DOB = "^(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9]\$"
const val REGEX_MOBILE = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"

/**
 * Checks if the provided email string matches with the [REGEX_EMAIL] pattern.
 * @param email the provided email string.
 * @return True if the string matches the pattern, false otherwise.
 */
fun isEmailValid(email: String): Boolean {
    return doesStringMatchPattern(email, REGEX_EMAIL)
}

/**
 * Checks if the provided password string matches with the [REGEX_PASSWORD] pattern.
 * @param password the provided password string.
 * @return True if the string matches the pattern, false otherwise.
 */
fun isPasswordValid(password: String): Boolean {
    return doesStringMatchPattern(password, REGEX_PASSWORD)
}

fun isDateOfBirthValid(DOB: String): Boolean {
    return doesStringMatchPattern(DOB, REGEX_DOB)
}

/**
 * Checks if the provided email string matches with the [REGEX_MOBILE] pattern.
 * @param countryCode the provided email string.
 * @param mobileNumber the provided email string.
 * @return True if the string matches the pattern, false otherwise.
 */
fun isPhoneValid(countryCode: String, mobileNumber: String): Boolean {
    var mMatcher: Matcher? = null
    val r = Pattern.compile(REGEX_MOBILE)
    val finalMobileNumber = countryCode + mobileNumber
    if (!TextUtils.isEmpty(finalMobileNumber)) {
        mMatcher = r.matcher(finalMobileNumber)
    }
    if (mMatcher!!.find()) {
        return true
    }
    return false
}


/**
 * Checks if the provided [String] matches a [Pattern] created with a provided regEx string.
 * @param string the provided string to be checked.
 * @param regex the provided pattern.
 * @return True if string matches the pattern, false otherwise.
 */
fun doesStringMatchPattern(string: String, regex: String): Boolean {
    if (!areStringsValid(string, regex))
        return false
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(string)
    return matcher.matches()
}

/**
 * More easily accessible method to get whether AT LEAST ONE String within a provided set is null
 * or 0-length.
 * @param arr the provided String set.
 * @return Whether the provided String set is null or 0-length.
 */
fun areStringsValid(vararg arr: String?): Boolean {
    for (s in arr) {
        if (TextUtils.isEmpty(s))
            return false
    }
    return true
}

/**
 * Copies any text to system clipboard.
 * @param context the activity/application [Context].
 * @param text the string to be copied to system clipboard.
 */
fun copyToClipboard(context: Context, text: String = "") {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("COPY_TEXT", text)
    clipboard.setPrimaryClip(clip)
}

