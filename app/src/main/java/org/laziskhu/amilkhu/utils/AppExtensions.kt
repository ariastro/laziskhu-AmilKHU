package org.laziskhu.amilkhu.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import es.dmoral.toasty.Toasty
import org.laziskhu.amilkhu.utils.Constants.COUNTRY_CODE_ID
import org.laziskhu.amilkhu.utils.Constants.LANGUAGE_ID
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*

fun View.toVisible() {
    visibility = View.VISIBLE
}

fun View.toGone() {
    visibility = View.GONE
}

fun logDebug(message: String) {
    Timber.d(message)
}

fun logError(message: String, throwable: Throwable? = null) {
    Timber.e(throwable, message)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showSuccessToasty(message: String) {
    Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showErrorToasty(message: String) {
    Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showInfoToasty(message: String) {
    Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Context.showWarningToasty(message: String) {
    Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.imagePickerCamera(launcher: ActivityResultLauncher<Intent>) {
    ImagePicker.with(this)
        .crop()
        .cameraOnly()
        .compress(1024)
        .createIntent { intent ->
            launcher.launch(intent)
        }
}

fun Fragment.imagePickerCamera(launcher: ActivityResultLauncher<Intent>) {
    ImagePicker.with(this)
        .crop()
        .cameraOnly()
        .compress(1024)
        .createIntent { intent ->
            launcher.launch(intent)
        }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun <T> AppCompatActivity.pushActivity(targetClass: Class<T>) {
    startActivity(Intent(this, targetClass))
}

fun String.toLocalDateTime(pattern: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale(LANGUAGE_ID, COUNTRY_CODE_ID))
    return LocalDateTime.parse(this, formatter)
}

fun String.toLocalDate(pattern: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale(LANGUAGE_ID, COUNTRY_CODE_ID))
    return LocalDate.parse(this, formatter)
}

fun LocalDate.format(pattern: String): String {
    val formatter =  DateTimeFormatter.ofPattern(pattern, Locale(LANGUAGE_ID, COUNTRY_CODE_ID))
    return formatter.format(this)
}

fun LocalDateTime.format(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale(LANGUAGE_ID, COUNTRY_CODE_ID))
    return formatter.format(this)
}