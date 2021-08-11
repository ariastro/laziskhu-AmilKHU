package org.laziskhu.amilkhu.ui.admintools.acceptattendance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment
import com.bumptech.glide.GenericTransitionOptions
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.databinding.FragmentZoomImageBinding
import org.laziskhu.amilkhu.di.module.GlideApp
import org.laziskhu.amilkhu.utils.getColorCompat

class ZoomImageFragment : SuperBottomSheetFragment() {

    private var _binding: FragmentZoomImageBinding? = null
    private val binding get() = _binding!!

    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageUrl = it.getString(ARG_IMAGE_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentZoomImageBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlideApp.with(this)
            .load(imageUrl)
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .into(binding.photoView)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

    }

    override fun getCornerRadius() = 0F
    override fun getExpandedHeight() = ViewGroup.LayoutParams.MATCH_PARENT
    override fun isSheetAlwaysExpanded() = true

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val TAG = "ZoomImageFragment"
        private const val ARG_IMAGE_URL = "image_url"

        @JvmStatic
        fun newInstance(imageUrl: String) =
            ZoomImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_URL, imageUrl)
                }
            }
    }
}