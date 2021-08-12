package org.laziskhu.amilkhu.ui.amiltools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.databinding.FragmentAmilToolsBottomSheetBinding
import org.laziskhu.amilkhu.ui.amiltools.payment.PaymentGatewayActivity
import org.laziskhu.amilkhu.utils.pushActivity

class AmilToolsBottomSheetFragment : SuperBottomSheetFragment() {

    private var _binding: FragmentAmilToolsBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentAmilToolsBottomSheetBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnPaymentGateway.setOnClickListener {
            dismiss()
            pushActivity(PaymentGatewayActivity::class.java)
        }
    }

    override fun getCornerRadius() = requireContext().resources.getDimension(R.dimen.sheet_rounded_corner)
    override fun getExpandedHeight() = ViewGroup.LayoutParams.WRAP_CONTENT
    override fun isSheetAlwaysExpanded() = true

    companion object {
        const val TAG = "AmilToolsBottomSheetFragment"
    }
}