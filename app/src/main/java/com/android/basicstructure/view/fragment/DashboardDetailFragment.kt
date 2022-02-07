package com.android.basicstructure.view.fragment

/**
 * Created by Jeetesh surana.
 */
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.basicstructure.R
import com.android.basicstructure.core.ui.BaseFragment
import com.android.basicstructure.core.util.addReplaceFragment
import com.android.basicstructure.core.util.goBack
import com.android.basicstructure.core.util.snackBar
import com.android.basicstructure.databinding.FragmentDashboardDetailBinding

class DashboardDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentDashboardDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardDetailBinding.inflate(
            LayoutInflater.from(requireContext()),
            null,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.let {
            it.btnCheckout.setOnClickListener {
                activity?.addReplaceFragment(
                    R.id.fl_container, CartFragment(),
                    addFragment = true,
                    addToBackStack = true
                )
            }
            it.imgBack.setOnClickListener {
                goBack()
            }
            it.imgFav.setOnClickListener { view ->
                view.isSelected = !view.isSelected
                val message = if (view.isSelected) {
                    resources.getString(R.string.addInFav)
                } else {
                    resources.getString(R.string.removeInFav)
                }
                binding.root.snackBar(message)
            }
        }
    }
}