package com.android.basicstructure.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.basicstructure.core.ui.BaseFragment
import com.android.basicstructure.core.util.goBack
import com.android.basicstructure.databinding.FragmentCartBinding
import com.android.basicstructure.model.response.CartData
import com.android.basicstructure.view.adapter.CartAdapter
import kotlinx.coroutines.launch

/**
 * Created by Jeetesh surana.
 */

@SuppressLint("NotifyDataSetChanged")
class CartFragment : BaseFragment() {

    private var mCartAdapter: CartAdapter? = null
    private var mCartDataList = ArrayList<CartData>()
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            init()
        }
    }

    private fun init() {
        initRecyclerView()
        initList()
        initClickListener()
    }

    private fun initClickListener() {
        binding.imgBack.setOnClickListener {
            goBack()
        }
    }

    private fun initList() {
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartDataList.add(CartData())
        mCartAdapter?.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.layoutManager = layoutManager
        mCartAdapter = CartAdapter(requireContext(), mCartDataList, object : CartAdapter
        .ItemClickListener {
            override fun itemClick(position: Int) {

            }
        })
        binding.rvCart.adapter = mCartAdapter
    }
}