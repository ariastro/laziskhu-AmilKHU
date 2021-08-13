package org.laziskhu.amilkhu.ui.history

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.databinding.ActivityHistoryAttendanceBinding
import org.laziskhu.amilkhu.utils.*
import org.laziskhu.amilkhu.vo.Status

class HistoryAttendanceActivity : BaseActivity() {

    private var _binding: ActivityHistoryAttendanceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHistoryAttendanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        getHistory()

    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            getHistory()
        }
        adapter = HistoryAdapter {}
        binding.rvHistory.setHasFixedSize(true)
        binding.rvHistory.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getHistory() {
        viewModel.getHistoryAttendance().observe(this) {
            when (it.status) {
                Status.LOADING -> {
//                    progress.show()
                    binding.shimmerViewContainer.show()
                }
                Status.ERROR -> {
//                    progress.dismiss()
                    showErrorToasty(it.message.toString())
                }
                Status.SUCCESS -> {
//                    progress.dismiss()
//                    binding.shimmerViewContainer.hide()
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.shimmerViewContainer.hide()
                        val data = it.data?.data
                        if (!data.isNullOrEmpty()) {
                            binding.rvHistory.toVisible()
                            binding.noDataLayout.root.toGone()
                            adapter.submitList(it.data.data)
                        } else {
                            binding.rvHistory.toGone()
                            binding.noDataLayout.root.toVisible()
                        }
                    }, 3000)
//                    val data = it.data?.data
//                    if (!data.isNullOrEmpty()) {
//                        binding.rvHistory.toVisible()
//                        binding.noDataLayout.root.toGone()
//                        adapter.submitList(it.data.data)
//                    } else {
//                        binding.rvHistory.toGone()
//                        binding.noDataLayout.root.toVisible()
//                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}