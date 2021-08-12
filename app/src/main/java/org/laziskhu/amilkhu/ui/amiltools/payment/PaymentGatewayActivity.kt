package org.laziskhu.amilkhu.ui.amiltools.payment

import android.os.Bundle
import androidx.activity.viewModels
import org.laziskhu.amilkhu.base.BaseActivity
import org.laziskhu.amilkhu.databinding.ActivityPaymentGatewayBinding
import org.laziskhu.amilkhu.utils.showErrorToasty
import org.laziskhu.amilkhu.utils.toGone
import org.laziskhu.amilkhu.utils.toVisible
import org.laziskhu.amilkhu.vo.Status

class PaymentGatewayActivity : BaseActivity() {

    private var _binding: ActivityPaymentGatewayBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PaymentGatewayViewModel by viewModels()

    private lateinit var rekeningAdapter: RekeningAdapter
    private lateinit var qrisAdapter: QrisAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPaymentGatewayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadData()

    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        rekeningAdapter = RekeningAdapter {}
//        binding.rvRekening.setHasFixedSize(true)
//        binding.rvRekening.adapter = rekeningAdapter
//
//        qrisAdapter = QrisAdapter {}
//        binding.rvQris.setHasFixedSize(true)
//        binding.rvQris.adapter = qrisAdapter
    }

    private fun loadData() {
        viewModel.getRekening().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progress.dismiss()
                    rekeningAdapter = RekeningAdapter {}
                    rekeningAdapter.submitList(it.data?.data)
                    binding.rvRekening.setHasFixedSize(true)
                    binding.rvRekening.adapter = rekeningAdapter
                }
                Status.ERROR -> {
                    progress.dismiss()
                    showErrorToasty(it.message.toString())
                }
                Status.LOADING -> {
                    progress.show()
                }
            }
        }

        viewModel.getQRIS().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progress.dismiss()
                    qrisAdapter = QrisAdapter {}
                    qrisAdapter.submitList(it.data?.data)
                    binding.rvQris.setHasFixedSize(true)
                    binding.rvQris.adapter = qrisAdapter
                }
                Status.ERROR -> {
                    progress.dismiss()
                    showErrorToasty(it.message.toString())
                }
                Status.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun isShowEmptyData(isRekeningEmpty: Boolean, isQRISEmpty: Boolean) {
        if (isRekeningEmpty && isQRISEmpty) {
            binding.rvQris.toGone()
            binding.rvRekening.toGone()
            binding.noDataLayout.root.toVisible()
        } else {
            binding.rvQris.toVisible()
            binding.rvRekening.toVisible()
            binding.noDataLayout.root.toGone()
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