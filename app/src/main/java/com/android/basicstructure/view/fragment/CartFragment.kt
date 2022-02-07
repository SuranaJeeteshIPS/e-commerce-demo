package com.android.basicstructure.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.basicstructure.R
import com.android.basicstructure.core.ui.BaseFragment
import com.android.basicstructure.core.util.goBack
import com.android.basicstructure.core.util.hideKeyboard
import com.android.basicstructure.core.util.snackBar
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
        mCartAdapter?.notifyDataSetChanged()
        getTotalValue()
    }

    private fun initRecyclerView() {
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        mCartAdapter = CartAdapter(requireContext(), mCartDataList, object : CartAdapter
        .ItemClickListener {
            override fun itemClick(position: Int) {
                activity?.hideKeyboard()
                getTotalValue()
            }

            override fun showMessage(message: String) {
                binding.root.snackBar(message)
                getTotalValue()
            }
        })
        binding.rvCart.adapter = mCartAdapter

        binding.rvCart.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                activity?.hideKeyboard()
            }
        })
    }

    private fun getTotalValue() {
        val total = mCartDataList.sumOf { it.getTotalAmountValue() }
        binding.txtTotalAmount.text = resources.getString(R.string.amount, total.toString())
    }
}