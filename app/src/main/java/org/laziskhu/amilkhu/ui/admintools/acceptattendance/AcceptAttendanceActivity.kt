package org.laziskhu.amilkhu.ui.admintools.acceptattendance

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.databinding.ActivityAcceptAttendanceBinding
import org.laziskhu.amilkhu.databinding.LayoutAcceptAttendanceDialogBinding
import org.laziskhu.amilkhu.utils.Constants.ACCEPTED
import org.laziskhu.amilkhu.utils.Constants.DENIED
import org.laziskhu.amilkhu.utils.showErrorToasty
import org.laziskhu.amilkhu.utils.showSuccessToasty
import org.laziskhu.amilkhu.vo.Status

class AcceptAttendanceActivity : BaseActivity() {

    private var _binding: ActivityAcceptAttendanceBinding? = null
    private val binding get() = _binding!!
    private var _dialogBinding: LayoutAcceptAttendanceDialogBinding? = null
    private val dialogBinding get() = _dialogBinding!!
    private lateinit var adapter: WaitingAttendanceAdapter
    private val viewModel: WaitingAttendanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAcceptAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        getWaitingAttendance()

    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getWaitingAttendance()
        }
        adapter = WaitingAttendanceAdapter(this) {
            showAcceptAttendanceDialog(it.idAttendence ?: "")
        }
        binding.rvAttendance.setHasFixedSize(true)
        binding.rvAttendance.adapter = adapter
    }

    private fun getWaitingAttendance() {
        viewModel.getWaitingAttendance().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progress.dismiss()
                    adapter.submitList(it.data?.data)
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

    private fun showAcceptAttendanceDialog(id: String) {
        val dialog = Dialog(this, R.style.ThemeDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _dialogBinding = LayoutAcceptAttendanceDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)

        dialogBinding.btnAccept.setOnClickListener {
            dialog.dismiss()
            updateAttendanceStatus(id, ACCEPTED)
        }

        dialogBinding.btnDeny.setOnClickListener {
            dialog.dismiss()
            updateAttendanceStatus(id, DENIED)
        }

        dialog.show()
    }

    private fun updateAttendanceStatus(id: String, status: String) {
        viewModel.updateAttendanceStatus(id, status).observe(this) {
            when(it.status) {
                Status.LOADING -> {
                    progress.show()
                }
                Status.ERROR -> {
                    progress.dismiss()
                    showErrorToasty(it.message.toString())
                }
                Status.SUCCESS -> {
                    progress.dismiss()
                    showSuccessToasty(getString(R.string.accepted_attendance))
                    getWaitingAttendance()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _dialogBinding = null
    }

}