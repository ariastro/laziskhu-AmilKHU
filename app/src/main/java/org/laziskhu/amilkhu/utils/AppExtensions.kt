package org.laziskhu.amilkhu.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.dhaval2404.imagepicker.ImagePicker
import es.dmoral.toasty.Toasty
import org.laziskhu.amilkhu.utils.Constants.COUNTRY_CODE_ID
import org.laziskhu.amilkhu.utils.Constants.LANGUAGE_ID
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.util.*
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

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
        .compress(2048)
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

fun <T> Fragment.pushActivity(targetClass: Class<T>) {
    startActivity(Intent(requireContext(), targetClass))
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

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Activity.hasPermissionLocation(context: Context, REQUEST_PERMISSION_CODE: Int): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            // Show the permission request
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_CODE
            )
            false
        }
    } else {
        true
    }
}

fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val theta = lon1 - lon2
    var dist = (sin(deg2rad(lat1))
            * sin(deg2rad(lat2))
            + (cos(deg2rad(lat1))
            * cos(deg2rad(lat2))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * Math.PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / Math.PI
}

fun getCurrentDate() = LocalDate.now().format(Constants.timeStampFormat)
fun getCurrentTime() = LocalDateTime.now().format(Constants.timeOnlyFormat)

fun ShimmerFrameLayout.show() {
    toVisible()
    startShimmer()
}

fun ShimmerFrameLayout.hide() {
    stopShimmer()
    toGone()
}