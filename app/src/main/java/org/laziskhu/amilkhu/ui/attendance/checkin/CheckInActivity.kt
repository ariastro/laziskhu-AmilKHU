package org.laziskhu.amilkhu.ui.attendance.checkin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.databinding.ActivityCheckInBinding
import org.laziskhu.amilkhu.utils.*
import org.laziskhu.amilkhu.utils.Constants.LAZISKHU_LATITUDE
import org.laziskhu.amilkhu.utils.Constants.LAZISKHU_LONGITUDE
import org.laziskhu.amilkhu.utils.Constants.MAX_DISTANCE
import org.laziskhu.amilkhu.vo.Status
import java.io.File

class CheckInActivity : BaseActivity() {

    private var _binding: ActivityCheckInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckInViewModel by viewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    private var latitude = 0.0
    private var longitude = 0.0
    private var isInOffice = false

    private lateinit var photo: File

    companion object {
        const val REQUEST_CODE_PERMISSION = 1252
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in)

        setupUI()
        showLoading(true)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()

    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun calculateDistance() {
        val distance = getDistance(latitude, LAZISKHU_LATITUDE, longitude, LAZISKHU_LONGITUDE)
        if (distance <= MAX_DISTANCE) {
            binding.notesLayout.toVisible()
            isInOffice = true
        } else {
            binding.notesLayout.toGone()
//            checkInAttendance(null)
            isInOffice = false
        }
    }

    private fun checkInAttendance(notes: String?) {
        viewModel.checkIn(latitude.toString(), longitude.toString(), isInOffice, notes, photo)
            .observe(this) {
                when (it.status) {
                    Status.SUCCESS -> {
                        progress.dismiss()
                        showSuccessToasty(it.data?.message.toString())
                        finish()
                    }
                    Status.LOADING -> {
                        progress.show()
                    }
                    Status.ERROR -> {
                        progress.dismiss()
                        showErrorToasty(it.message.toString())
                    }
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progress.isGone = !isLoading
        binding.tvGettingLocation.isGone = !isLoading
        binding.imgProfile.isGone = isLoading
        binding.btnSubmit.isGone = isLoading
    }

    private fun checkLocationPermission() {
        if (hasPermissionLocation(this, REQUEST_CODE_PERMISSION)) {
            checkLocationServices()
        }
    }

    private fun checkLocationServices() {
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener(this) {
            getDeviceLocation()
        }

        task.addOnFailureListener(this) { e ->
            if (e is ResolvableApiException) {
                try {
                    gpsLauncher.launch(IntentSenderRequest.Builder(e.resolution).build())
                } catch (e1: IntentSender.SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                if (result != null) {
                    latitude = task.result.latitude
                    longitude = task.result.longitude
                    imagePickerCamera(checkInLauncher)
                } else {
                    val locationRequest = LocationRequest.create()
                    locationRequest.interval = 5000
                    locationRequest.fastestInterval = 5000
                    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    locationCallback = object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            super.onLocationResult(locationResult)
                            if (locationResult == null) {
                                return
                            }
                            val location = locationResult.lastLocation
                            latitude = location.latitude
                            longitude = location.longitude
                            imagePickerCamera(checkInLauncher)
                            fusedLocationClient.removeLocationUpdates(locationCallback)
                        }
                    }
                    fusedLocationClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        null
                    )
                }
            } else {
                showWarningToasty(getString(R.string.failed_to_get_location))
            }
        }
    }

    private val gpsLauncher = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getDeviceLocation()
            }
        }

    private val checkInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                val path = fileUri.path
                photo = (File(path!!))
                binding.imgProfile.setImageURI(fileUri)
                calculateDistance()
                showLoading(false)
            } else {
                onBackPressed()
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkLocationServices()
                } else {
                    showWarningToasty(getString(R.string.permission_denied))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}