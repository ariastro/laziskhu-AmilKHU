package org.laziskhu.amilkhu.ui.admintools.acceptattendance

import android.os.Bundle
import androidx.activity.viewModels
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.databinding.ActivityAcceptAttendanceBinding
import org.laziskhu.amilkhu.vo.Status

class AcceptAttendanceActivity : BaseActivity() {

    private var _binding: ActivityAcceptAttendanceBinding? = null
    private val binding get() = _binding!!
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

        }
        binding.rvAttendance.setHasFixedSize(true)
        binding.rvAttendance.adapter = adapter
    }

    private fun getWaitingAttendance() {
        viewModel.getWaitingAttendance().observe(this) {
            when(it.status) {
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}