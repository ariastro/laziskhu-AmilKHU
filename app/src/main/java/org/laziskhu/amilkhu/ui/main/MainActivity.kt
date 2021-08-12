package org.laziskhu.amilkhu.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.GenericTransitionOptions
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.data.source.local.Prefs
import org.laziskhu.amilkhu.data.source.remote.response.GetProfileResponse
import org.laziskhu.amilkhu.databinding.ActivityMainBinding
import org.laziskhu.amilkhu.di.module.GlideApp
import org.laziskhu.amilkhu.ui.admintools.AdminToolsBottomSheetFragment
import org.laziskhu.amilkhu.ui.amiltools.AmilToolsBottomSheetFragment
import org.laziskhu.amilkhu.ui.attendance.AttendanceActivity
import org.laziskhu.amilkhu.ui.history.HistoryAttendanceActivity
import org.laziskhu.amilkhu.utils.Role
import org.laziskhu.amilkhu.utils.getCurrentDate
import org.laziskhu.amilkhu.utils.pushActivity
import org.laziskhu.amilkhu.utils.showErrorToasty
import org.laziskhu.amilkhu.vo.Status

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    private val currentDate = getCurrentDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = viewModel

        setupUI()
        setupClickListeners()
        getProfile()

    }

    private fun setupUI() {
        binding.btnAdminTools.isGone = Prefs.role == Role.USER
    }

    private fun setupClickListeners() {
        binding.btnCheckIn.setOnClickListener {
            if (Prefs.isCheckedIn && Prefs.checkInDate == currentDate) {
                showErrorToasty(getString(R.string.already_checkin))
            } else {
                startActivity(Intent(this, AttendanceActivity::class.java).apply {
                    putExtra(AttendanceActivity.EXTRA_ATTENDANCE_TYPE, true)
                })
            }
        }

        binding.btnCheckOut.setOnClickListener {
            if (Prefs.isCheckedIn && Prefs.checkInDate == currentDate && !Prefs.isCheckedOut) {
                startActivity(Intent(this, AttendanceActivity::class.java).apply {
                    putExtra(AttendanceActivity.EXTRA_ATTENDANCE_TYPE, false)
                })
            } else {
                if (Prefs.checkInDate == currentDate) {
                    showErrorToasty(getString(R.string.already_checkout))
                } else {
                    showErrorToasty(getString(R.string.not_absent_yet))
                }
            }
        }

        binding.btnHistory.setOnClickListener {
            pushActivity(HistoryAttendanceActivity::class.java)
        }

        binding.btnAmilTools.setOnClickListener {
            AmilToolsBottomSheetFragment().show(supportFragmentManager, AmilToolsBottomSheetFragment.TAG)
        }

        binding.btnAdminTools.setOnClickListener {
            AdminToolsBottomSheetFragment().show(supportFragmentManager, AdminToolsBottomSheetFragment.TAG)
        }

    }

    private fun getProfile() {
        viewModel.getProfile(Prefs.userId).observe(this) {
            when(it?.status) {
                Status.SUCCESS -> {
                    progress.dismiss()
                    it.data?.let { response ->
                        showUserData(response.data ?: GetProfileResponse.Profile())
                    }
                }
                Status.ERROR -> {
                    progress.dismiss()
                }
                Status.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun showUserData(profile: GetProfileResponse.Profile) {
        binding.apply {
            fullname.text = profile.name
            position.text = profile.position
            if (!profile.photo.isNullOrEmpty()) {
                GlideApp.with(this@MainActivity)
                    .load(profile.getProfilePicture())
                    .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
                    .into(imgProfile)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}