package org.laziskhu.amilkhu.ui.attendance.checkin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toFile
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

    private var isCheckIn = true

    companion object {
        const val REQUEST_CODE_PERMISSION = 1252
        const val EXTRA_ATTENDANCE_TYPE = "EXTRA_ATTENDANCE_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_check_in)

        getAttendanceType()
        progress.show()
        setupUI()
        setupClickListeners()
        setupSearchWatchers()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermission()

    }

    private fun getAttendanceType() {
        isCheckIn = intent.getBooleanExtra(EXTRA_ATTENDANCE_TYPE, true)
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (isCheckIn) supportActionBar?.title = getString(R.string.absen_masuk) else getString(R.string.absen_keluar)
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            checkInAttendance(binding.notes.text.toString())
        }
    }

    private fun setupSearchWatchers() {
        binding.notes.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.btnSubmit.isEnabled = p0.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun calculateDistance() {
        val distance = getDistance(latitude, LAZISKHU_LATITUDE, longitude, LAZISKHU_LONGITUDE)
        if (distance <= 1000000000000) {
            isInOffice = true
            binding.btnSubmit.toGone()
            binding.notesLayout.toGone()
            binding.imgProfile.toGone()
            logDebug("data: $photo, $latitude, $longitude, $isInOffice")
            checkInAttendance(null)
        } else {
            binding.notesLayout.toVisible()
            binding.imgProfile.toVisible()
            binding.btnSubmit.toVisible()
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
                        finish()
                    }
                }
            }
    }

    private fun checkOutAttendance() {
        viewModel.checkOut(latitude.toString(), longitude.toString())
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
                        finish()
                    }
                }
            }
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
                    if (isCheckIn) {
                        imagePickerCamera(checkInLauncher)
                    } else {
                        checkOutAttendance()
                    }
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
                            if (isCheckIn) {
                                imagePickerCamera(checkInLauncher)
                            } else {
                                checkOutAttendance()
                            }
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

    private val gpsLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                getDeviceLocation()
            }
        }

    private val checkInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                photo = fileUri.toFile()
                binding.imgProfile.setImageURI(fileUri)
                calculateDistance()
            } else {
                progress.dismiss()
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